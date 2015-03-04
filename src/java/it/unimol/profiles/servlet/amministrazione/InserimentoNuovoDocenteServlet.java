package it.unimol.profiles.servlet.amministrazione;

import it.unimol.profiles.SQLLayer.ConnectionPool;
import it.unimol.profiles.ManagerDocenti;
import it.unimol.profiles.servlet.sviluppo.Log;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
            PreparedStatement preparedStatement = connection.prepareStatement(""
                    + "INSERT INTO docenti (nome,cognome,id_dipartimento,id_ruolo,sesso,password) "
                    + "VALUES (?,?,?,?,?,?) ");
            preparedStatement.setString(1,nome);
            preparedStatement.setString(2,cognome);
            preparedStatement.setString(3,dipartimento);
            preparedStatement.setString(4,ruolo);
            preparedStatement.setString(5,sesso);
            preparedStatement.setString(6,password);

            
            preparedStatement.executeUpdate();

            preparedStatement.close();
            
            
            
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

        ArrayList<String> ruoli = getElencoRuoli();
        ArrayList<String> dipartimenti = getElencoDipartimenti();
        
        Connection connection = null;

        request.setAttribute("ruoli", ruoli);
        request.setAttribute("dipartimenti", dipartimenti);
        request.setAttribute("messaggio", messaggio);

        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/Jsp/JspAmministratore/InserimentoNuovoDocenteJsp.jsp");
        dispatcher.forward(request, response);

    }

}
