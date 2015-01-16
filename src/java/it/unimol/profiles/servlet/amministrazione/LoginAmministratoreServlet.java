package it.unimol.profiles.servlet.amministrazione;

import it.unimol.profiles.ConnectionPool;
import it.unimol.profiles.ManagerDocenti;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(name = "LoginAmministratoreServlet", urlPatterns = {"/LoginAmministratoreServlet"})
public class LoginAmministratoreServlet extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/Jsp/JspAmministratore/LoginAmministratoreJsp.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nomeAmministratore = request.getParameter("nome_amministratore");
        String password = request.getParameter("password");

        //Logger.getLogger(ManagerDocenti.class.getName()).log(Level.SEVERE, request.getParameterNames().nextElement());
        Connection connection = null;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet;

            resultSet = statement.executeQuery(""
                    + "SELECT * "
                    + "FROM amministratori "
                    + "WHERE nome='" + nomeAmministratore + "' "
                    + "AND password='" + password + "'");

            if (resultSet.next()) {
                request.getSession().setAttribute("isAdmin", true);
                response.sendRedirect("AreaRiservataAmministratore");
            } else {
                RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/Jsp/JspAmministratore/LoginAmministratoreJsp.jsp");
                dispatcher.forward(request, response);
            }

            resultSet.close(); //non dimenticare 
            statement.close(); //queste due istruzioni!!!
        } catch (SQLException ex) {
            Logger.getLogger(ManagerDocenti.class.getName()).log(Level.SEVERE, null, ex); //cosa fare in caso di errore del database?
            Logger.getLogger(ManagerDocenti.class.getName()).log(Level.SEVERE, null, "ERRORE DEL DATABASE, CONTROLLARE CHE SIA ATTIVO IL SERVIZIO MYSQL E CHE I PARAMETRI DELLA CLASSE ParametriDatabase SIANO IMPOSTATI CORRETTAMENTE"); //cosa fare in caso di errore del database?
        } finally { //il contenuto del finally è fondamentale per il funzionamento del connection pool
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception ignore) {
                }
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
