package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ClientDAO;
import dao.PretDAO;
import model.Pret;

/**
 * Servlet implementation class PretServlet
 */
@WebServlet("/prets")
public class PretServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PretDAO pretDAO = new PretDAO();
    private ClientDAO clientDAO = new ClientDAO();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PretServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		  String action = request.getParameter("action");

		    if ("add".equals(action)) {
		        request.setAttribute("clients", clientDAO.findAll());
		        request.getRequestDispatcher("/WEB-INF/ajouterPret.jsp").forward(request, response);
		        return;
		    }

		    if ("delete".equals(action)) {
		        int id = Integer.parseInt(request.getParameter("id"));
		        pretDAO.delete(id);
		        response.sendRedirect(request.getContextPath() + "/prets");
		        return;
		    }

		    if ("edit".equals(action)) {
		        int id = Integer.parseInt(request.getParameter("id"));
		        Pret pret = pretDAO.findById(id);

		        request.setAttribute("pret", pret);
		        request.setAttribute("clients", clientDAO.findAll());
		        request.getRequestDispatcher("/WEB-INF/modifierPret.jsp").forward(request, response);
		        return;
		    }

		    // Filtre par clientId 
		    String risque = request.getParameter("risque");
		    String clientId = request.getParameter("clientId");
		    String q = request.getParameter("q");

		    List<Pret> prets;

		    if ("1".equals(risque)) {
		        prets = pretDAO.findPretsRisque();
		    }
		    else if (clientId != null && !clientId.isEmpty()) {
		        prets = pretDAO.findByClient(Integer.parseInt(clientId));
		    }
		    else if (q != null && !q.trim().isEmpty()) {
		        prets = pretDAO.search(q.trim());
		    }
		    else {
		        prets = pretDAO.findAll();
		    }

		    request.setAttribute("prets", prets);
		    request.setAttribute("clients", clientDAO.findAll());
		    request.getRequestDispatcher("/WEB-INF/prets.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String action = request.getParameter("action");

	        if ("add".equals(action)) {

	            Pret pret = new Pret();
	            pret.setIdClient(Integer.parseInt(request.getParameter("id_client")));
	            pret.setTypePret(request.getParameter("type_pret"));
	            pret.setMontantMensuel(Double.parseDouble(request.getParameter("montant_mensuel")));
	            pret.setDuree(Integer.parseInt(request.getParameter("duree")));
	            pret.setTauxInteret(Double.parseDouble(request.getParameter("taux_interet")));

	            pretDAO.save(pret);
	            response.sendRedirect(request.getContextPath() + "/prets");
	        }
	        if ("update".equals(action)) {

	            Pret pret = new Pret();
	            pret.setIdPret(Integer.parseInt(request.getParameter("id_pret")));
	            pret.setIdClient(Integer.parseInt(request.getParameter("id_client")));
	            pret.setTypePret(request.getParameter("type_pret"));
	            pret.setMontantMensuel(Double.parseDouble(request.getParameter("montant_mensuel")));
	            pret.setDuree(Integer.parseInt(request.getParameter("duree")));
	            pret.setTauxInteret(Double.parseDouble(request.getParameter("taux_interet")));

	            pretDAO.update(pret);

	            response.sendRedirect(request.getContextPath() + "/prets");
	        }

	}

}
