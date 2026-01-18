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
import model.Client;


/**
 * Servlet implementation class ClientServlet
 */
@WebServlet("/clients")
public class ClientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ClientDAO clientDAO = new ClientDAO();
	private PretDAO pretDAO = new PretDAO();


       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClientServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Client client = clientDAO.findById(id);

            if (client == null) {
                response.sendRedirect(request.getContextPath() + "/clients");
                return;
            }

            request.setAttribute("client", client);
            request.getRequestDispatcher("/WEB-INF/modifierClient.jsp")
                   .forward(request, response);
            return;
        }

        if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));

            int nbPrets = pretDAO.countByClient(id);

            if (nbPrets > 0) {
                request.getSession().setAttribute("error",
                    "Impossible de supprimer ce client : il possède " + nbPrets + " prêt(s). Supprimez d’abord ses prêts.");
                response.sendRedirect(request.getContextPath() + "/clients");
                return;
            }

            clientDAO.delete(id);

            request.getSession().setAttribute("success", "Client supprimé avec succès.");
            response.sendRedirect(request.getContextPath() + "/clients");
            return;
        }

        if ("add".equals(action)) {
            request.getRequestDispatcher("/WEB-INF/ajouterClient.jsp")
                   .forward(request, response);
            return;
        }
        
        if ("view".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));

            Client client = clientDAO.findById(id);
            if (client == null) {
                response.sendRedirect(request.getContextPath() + "/clients");
                return;
            }

            // prêts du client
            List<model.Pret> pretsClient = pretDAO.findByClient(id);

            request.setAttribute("client", client);
            request.setAttribute("pretsClient", pretsClient);

            request.getRequestDispatcher("/WEB-INF/detailsClient.jsp")
                   .forward(request, response);
            return;
        }

        
        String q = request.getParameter("q");

        List<Client> clients;
        if (q != null && !q.trim().isEmpty()) {
            clients = clientDAO.search(q.trim());
        } else {
        	// Liste des clients (par défaut)
            clients = clientDAO.findAll();
        }

        request.setAttribute("clients", clients);
        request.getRequestDispatcher("/WEB-INF/clients.jsp").forward(request, response);



        // Liste des clients (par défaut)
        /*List<Client> clients = clientDAO.findAll();
        request.setAttribute("clients", clients);
        request.getRequestDispatcher("/WEB-INF/clients.jsp")
               .forward(request, response);*/
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("add".equals(action)) {
            ajouterClient(request, response);
            return;
        }

        if ("update".equals(action)) {
            modifierClient(request, response);
        }
    }

    private void ajouterClient(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id_client");
        String ville = request.getParameter("ville");
        String codePostal = request.getParameter("code_postal");
        String revenuStr = request.getParameter("revenu_mensuel");

        if (idStr.isEmpty() || ville.isEmpty() ||
            codePostal.isEmpty() || revenuStr.isEmpty()) {

            request.setAttribute("erreur", "Tous les champs sont obligatoires.");
            request.getRequestDispatcher("/WEB-INF/ajouterClient.jsp")
                   .forward(request, response);
            return;
        }

        int id = Integer.parseInt(idStr);
        double revenu = Double.parseDouble(revenuStr);

        if (clientDAO.findById(id) != null) {
            request.setAttribute("erreur", "ID client déjà existant.");
            request.getRequestDispatcher("/WEB-INF/ajouterClient.jsp")
                   .forward(request, response);
            return;
        }

        clientDAO.save(new Client(id, ville, codePostal, revenu));

        request.getSession().setAttribute(
            "success", "Client ajouté avec succès."
        );

        response.sendRedirect(request.getContextPath() + "/clients");
    }

    private void modifierClient(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(request.getParameter("id_client"));
        String ville = request.getParameter("ville");
        String codePostal = request.getParameter("code_postal");
        double revenu = Double.parseDouble(request.getParameter("revenu_mensuel"));

        clientDAO.update(new Client(id, ville, codePostal, revenu));

        request.getSession().setAttribute(
            "success", "Client modifié avec succès."
        );

        response.sendRedirect(request.getContextPath() + "/clients");
    }

}
	

