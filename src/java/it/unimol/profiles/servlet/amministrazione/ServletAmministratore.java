package it.unimol.profiles.servlet.amministrazione;

import it.unimol.profiles.servlet.HandlerDocenteServlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Stefano
 */
public abstract class ServletAmministratore extends HandlerDocenteServlet {

    protected abstract void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (adminCheck(request, response)) {
            processRequest(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (adminCheck(request, response)) {
            processRequest(request, response);
        }
    }

    private boolean adminCheck(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("isAdmin") == null) {
            request.getSession().setAttribute("isAdmin", false);
            response.sendRedirect("LoginAmministratore");
            return false;
        } else if (!(boolean) request.getSession().getAttribute("isAdmin")) {
            response.sendRedirect("LoginAmministratore");
            return false;
        }
        return true;
    }

}
