package servlet;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ClientDAO;
import dao.PretDAO;
import model.Client;
import model.Pret;
import util.FlaskIAClient;

/**
 * Servlet implementation class DashboardServlet
 */
@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DashboardServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	      // 🔐 Sécurité
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("utilisateur") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        ClientDAO clientDAO = new ClientDAO();
        PretDAO pretDAO = new PretDAO();

        // ✅ Option C : calculer uniquement si pas encore évalué
        List<Pret> tousLesPrets = pretDAO.findAll();

        for (Pret pret : tousLesPrets) {

            int idPret = pret.getIdPret();

            if (!pretDAO.hasEvaluation(idPret)) {

                Client client = clientDAO.findById(pret.getIdClient());
                if (client == null) continue;

                String jsonInput = String.format(Locale.US, """
                    {
                      "revenu": %.2f,
                      "remboursement": %.2f,
                      "duree": %d,
                      "taux": %.2f,
                      "type": "%s"
                    }
                    """,
                        client.getRevenuMensuel(),
                        pret.getMontantMensuel(),
                        pret.getDuree(),
                        pret.getTauxInteret(),
                        pret.getTypePret().toLowerCase()
                );

                try {
                    FlaskIAClient.PredictionResult result = FlaskIAClient.predict(jsonInput);
                    pretDAO.saveEvaluationRisque(idPret, result.getProbabilite(), result.getInterpretation());
                } catch (Exception e) {
                    e.printStackTrace(); // Flask down => ignore ce prêt
                }
            }
        }

        // ✅ Stats
        request.setAttribute("nbClients", clientDAO.count());
        request.setAttribute("nbPrets", pretDAO.count());
        request.setAttribute("totalMontant", pretDAO.sumMontant());
        request.setAttribute("nbPretsRisque", pretDAO.countPretsRisque());
        

        request.getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
