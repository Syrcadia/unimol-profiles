package it.unimol.profiles.servlet.amministrazione;

import it.unimol.profiles.SQLLayer.ConnectionPool;
import it.unimol.profiles.ManagerDocenti;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
@WebServlet(name = "LoginAmministratore", urlPatterns = {"/LoginAmministratore"})
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

        if (nomeAmministratore == null || password == null) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/Jsp/JspAmministratore/LoginAmministratoreJsp.jsp");
            dispatcher.forward(request, response);
        }

        //Logger.getLogger(ManagerDocenti.class.getName()).log(Level.SEVERE, request.getParameterNames().nextElement());
        Connection connection = null;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(""
                    + "SELECT * "
                    + "FROM amministratori "
                    + "WHERE nome=? "
                    + "AND password=?");
            preparedStatement.setString(1, nomeAmministratore);
            preparedStatement.setString(2, password);
            
            ResultSet resultSet;

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                request.getSession().setAttribute("isAdmin", true);
                response.sendRedirect("AreaRiservataAmministratore");
            } else {
                request.getSession().setAttribute("isAdmin", false);
                RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/Jsp/JspAmministratore/LoginAmministratoreJsp.jsp");
                dispatcher.forward(request, response);
            }

            resultSet.close(); //non dimenticare 
            preparedStatement.close(); //queste due istruzioni!!!
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
