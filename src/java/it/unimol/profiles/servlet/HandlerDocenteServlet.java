package it.unimol.profiles.servlet;

import it.unimol.profiles.ConnectionPool;
import it.unimol.profiles.ManagerDocenti;
import it.unimol.profiles.beans.utils.Docente;
import it.unimol.profiles.beans.utils.ElencoSezioniPersonalizzate;
import static it.unimol.profiles.servlet.SezioneServlet.PERCORSO_FOTO_PROFILO_DEFAULT;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Stefano
 */
@WebServlet(name = "HandlerDocente", urlPatterns = {"/HandlerDocente"})
public abstract class HandlerDocenteServlet extends HttpServlet {

    protected boolean esisteDocente(Docente docente) {
        Connection connection = null;
        boolean esisteDocente = false;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet;
            resultSet = statement.executeQuery(""
                    + "SELECT * "
                    + "FROM docenti "
                    + "WHERE "
                    + "id =" + docente.getId() + " "
                    + "AND nome ='" + docente.getNome() + "' "
                    + "AND cognome ='" + docente.getCognome() + "' "
                    + "AND sesso ='" + docente.getSesso() + "'"
            );

            esisteDocente = resultSet.next();

            resultSet.close();
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(ManagerDocenti.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(ManagerDocenti.class.getName()).log(Level.SEVERE, null, "ERRORE DEL DATABASE, CONTROLLARE CHE SIA ATTIVO IL SERVIZIO MYSQL E CHE I PARAMETRI DELLA CLASSE ParametriDatabase SIANO IMPOSTATI CORRETTAMENTE"); //cosa fare in caso di errore del database?
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception ignore) {
                }
            }
        }
        return esisteDocente;
    }

    protected Docente getDocenteDallaUrl(HttpServletRequest request) {
        Docente docente = new Docente();
        docente.setId(((String) request.getParameter("id")));
        docente.setNome(((String) request.getParameter("nome")));
        docente.setCognome(((String) request.getParameter("cognome")));
        docente.setSesso(((String) request.getParameter("s")));
        return docente;
    }

    protected String getMessaggioDocenteNonTrovato(Docente docente) {
        return "Il docente richiesto "
                + "(nome = " + docente.getNome() + ", "
                + "cognome = " + docente.getCognome() + ", "
                + "id = " + docente.getId() + ", "
                + "s=" + docente.getSesso() + ")"
                + "non è presente nel database";
    }

    protected String getPercorsoFotoProfilo(Docente docente) { //ritorna la posizione della foto
        Connection connection = null;
        String nomeFotoProfilo = null;
        String fotoPath;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(""
                    + "SELECT nome_foto_profilo "
                    + "FROM docenti "
                    + "WHERE id = " + docente.getId());
            resultSet.next();

            nomeFotoProfilo = resultSet.getString("nome_foto_profilo");

            resultSet.close();
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(ManagerDocenti.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(ManagerDocenti.class.getName()).log(Level.SEVERE, null, "ERRORE DEL DATABASE, CONTROLLARE CHE SIA ATTIVO IL SERVIZIO MYSQL E CHE I PARAMETRI DELLA CLASSE ParametriDatabase SIANO IMPOSTATI CORRETTAMENTE"); //cosa fare in caso di errore del database?
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception ignore) {
                }
            }
        }

        if (nomeFotoProfilo == null) {
            return PERCORSO_FOTO_PROFILO_DEFAULT;
        } else {
            fotoPath = "Risorse/"
                    + docente.getNomeCartella()
                    + "/foto_profilo/"
                    + nomeFotoProfilo;
        }
        return fotoPath;
    }

    protected ElencoSezioniPersonalizzate getElencoSezioniPersonalizzate(Docente docente) {
        Connection connection = null;
        ElencoSezioniPersonalizzate elencoSezioniPersonalizzate = new ElencoSezioniPersonalizzate();

        try {
            connection = ConnectionPool.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(""
                    + "SELECT id_sezione, nome_sezione, ordine "
                    + "FROM sezioni_docenti "
                    + "WHERE id_docente = " + docente.getId() + " "
                    + "ORDER BY ordine");

            while (resultSet.next()) {
                elencoSezioniPersonalizzate.addSezione(resultSet.getString("nome_sezione"), resultSet.getString("id_sezione"));
            }

            resultSet.close();
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(ManagerDocenti.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(ManagerDocenti.class.getName()).log(Level.SEVERE, null, "ERRORE DEL DATABASE, CONTROLLARE CHE SIA ATTIVO IL SERVIZIO MYSQL E CHE I PARAMETRI DELLA CLASSE ParametriDatabase SIANO IMPOSTATI CORRETTAMENTE"); //cosa fare in caso di errore del database?
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception ignore) {
                }
            }
        }

        return elencoSezioniPersonalizzate;

    }

    protected ArrayList<String> getElencoRuoli() {
        ArrayList<String> ruoli = new ArrayList<>();
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
        return ruoli;
    }

    protected ArrayList<String> getElencoDipartimenti() {
        ArrayList<String> dipartimenti = new ArrayList<>();
        Connection connection = null;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet;

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
        return dipartimenti;
    }
}


