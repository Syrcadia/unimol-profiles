package it.unimol.profiles.servlet;

import it.unimol.profiles.ConnectionPool;
import it.unimol.profiles.ManagerDocenti;
import it.unimol.profiles.beans.utils.Docente;
import it.unimol.profiles.beans.utils.ElencoDocenti;
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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Stefano
 */
@WebServlet(name = "ElencoDocenti", urlPatterns = {"/ElencoDocenti"})
public class ElencoDocentiServlet extends HttpServlet {

    private static final String DB_DOCENTI_RUOLO_PROFESSORE_ORDINARIO = "Professore ordinario";
    private static final String DB_DOCENTI_RUOLO_PROFESSORE_ASSOCIATO = "Professore associato";
    private static final String DB_DOCENTI_RUOLO_RICERCATORE = "Ricercatore";
    private static final String DB_DOCENTI_RUOLO_RICERCATORE_A_TEMPO_DETERMINATO = "Ricercatore a tempo determinato";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ElencoDocenti elencoDocenti;
        elencoDocenti = this.getElencoDocenti();

        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/Jsp/ElencoDocentiJsp.jsp");
        request.setAttribute("elenco_docenti", elencoDocenti);
        dispatcher.forward(request, response);
    }

    public ElencoDocenti getElencoDocenti() {

        ElencoDocenti elencoDocenti = new ElencoDocenti();
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet;

            resultSet = statement.executeQuery(""
                    + "SELECT * "
                    + "FROM docenti ");
            ArrayList<Docente> listaProfessoriOrdinari = new ArrayList<>();
            ArrayList<Docente> listaProfessoriAssociati = new ArrayList<>();
            ArrayList<Docente> listaRicercatori = new ArrayList<>();
            ArrayList<Docente> listaRicercatoriATempoDeterminato = new ArrayList<>();
            while (resultSet.next()) {
                Docente nextDocente = new Docente();
                nextDocente.setId(resultSet.getString("id"));
                nextDocente.setNome(resultSet.getString("nome"));
                nextDocente.setCognome(resultSet.getString("cognome"));
                nextDocente.setSesso(resultSet.getString("sesso"));
                switch (resultSet.getString("ruolo")) {
                    case DB_DOCENTI_RUOLO_PROFESSORE_ORDINARIO:
                        listaProfessoriOrdinari.add(nextDocente);
                        break;

                    case DB_DOCENTI_RUOLO_PROFESSORE_ASSOCIATO:
                        listaProfessoriAssociati.add(nextDocente);
                        break;

                    case DB_DOCENTI_RUOLO_RICERCATORE:
                        listaRicercatori.add(nextDocente);
                        break;

                    case DB_DOCENTI_RUOLO_RICERCATORE_A_TEMPO_DETERMINATO:
                        listaRicercatoriATempoDeterminato.add(nextDocente);
                        break;
                }
            }
            elencoDocenti.setListaProfessoriOrdinari(listaProfessoriOrdinari);
            elencoDocenti.setListaProfessoriAssociati(listaProfessoriAssociati);
            elencoDocenti.setListaRicercatori(listaRicercatori);
            elencoDocenti.setListaRicercatoriATempoDeterminato(listaRicercatoriATempoDeterminato);

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

        return elencoDocenti;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

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
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
