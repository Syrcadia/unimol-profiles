package it.unimol.profiles.servlet.amministrazione;

import it.unimol.profiles.ConnectionPool;
import it.unimol.profiles.ManagerDocenti;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Stefano
 */
@WebServlet(name = "InserimentoNuovoDocente", urlPatterns = {"/InserimentoNuovoDocente"})
public class InserimentoNuovoDocenteServlet extends ServletAmministratore {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("action") != null && request.getParameter("action").equals("inserisci")) {
            inserisciDocente(request);
            mostraForm(request, response, "Docente inserito con successo");
           

        } else {
            mostraForm(request, response, null);
        }

    }

    private void inserisciDocente(HttpServletRequest request) {

        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String ruolo = request.getParameter("ruolo");
        String dipartimento = request.getParameter("dipartimento");
        String sesso = request.getParameter("sesso");
        String password = request.getParameter("password");

        Connection connection = null;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            Statement statement = connection.createStatement();

            statement.executeUpdate(""
                    + "INSERT INTO docenti (nome,cognome,id_dipartimento,id_ruolo,sesso,password) "
                    + "VALUES ('" + nome + "','" + cognome + "','" + dipartimento + "','" + ruolo + "','" + sesso + "','" + password + "') ");

            statement.close();
            
            
            
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

    private void mostraForm(HttpServletRequest request, HttpServletResponse response, String messaggio) throws ServletException, IOException {

        ArrayList<String> ruoli = new ArrayList<>();
        ArrayList<String> dipartimenti = new ArrayList<>();
        

        Connection connection = null;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet;

            resultSet = statement.executeQuery(""
                    + "SELECT * "
                    + "FROM ruoli "
                    + "ORDER BY id");

            while (resultSet.next()) {
                ruoli.add(resultSet.getString("nome_ruolo"));
            }

            resultSet = statement.executeQuery(""
                    + "SELECT * "
                    + "FROM dipartimenti "
                    + "ORDER BY id");

            while (resultSet.next()) {
                dipartimenti.add(resultSet.getString("nome_dipartimento"));
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

        request.setAttribute("ruoli", ruoli);
        request.setAttribute("dipartimenti", dipartimenti);
        request.setAttribute("messaggio", messaggio);

        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/Jsp/JspAmministratore/InserimentoNuovoDocenteJsp.jsp");
        dispatcher.forward(request, response);

    }

}
