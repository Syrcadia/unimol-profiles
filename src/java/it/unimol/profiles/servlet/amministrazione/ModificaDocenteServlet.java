package it.unimol.profiles.servlet.amministrazione;

import it.unimol.profiles.SQLLayer.ConnectionPool;
import it.unimol.profiles.ManagerDocenti;
import it.unimol.profiles.ManagerFileSystem;
import it.unimol.profiles.beans.pagine.docente.InformazioniGeneraliDocente;
import it.unimol.profiles.beans.utils.Docente;
import it.unimol.profiles.beans.utils.ElencoDocenti;
import it.unimol.profiles.exceptions.UploadNonValidoException;
import it.unimol.profiles.servlet.InformazioniGeneraliDocenteServlet;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
                if (null != part.getName()) {
                    switch (part.getName()) {
                        case "action": {
                            Scanner scanner = new Scanner(part.getInputStream());
                            action = scanner.nextLine();
                            break;
                        }
                        case "nome_docente": {
                            Scanner scanner = new Scanner(part.getInputStream());
                            nomeDocente = scanner.nextLine();
                            break;
                        }
                        case "cognome_docente": {
                            Scanner scanner = new Scanner(part.getInputStream());
                            cognomeDocente = scanner.nextLine();
                            break;
                        }
                    }
                }
            }
        } else {
            action = request.getParameter("action");
        }

        if (action != null) {

            String idDocente = request.getParameter("id_docente");
            Connection connection = null;
            PreparedStatement preparedStatement = null;

            try {
                connection = ConnectionPool.getInstance().getConnection();
                

                switch (action) {
                    case "aggiungi_indirizzo_email":

                        String newEmail = request.getParameter("email");
                        preparedStatement = connection.prepareStatement(""
                                + "INSERT INTO email (email,id_docente) "
                                + "VALUES (?,?) ");
                        preparedStatement.setString(1, newEmail);
                        preparedStatement.setString(2, idDocente);
                        preparedStatement.executeUpdate();
                        preparedStatement.close();

                        break;

                    case "rimuovi_indirizzo_email":

                        String emailToDelete = request.getParameter("email");
                        preparedStatement = connection.prepareStatement(""
                                + "DELETE FROM email "
                                + "WHERE email=? "
                                + "AND id_docente=? ");
                        preparedStatement.setString(1, emailToDelete);
                        preparedStatement.setString(2, idDocente);
                        preparedStatement.executeUpdate();
                        preparedStatement.close();

                        break;
                    case "aggiungi_contatto_telefonico":

                        String newTelefono = request.getParameter("telefono");

                        preparedStatement = connection.prepareStatement(""
                                + "INSERT INTO telefono (num_telefono,id_docente) "
                                + "VALUES (?,?) ");
                        preparedStatement.setString(1, newTelefono);
                        preparedStatement.setString(2, idDocente);
                        
                        preparedStatement.executeUpdate();
                        preparedStatement.close();

                        break;
                    case "rimuovi_contatto_telefonico":
                        String telefonoToDelete = request.getParameter("contatti");

                        preparedStatement = connection.prepareStatement(""
                                + "DELETE FROM telefono "
                                + "WHERE num_telefono=? "
                                + "AND id_docente=? ");
                        preparedStatement.setString(1, telefonoToDelete);
                        preparedStatement.setString(2, idDocente);
                        
                        preparedStatement.executeUpdate();
                        preparedStatement.close();

                        break;
                    case "modifica_ruolo":

                        String idNuovoRuolo = request.getParameter("ruoli");

                        preparedStatement = connection.prepareStatement(""
                                + "UPDATE docenti "
                                + "SET id_ruolo=? "
                                + "WHERE id=? ");
                        preparedStatement.setString(1, idNuovoRuolo);
                        preparedStatement.setString(2, idDocente);
                        
                        preparedStatement.executeUpdate();
                        preparedStatement.close();

                        break;
                    case "modifica_dipartimento":

                        String idNuovoDipartimento = request.getParameter("dipartimenti");

                        preparedStatement = connection.prepareStatement(""
                                + "UPDATE docenti "
                                + "SET id_dipartimento=? "
                                + "WHERE id=? ");
                        preparedStatement.setString(1, idNuovoDipartimento);
                        preparedStatement.setString(2, idDocente);
                        
                        preparedStatement.executeUpdate();
                        preparedStatement.close();

                        break;
                    case "configura_pagina_insegnamenti":

                        String idPaginaInsegnamenti = request.getParameter("pagina_insegnamenti");
                        
                        preparedStatement = connection.prepareStatement(""
                                + "UPDATE docenti "
                                + "SET id_pagina_insegnamenti=? "
                                + "WHERE id=? ");
                        preparedStatement.setString(1, idPaginaInsegnamenti);
                        preparedStatement.setString(2, idDocente);

                        preparedStatement.executeUpdate();
                        preparedStatement.close();

                        break;
                    case "modifica_curriculum":
                        String percorsoFilePdf = "Risorse" + File.separator + idDocente + File.separator + "curriculum";
                        String nomeFilePdf = "curriculum.pdf";
                        ManagerFileSystem.inserisciFile(request, percorsoFilePdf, nomeFilePdf);
                        break;
                    case "modifica_pubblicazioni":
                        String percorsoFileBib = "Risorse" + File.separator + idDocente + File.separator + "pubblicazioni";
                        String nomeFileBib = "pubblicazioni.bib";
                        ManagerFileSystem.inserisciFile(request, percorsoFileBib, nomeFileBib);

                        break;
                    case "modifica_orario_di_ricevimento":
                        String percorsoOrarioHtml = "Risorse" + File.separator + idDocente + File.separator + "orario_ricevimento";
                        String nomeOrarioHtml = "orario_ricevimento.html";
                        ManagerFileSystem.inserisciFile(request, percorsoOrarioHtml, nomeOrarioHtml);

                        break;
                    case "inserisci_sezione_personalizzata":
                        String newSezione = request.getParameter("nome_sezione");
                        preparedStatement = connection.prepareStatement(""
                                + "INSERT INTO sezioni_docenti (id_docente,nome_sezione) "
                                + "VALUES (?,?)");
                        preparedStatement.setString(1, idDocente);
                        preparedStatement.setString(2, newSezione);
                        
                        preparedStatement.executeUpdate();
                        preparedStatement.close();

                        break;
                    case "modifica_sezione_personalizzata":

                        break;
                    case "elimina_sezione_personalizzata":

                        break;
                    case "modifica_immagine_profilo":
                        String percorsoFotoProfilo = "Risorse" + File.separator + idDocente + File.separator + "foto_profilo";
                        String nomeFotoProfilo = null;
                        for (Part part : request.getParts()) {
                            if ("file".equals(part.getName())) {
                                nomeFotoProfilo = part.getSubmittedFileName();
                                break;
                            }
                        }
                        ManagerFileSystem.inserisciFile(request, percorsoFotoProfilo, nomeFotoProfilo);

                        preparedStatement = connection.prepareStatement(""
                                + "UPDATE docenti "
                                + "SET nome_foto_profilo=? "
                                + "WHERE id=? ");
                        preparedStatement.setString(1, nomeFotoProfilo);
                        preparedStatement.setString(2, idDocente);
                        
                        preparedStatement.executeUpdate();
                        preparedStatement.close();

                        break;
                }
                

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
                    + "FROM docenti INNER JOIN ruoli "
                    + "ON docenti.id_ruolo = ruoli.id "
                    + "ORDER BY docenti.cognome"
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
