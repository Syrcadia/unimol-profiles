package it.unimol.profiles.servlet;

import it.unimol.profiles.ConnectionPool;
import it.unimol.profiles.ManagerDocenti;
import it.unimol.profiles.beans.utils.Docente;
import it.unimol.profiles.beans.utils.ElencoSezioniPersonalizzate;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Stefano
 */
public abstract class SezioneServlet extends HttpServlet {

    public static final String PERCORSO_FOTO_PROFILO_DEFAULT = "Images/profilo-default.png";

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Docente docente = this.getDocenteDallaUrl(request);
        if (!esisteDocente(docente)) {
            response.sendError(404, this.getMessaggioDocenteNonTrovato(docente));
        } else {
            request.setAttribute("percorso_foto_profilo", this.getPercorsoFotoProfilo(docente));
            request.setAttribute("elenco_sezioni_personalizzate", this.getElencoSezioniPersonalizzate(docente));
            request.setAttribute("docente", docente);
            this.setCustomRequestAttributes(request, response, docente);
            RequestDispatcher dispatcher = request.getRequestDispatcher(this.getJspToForward());
            dispatcher.forward(request, response);
        }
    }
    
    protected abstract String getJspToForward();
    
    protected abstract void setCustomRequestAttributes(HttpServletRequest request, HttpServletResponse response, Docente docente) throws ServletException, IOException;
    
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
