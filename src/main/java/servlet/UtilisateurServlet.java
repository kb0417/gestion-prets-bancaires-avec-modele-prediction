package servlet;

import dao.UtilisateurDAO;
import model.Utilisateur;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/utilisateurs")
public class UtilisateurServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        // 🔐 Sécurité : ADMIN uniquement
        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Utilisateur u = (Utilisateur) session.getAttribute("utilisateur");

        if (u == null || !"ADMIN".equals(u.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }


        // ➕ Ajouter
        if ("add".equals(action)) {
            request.getRequestDispatcher("/WEB-INF/ajouterUtilisateur.jsp")
                   .forward(request, response);
            return;
        }

        // ✏️ Modifier
        if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Utilisateur utilisateur = utilisateurDAO.findById(id);

            if (utilisateur == null) {
                response.sendRedirect(request.getContextPath() + "/utilisateurs");
                return;
            }

            request.setAttribute("utilisateur", utilisateur);
            request.getRequestDispatcher("/WEB-INF/modifierUtilisateur.jsp")
                   .forward(request, response);
            return;
        }

        // 🗑 Supprimer
        if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            utilisateurDAO.delete(id);

            request.getSession().setAttribute(
                "success", "Utilisateur supprimé avec succès."
            );

            response.sendRedirect(request.getContextPath() + "/utilisateurs");
            return;
        }

        // 📋 Liste (par défaut)
        List<Utilisateur> utilisateurs = utilisateurDAO.findAll();
        request.setAttribute("utilisateurs", utilisateurs);

        request.getRequestDispatcher("/WEB-INF/utilisateurs.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("add".equals(action)) {
            ajouterUtilisateur(request, response);
            return;
        }

        if ("update".equals(action)) {
            modifierUtilisateur(request, response);
        }
    }

    // =============================
    // 🔹 AJOUT
    // =============================
    private void ajouterUtilisateur(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id_utilisateur"));
        String login = request.getParameter("login");
        String motDePasse = request.getParameter("mot_de_passe");
        String role = request.getParameter("role");

        if (utilisateurDAO.findById(id) != null) {
            request.setAttribute("erreur", "ID utilisateur déjà existant.");
            request.getRequestDispatcher("/WEB-INF/ajouterUtilisateur.jsp")
                   .forward(request, response);
            return;
        }

        Utilisateur u = new Utilisateur();
        u.setIdUtilisateur(id);
        u.setLogin(login);
        u.setMotDePasse(motDePasse);
        u.setRole(role);

        utilisateurDAO.save(u);

        request.getSession().setAttribute(
            "success", "Utilisateur ajouté avec succès."
        );

        response.sendRedirect(request.getContextPath() + "/utilisateurs");
    }

    // =============================
    // 🔹 MODIFICATION
    // =============================
    private void modifierUtilisateur(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(request.getParameter("id_utilisateur"));
        String login = request.getParameter("login");
        String motDePasse = request.getParameter("mot_de_passe");
        String role = request.getParameter("role");

        Utilisateur u = new Utilisateur();
        u.setIdUtilisateur(id);
        u.setLogin(login);
        u.setMotDePasse(motDePasse);
        u.setRole(role);

        utilisateurDAO.update(u);

        request.getSession().setAttribute(
            "success", "Utilisateur modifié avec succès."
        );

        response.sendRedirect(request.getContextPath() + "/utilisateurs");
    }
}
