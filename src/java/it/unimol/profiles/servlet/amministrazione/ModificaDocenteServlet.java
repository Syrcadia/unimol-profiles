package it.unimol.profiles.servlet.amministrazione;

import it.unimol.profiles.ConnectionPool;
import it.unimol.profiles.ManagerDocenti;
import it.unimol.profiles.ManagerFileSystem;
import it.unimol.profiles.beans.pagine.docente.InformazioniGeneraliDocente;
import it.unimol.profiles.beans.utils.Docente;
import it.unimol.profiles.beans.utils.ElencoDocenti;
import it.unimol.profiles.exceptions.UploadNonValidoException;
import it.unimol.profiles.servlet.InformazioniGeneraliDocenteServlet;
import it.unimol.profiles.servlet.sviluppo.Log;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author Stefano
 */
@MultipartConfig()
@WebServlet(name = "ModificaDocente", urlPatterns = {"/ModificaDocente"})
public class ModificaDocenteServlet extends ServletAmministratore {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = null;
        String nomeDocente = null;
        String cognomeDocente = null;

        if (request.getContentType() != null && request.getContentType().toLowerCase().contains("multipart/form-data")) {
            for (Part part : request.getParts()) {
                if ("action".equals(part.getName())) {
                    Scanner scanner = new Scanner(part.getInputStream());
                    action = scanner.nextLine();
                } else if ("nome_docente".equals(part.getName())) {
                    Scanner scanner = new Scanner(part.getInputStream());
                    nomeDocente = scanner.nextLine();
                } else if ("cognome_docente".equals(part.getName())) {
                    Scanner scanner = new Scanner(part.getInputStream());
                    cognomeDocente = scanner.nextLine();
                }
            }
        } else {
            action = request.getParameter("action");
        }

        if (action != null) {

            String idDocente = request.getParameter("id_docente");

            Connection connection = null;

            try {
                connection = ConnectionPool.getInstance().getConnection();
                Statement statement = connection.createStatement();

                switch (action) {
                    case "aggiungi_indirizzo_email":

                        String newEmail = request.getParameter("email");

                        statement.executeUpdate(""
                                + "INSERT INTO email (email,id_docente) "
                                + "VALUES ('" + newEmail + "','" + idDocente + "') ");

                        break;

                    case "rimuovi_indirizzo_email":

                        String emailToDelete = request.getParameter("email");

                        statement.executeUpdate(""
                                + "DELETE FROM email "
                                + "WHERE email='" + emailToDelete + "' "
                                + "AND id_docente='" + idDocente + "' ");

                        break;
                    case "aggiungi_contatto_telefonico":

                        String newTelefono = request.getParameter("telefono");

                        statement.executeUpdate(""
                                + "INSERT INTO telefono (num_telefono,id_docente) "
                                + "VALUES ('" + newTelefono + "','" + idDocente + "') ");

                        break;
                    case "rimuovi_contatto_telefonico":
                        String telefonoToDelete = request.getParameter("contatti");

                        statement.executeUpdate(""
                                + "DELETE FROM telefono "
                                + "WHERE num_telefono='" + telefonoToDelete + "' "
                                + "AND id_docente='" + idDocente + "' ");

                        break;
                    case "modifica_ruolo":

                        String idNuovoRuolo = request.getParameter("ruoli");

                        statement.executeUpdate(""
                                + "UPDATE docenti "
                                + "SET id_ruolo='" + idNuovoRuolo + "' "
                                + "WHERE id='" + idDocente + "' ");

                        break;
                    case "modifica_dipartimento":

                        String idNuovoDipartimento = request.getParameter("dipartimenti");

                        statement.executeUpdate(""
                                + "UPDATE docenti "
                                + "SET id_dipartimento='" + idNuovoDipartimento + "' "
                                + "WHERE id='" + idDocente + "' ");

                        break;
                    case "configura_pagina_insegnamenti":

                        String idPaginaInsegnamenti = request.getParameter("pagina_insegnamenti");

                        statement.executeUpdate(""
                                + "UPDATE docenti "
                                + "SET id_pagina_insegnamenti='" + idPaginaInsegnamenti + "' "
                                + "WHERE id='" + idDocente + "' ");

                        break;
                    case "modifica_curriculum":
                        String percorsoFilePdf = "Risorse" + File.separator + nomeDocente.toLowerCase() + "_" + cognomeDocente.toLowerCase() + "_" + idDocente + File.separator + "curriculum";
                        String nomeFilePdf = "curriculum" + "_" + nomeDocente.toLowerCase() + "_" + cognomeDocente.toLowerCase() + ".pdf";
                        ManagerFileSystem.inserisciFile(request, percorsoFilePdf, nomeFilePdf);

                        break;
                    case "modifica_pubblicazioni":
                        break;
                    case "modifica_orario_di_ricevimento":
                        break;
                    case "modifica_sezione_personalizzata":
                        break;
                    case "modifica_immagine_profilo":
                        break;
                    
                }

                statement.close();

            } catch (SQLException ex) {
                Logger.getLogger(ManagerDocenti.class.getName()).log(Level.SEVERE, null, ex); //cosa fare in caso di errore del database?
                Logger.getLogger(ManagerDocenti.class.getName()).log(Level.SEVERE, null, "ERRORE DEL DATABASE, CONTROLLARE CHE SIA ATTIVO IL SERVIZIO MYSQL E CHE I PARAMETRI DELLA CLASSE ParametriDatabase SIANO IMPOSTATI CORRETTAMENTE"); //cosa fare in caso di errore del database?
            } catch (UploadNonValidoException ex) {
                Logger.getLogger(ModificaDocenteServlet.class.getName()).log(Level.SEVERE, null, ex);
            } finally { //il contenuto del finally è fondamentale per il funzionamento del connection pool
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (Exception ignore) {
                    }
                }
            }
        } else {
            if (request.getParameter("id") == null) {
                mostraElencoDocentiDaModificare(request, response);
            } else {
                mostraMenuModifica(request, response, request.getParameter("id"));
            }
        }
    }

    private void mostraMenuModifica(HttpServletRequest request, HttpServletResponse response, String id) throws ServletException, IOException {

        Docente docente = getDocenteDallaUrl(request);
        if (!esisteDocente(docente)) {
            response.sendError(404, this.getMessaggioDocenteNonTrovato(docente));
        } else {

            InformazioniGeneraliDocenteServlet informazioniGeneraliDocenteServlet = new InformazioniGeneraliDocenteServlet();
            InformazioniGeneraliDocente informazioniGeneraliDocente = informazioniGeneraliDocenteServlet.getInfoGeneraliDocente(docente);

            request.setAttribute("percorso_foto_profilo", this.getPercorsoFotoProfilo(docente));
            request.setAttribute("elenco_sezioni_personalizzate", this.getElencoSezioniPersonalizzate(docente));
            request.setAttribute("docente", docente);
            request.setAttribute("informazioni_generali_docente", informazioniGeneraliDocente);
            request.setAttribute("ruoli", this.getElencoRuoli());
            request.setAttribute("dipartimenti", this.getElencoDipartimenti());

            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/Jsp/JspAmministratore/MenuModificaDocenteJsp.jsp");
            dispatcher.forward(request, response);
        }

    }

    private void mostraElencoDocentiDaModificare(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ElencoDocenti elencoDocenti = new ElencoDocenti();
        Connection connection = null;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet;

            ArrayList<ArrayList<Docente>> liste = new ArrayList<>();
            ArrayList<String> ruoli = new ArrayList<>();

            resultSet = statement.executeQuery(""
                    + "SELECT * "
                    + "FROM ruoli ");

            while (resultSet.next()) {
                ArrayList<Docente> nextLista = new ArrayList<>();
                liste.add(nextLista);
                ruoli.add(resultSet.getString("nome_ruolo"));
            }

            resultSet = statement.executeQuery(""
                    + "SELECT docenti.id, docenti.nome, docenti.cognome, docenti.id_ruolo, docenti.id_pagina_insegnamenti, docenti.nome_foto_profilo, docenti.sesso, ruoli.nome_ruolo "
                    + "FROM docenti INNER JOIN ruoli ON docenti.id_ruolo = ruoli.id "
            );

            while (resultSet.next()) {
                Docente docente = new Docente();
                docente.setId(resultSet.getString("id"));
                docente.setNome(resultSet.getString("nome"));
                docente.setCognome(resultSet.getString("cognome"));
                docente.setSesso(resultSet.getString("sesso"));

                int i = 0;
                while (!resultSet.getString("nome_ruolo").equals(ruoli.get(i))) {
                    i++;
                }
                liste.get(i).add(docente);
            }

            elencoDocenti.setElencoDocenti(liste);
            elencoDocenti.setRuoli(ruoli);

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

        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/Jsp/JspAmministratore/ElencoDocentiDaModificareJsp.jsp");
        request.setAttribute("elenco_docenti", elencoDocenti);
        dispatcher.forward(request, response);
    }

}
