package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        HttpSession session = req.getSession(false);

        boolean estConnecte = (session != null && session.getAttribute("utilisateur") != null);

        boolean estPageLogin =
                uri.endsWith("login.jsp") ||
                uri.endsWith("/login");

        boolean estRessourcePublique =
                uri.contains("/css/") ||
                uri.contains("/images/");

        if (estConnecte || estPageLogin || estRessourcePublique) {
            chain.doFilter(request, response);
        } else {
            res.sendRedirect(req.getContextPath() + "/login");
        }
    }
}
