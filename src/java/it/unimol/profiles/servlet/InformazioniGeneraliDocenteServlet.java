package it.unimol.profiles.servlet;

import it.unimol.profiles.ConnectionPool;
import it.unimol.profiles.ManagerDocenti;
import it.unimol.profiles.beans.pagine.docente.InformazioniGeneraliDocente;
import it.unimol.profiles.beans.utils.Docente;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Stefano
 */
@WebServlet(name = "InformazioniGeneraliDocente", urlPatterns = {"/InformazioniGeneraliDocente"})
public class InformazioniGeneraliDocenteServlet extends SezioneServlet {

    @Override
    protected void setCustomRequestAttributes(HttpServletRequest request, HttpServletResponse response, Docente docente) throws ServletException, IOException {
        InformazioniGeneraliDocente informazioniGeneraliDocente = this.getInfoGeneraliDocente(docente);
        request.setAttribute("informazioni_generali_docente", informazioniGeneraliDocente);
    }

    @Override
    protected String getJspToForward() {
        return "WEB-INF/Jsp/JspDocenti/InformazioniGeneraliDocenteJsp.jsp";
    }

    public InformazioniGeneraliDocente getInfoGeneraliDocente(Docente docente) {
        InformazioniGeneraliDocente informazioniGeneraliDocente = new InformazioniGeneraliDocente();

        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet;

//            resultSet = statement.executeQuery(""
//                    + "SELECT docenti.nome, docenti.cognome, docenti.dipartimento, ruoli.nome_ruolo "
//                    + "FROM docenti INNER JOIN ruoli ON docenti.id_ruolo = ruoli.id "
//                    + "WHERE docenti.id = " + docente.getId());
             resultSet = statement.executeQuery(""
                    + "SELECT docenti.nome, docenti.cognome, dipartimenti.nome_dipartimento, ruoli.nome_ruolo "
                    + "FROM ((docenti INNER JOIN ruoli ON docenti.id_ruolo = ruoli.id) INNER JOIN dipartimenti ON docenti.id_dipartimento = dipartimenti.id)"
                    + "WHERE docenti.id = " + docente.getId());
            resultSet.next();
            informazioniGeneraliDocente.setNome(resultSet.getString("nome"));
            informazioniGeneraliDocente.setCognome(resultSet.getString("cognome"));
            informazioniGeneraliDocente.setDipartimento(resultSet.getString("nome_dipartimento"));
            informazioniGeneraliDocente.setRuolo(resultSet.getString("nome_ruolo"));

            resultSet = statement.executeQuery(""
                    + "SELECT email "
                    + "FROM email "
                    + "WHERE id_docente = " + docente.getId());
            ArrayList<String> emails = new ArrayList<>();
            while (resultSet.next()) {
                emails.add(resultSet.getString("email"));
            }
            informazioniGeneraliDocente.setEmail(emails);

            resultSet = statement.executeQuery(""
                    + "SELECT num_telefono "
                    + "FROM telefono "
                    + "WHERE id_docente = " + docente.getId());
            ArrayList<String> telefoni = new ArrayList<>();
            while (resultSet.next()) {
                telefoni.add(resultSet.getString("num_telefono"));
            }
            informazioniGeneraliDocente.setTelefono(telefoni);

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
        return informazioniGeneraliDocente;
    }

}
