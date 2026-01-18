package servlet;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ClientDAO;
import dao.PretDAO;
import model.Client;
import model.Pret;
import util.FlaskIAClient;

/**
 * Servlet implementation class EvaluationRisqueServlet
 */
@WebServlet("/evaluerRisque")
public class EvaluationRisqueServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EvaluationRisqueServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		 /*this.getServletContext()
         .getRequestDispatcher("/WEB-INF/resultatRisque.jsp")
         .forward(request, response);*/
		request.getRequestDispatcher("/WEB-INF/evaluationRisque.jsp")
        .forward(request, response);
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1️⃣ Récupération du prêt
        int idPret = Integer.parseInt(request.getParameter("id_pret"));

        PretDAO pretDAO = new PretDAO();
        ClientDAO clientDAO = new ClientDAO();

        Pret pret = pretDAO.findById(idPret);

        if (pret == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Prêt introuvable");
            return;
        }

        Client client = clientDAO.findById(pret.getIdClient());

        if (client == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Client introuvable");
            return;
        }

        // 2️⃣ Construction du JSON pour l’IA
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



        // 3️⃣ Appel de l’API Flask
        FlaskIAClient.PredictionResult result = null;
		try {
			result = FlaskIAClient.predict(jsonInput);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        String interpretation = result.getInterpretation();
        double probabilite = result.getProbabilite();

        request.setAttribute("interpretation", interpretation);
        request.setAttribute("probabilite", probabilite);

        pretDAO.saveEvaluationRisque(idPret, probabilite, interpretation);

        request.getRequestDispatcher("/WEB-INF/evaluationRisque.jsp")
               .forward(request, response);


    }
    
}
