package it.unimol.profiles;

import it.unimol.profiles.beans.pagine.docente.sezioniPersonalizzate.SezionePersonalizzata;
import it.unimol.profiles.beans.utils.*;
import it.unimol.profiles.beans.pagine.docente.*;
import it.unimol.profiles.beans.pagine.docente.sezioniPersonalizzate.ContenutoFile;
import it.unimol.profiles.beans.pagine.docente.sezioniPersonalizzate.ContenutoFoto;
import it.unimol.profiles.beans.pagine.docente.sezioniPersonalizzate.ContenutoTesto;
import it.unimol.profiles.exceptions.DocenteInesistenteException;
import it.unimol.profiles.exceptions.RisorsaNonPresenteException;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author Stefano
 */
public class ManagerDocenti {

    private static ManagerDocenti singletonManagerDocenti;

    private static final String DB_DOCENTI_RUOLO_PROFESSORE_ORDINARIO = "Professore ordinario";
    private static final String DB_DOCENTI_RUOLO_PROFESSORE_ASSOCIATO = "Professore associato";
    private static final String DB_DOCENTI_RUOLO_RICERCATORE = "Ricercatore";
    private static final String DB_DOCENTI_RUOLO_RICERCATORE_A_TEMPO_DETERMINATO = "Ricercatore a tempo determinato";

    private ManagerDocenti() {
    }

    public static ManagerDocenti getInstance() {
        if (singletonManagerDocenti == null) {
            singletonManagerDocenti = new ManagerDocenti();
        }
        return singletonManagerDocenti;
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

    public boolean esisteDocente(Docente docente) {
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

    public InformazioniGeneraliDocente getInfoGeneraliDocente(Docente docente) throws DocenteInesistenteException {

        if (!esisteDocente(docente)) {
            throw new DocenteInesistenteException();
        }

        InformazioniGeneraliDocente informazioniGeneraliDocente = new InformazioniGeneraliDocente();

        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet;

            resultSet = statement.executeQuery(""
                    + "SELECT nome, cognome, dipartimento, ruolo "
                    + "FROM docenti "
                    + "WHERE id = " + docente.getId());
            resultSet.next();
            informazioniGeneraliDocente.setNome(resultSet.getString("nome"));
            informazioniGeneraliDocente.setCognome(resultSet.getString("cognome"));
            informazioniGeneraliDocente.setDipartimento(resultSet.getString("dipartimento"));
            informazioniGeneraliDocente.setRuolo(resultSet.getString("ruolo"));

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

    public void setInfoGeneraliDocente(Docente docente, InformazioniGeneraliDocente informazioniGeneraliDocente) throws DocenteInesistenteException {
        if (!esisteDocente(docente)) {
            throw new DocenteInesistenteException();
        }
        //TODO
    }

    public InsegnamentiDocente getInsegnamentiDocente(Docente docente) throws DocenteInesistenteException, RisorsaNonPresenteException {

        if (!esisteDocente(docente)) {
            throw new DocenteInesistenteException();
        }

        InsegnamentiDocente insegnamentiDocente = null;
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(""
                    + "SELECT id_pagina_insegnamenti "
                    + "FROM docenti "
                    + "WHERE id = " + docente.getId());
            resultSet.next();

            String idPaginaInsegnamenti = resultSet.getString("id_pagina_insegnamenti");

            if (idPaginaInsegnamenti != null) {
                insegnamentiDocente = new InsegnamentiDocente();
                Document document = Jsoup.connect("http://docenti.unimol.it/index.php?u=" + idPaginaInsegnamenti + "&id=2").get();
                insegnamentiDocente.setTestoFormattatoHtml(document.getElementsByClass("insidebox").html());
            } else {
                throw new RisorsaNonPresenteException();
            }
            resultSet.close();
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(ManagerDocenti.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(ManagerDocenti.class.getName()).log(Level.SEVERE, null, "ERRORE DEL DATABASE, CONTROLLARE CHE SIA ATTIVO IL SERVIZIO MYSQL E CHE I PARAMETRI DELLA CLASSE ParametriDatabase SIANO IMPOSTATI CORRETTAMENTE"); //cosa fare in caso di errore del database?
        } catch (IOException ex) {
            Logger.getLogger(ManagerDocenti.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(ManagerDocenti.class.getName()).log(Level.SEVERE, null, "SI E' VERIFICATO UN ERRORE CON LA LIBRERIA JSOUP");
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception ignore) {
                }
            }
        }
        return insegnamentiDocente;
    }

    public CurriculumDocente getCurriculumDocente(Docente docente, String contextPath) throws DocenteInesistenteException, RisorsaNonPresenteException {
        if (!esisteDocente(docente)) {
            throw new DocenteInesistenteException();
        }

        CurriculumDocente curriculumDocente = new CurriculumDocente();
        String percorsoCurriculumHtml = "Risorse/" + docente.getNome().toLowerCase() + "_" + docente.getCognome().toLowerCase() + "_" + docente.getId() + "/curriculum/curriculum_" + docente.getNome().toLowerCase() + "_" + docente.getCognome().toLowerCase() + ".html";
        String percorsoCurriculumPdf = "Risorse/" + docente.getNome().toLowerCase() + "_" + docente.getCognome().toLowerCase() + "_" + docente.getId() + "/curriculum/curriculum_" + docente.getNome().toLowerCase() + "_" + docente.getCognome().toLowerCase() + ".pdf";

        File curriculumHtml = new File(contextPath + "/" + percorsoCurriculumHtml);
        File curriculumPdf = new File(contextPath + "/" + percorsoCurriculumPdf);

        if (!curriculumHtml.isFile() && !curriculumPdf.isFile()) {
            throw new RisorsaNonPresenteException();
        } else {
            if (curriculumHtml.isFile()) {
                curriculumDocente.setHtmlLink(percorsoCurriculumHtml);
            }
            if (curriculumPdf.isFile()) {
                curriculumDocente.setPdfLink(percorsoCurriculumPdf);
            }
        }
        return curriculumDocente;
    }

    public void setCurriculumDocente(Docente docente, CurriculumDocente curriculumDocente) throws DocenteInesistenteException {
        //TODO
    }

    public PubblicazioniDocente getPubblicazioniDocente(Docente docente, String contextPath) throws DocenteInesistenteException, RisorsaNonPresenteException {
        if (!esisteDocente(docente)) {
            throw new DocenteInesistenteException();
        }

        PubblicazioniDocente pubblicazioniDocente = new PubblicazioniDocente();
        String percorsoPubblicazioniBibTex = "Risorse/" + docente.getNome().toLowerCase() + "_" + docente.getCognome().toLowerCase() + "_" + docente.getId() + "/pubblicazioni/pubblicazioni_" + docente.getNome().toLowerCase() + "_" + docente.getCognome().toLowerCase() + ".bib";

        File pubblicazioniBibTex = new File(contextPath + "/" + percorsoPubblicazioniBibTex);

        if (!(pubblicazioniBibTex.isFile())) {
            throw new RisorsaNonPresenteException();
        } else {
            pubblicazioniDocente.setBibTexLink(percorsoPubblicazioniBibTex);
        }

        return pubblicazioniDocente;

    }

    public void setPubblicazioniDocente(Docente docente, PubblicazioniDocente PubblicazioniDocente) throws DocenteInesistenteException {
        //TODO inserire nel db
    }

    public RicevimentoStudenti getRicevimentoStudenti(Docente docente, String contextPath) throws DocenteInesistenteException, RisorsaNonPresenteException {

        if (!esisteDocente(docente)) {
            throw new DocenteInesistenteException();
        }

        RicevimentoStudenti ricevimentoStudenti = new RicevimentoStudenti();
        String percorsoOrarioRicevimentoHtml = "Risorse/" + docente.getNome().toLowerCase() + "_" + docente.getCognome().toLowerCase() + "_" + docente.getId() + "/orario_ricevimento/orario_ricevimento_" + docente.getNome().toLowerCase() + "_" + docente.getCognome().toLowerCase() + ".html";

        File orarioRicevimentoHtml = new File(contextPath + "/" + percorsoOrarioRicevimentoHtml);

        if (!orarioRicevimentoHtml.isFile()) {
            throw new RisorsaNonPresenteException();
        } else {
            ricevimentoStudenti.setRicevimentoStudentiLink(percorsoOrarioRicevimentoHtml);
        }

        return ricevimentoStudenti;
    }

    public ElencoSezioniPersonalizzate getElencoSezioniPersonalizzate(Docente docente) throws DocenteInesistenteException {
        if (!esisteDocente(docente)) {
            throw new DocenteInesistenteException();
        }

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

    public SezionePersonalizzata getSezionePersonalizzata(Docente docente, int idSezione) throws DocenteInesistenteException, RisorsaNonPresenteException {
        if (!esisteDocente(docente)) {
            throw new DocenteInesistenteException();
        }

        Connection connection = null;
        SezionePersonalizzata sezionePersonalizzata = new SezionePersonalizzata();

        try {
            connection = ConnectionPool.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(""
                    + "SELECT * "
                    + "FROM sezioni_docenti "
                    + "WHERE id_sezione = " + idSezione + " "
                    + "AND id_docente = " + docente.getId());
            if (!resultSet.next()) {
                throw new RisorsaNonPresenteException();
            }
            sezionePersonalizzata.setNomeSezione(resultSet.getString("nome_sezione"));
            sezionePersonalizzata.setIdSezione(resultSet.getString("id_sezione"));
            String percorsoCartellaSezione = "Risorse/" + docente.getNome().toLowerCase() + "_" + docente.getCognome().toLowerCase() + "_" + docente.getId() + "/sezione_" + sezionePersonalizzata.getNomeSezione().replaceAll(" ", "_").toLowerCase() + "_" + sezionePersonalizzata.getIdSezione();

            resultSet = statement.executeQuery(""
                    + "SELECT id_risorsa, tipo_risorsa, ordine "
                    + "FROM contenuti "
                    + "WHERE id_sezione = " + idSezione + " "
                    + "ORDER BY ordine");

            ResultSet auxResultSet = null;
            Statement auxStatement = connection.createStatement();

            while (resultSet.next()) {
                switch (resultSet.getString("tipo_risorsa")) {
                    case "testo":
                        auxResultSet = auxStatement.executeQuery(""
                                + "SELECT nome_testo "
                                + "FROM testi "
                                + "WHERE id = " + resultSet.getString("id_risorsa"));
                        auxResultSet.next();
                        ContenutoTesto contenutoTesto = new ContenutoTesto();
                        contenutoTesto.setLinkHtml(percorsoCartellaSezione + "/" + auxResultSet.getString("nome_testo"));
                        contenutoTesto.setOrdine(Integer.parseInt(resultSet.getString("ordine")));
                        sezionePersonalizzata.add(contenutoTesto);
                        break;
                    case "file":
                        auxResultSet = auxStatement.executeQuery(""
                                + "SELECT nome_file, descrizione, estensione "
                                + "FROM files "
                                + "WHERE id = " + resultSet.getString("id_risorsa"));
                        auxResultSet.next();
                        ContenutoFile contenutoFile = new ContenutoFile();
                        contenutoFile.setFileLink(percorsoCartellaSezione + "/" + auxResultSet.getString("nome_file"));
                        contenutoFile.setDescrizioneFile(auxResultSet.getString("descrizione"));
                        contenutoFile.setOrdine(Integer.parseInt(resultSet.getString("ordine")));
                        sezionePersonalizzata.add(contenutoFile);
                        break;
                    case "foto":
                        auxResultSet = auxStatement.executeQuery(""
                                + "SELECT nome_foto, descrizione "
                                + "FROM foto "
                                + "WHERE id= " + resultSet.getString("id_risorsa"));
                        auxResultSet.next();
                        ContenutoFoto contenutoFoto = new ContenutoFoto();
                        contenutoFoto.setLinkFoto(percorsoCartellaSezione + "/" + auxResultSet.getString("nome_foto"));
                        contenutoFoto.setDescrizioneFoto(auxResultSet.getString("descrizione"));
                        contenutoFoto.setOrdine(Integer.parseInt(resultSet.getString("ordine")));
                        sezionePersonalizzata.add(contenutoFoto);
                        break;
                }
            }
            if (auxResultSet != null) {
                auxResultSet.close();
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

        return sezionePersonalizzata;
    }

    public void setSezionePersonalizzata(Docente docente, SezionePersonalizzata sezionePersonalizzata) throws DocenteInesistenteException {
        //inserire nel db;
    }

    public String getPercorsoFotoDocente(Docente docente) throws DocenteInesistenteException, RisorsaNonPresenteException { //ritorna la posizione della foto

        if (!esisteDocente(docente)) {
            throw new DocenteInesistenteException();
        }

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
            throw new RisorsaNonPresenteException();
        } else {
            fotoPath = "Risorse/"
                    + docente.getNomeCartella()
                    + "/foto_profilo/"
                    + nomeFotoProfilo;
        }
        return fotoPath;
    }

    private void inserisciFile(File file, String directory) {
        /*
         salva il file passato nella cartella identificata dalla stringa directory
         */
    }

    public void inserisciDocente() {
        //todo inserire il professore in questione nel db
    }

    public void eliminaDocente(Docente docente) throws DocenteInesistenteException {
        if (!esisteDocente(docente)) {
            throw new DocenteInesistenteException();
        }
        //todo eliminare il professore in questione dal db
    }
}
