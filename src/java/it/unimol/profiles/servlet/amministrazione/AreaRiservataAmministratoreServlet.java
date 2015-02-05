package it.unimol.profiles.servlet.amministrazione;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Stefano
 */
@WebServlet(name = "AreaRiservataAmministratore", urlPatterns = {"/AreaRiservataAmministratore"})
public class AreaRiservataAmministratoreServlet extends ServletAmministratore {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/Jsp/JspAmministratore/AreaRiservataAmministratoreJsp.jsp");
            dispatcher.forward(request, response);
    }
}
