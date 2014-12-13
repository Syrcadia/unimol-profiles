package it.unimol.profiles;

import it.unimol.profiles.beans.utils.*;
import it.unimol.profiles.beans.pagine.*;
import it.unimol.profiles.beans.pagine.docente.*;
import it.unimol.profiles.stubs.StubFactory;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    public ElencoDocenti getListaDocenti() {

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
                nextDocente.setId(resultSet.getString("id_docente"));
                nextDocente.setNome(resultSet.getString("nome"));
                nextDocente.setCognome(resultSet.getString("cognome"));
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

    public RisultatiRicerca getRisultatiRicerca(String parametroNome, String parametroCognome) {
        return StubFactory.getRisultatiRicercaStub(); //Questo per ora non si fa
    }

    public InformazioniGeneraliDocente getInfoGeneraliDocente(Docente docente) throws Exception {
        InformazioniGeneraliDocente informazioniGeneraliDocente = new InformazioniGeneraliDocente();

        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet;

            resultSet = statement.executeQuery(""
                    + "SELECT nome, cognome, dipartimento, ruolo "
                    + "FROM docenti "
                    + "WHERE id_docente= " + docente.getId());
            resultSet.next();
            informazioniGeneraliDocente.setNome(resultSet.getString("nome"));
            informazioniGeneraliDocente.setCognome(resultSet.getString("cognome"));
            informazioniGeneraliDocente.setDipartimento(resultSet.getString("dipartimento"));
            informazioniGeneraliDocente.setRuolo(resultSet.getString("ruolo"));
            
            resultSet = statement.executeQuery(""
                    + "SELECT email "
                    + "FROM email "
                    + "WHERE id_docente= " + docente.getId());
            ArrayList<String> emails = new ArrayList<>();
            while(resultSet.next()){
                emails.add(resultSet.getString("email"));
            }
            informazioniGeneraliDocente.setEmail(emails);
            
            resultSet = statement.executeQuery(""
                    + "SELECT num_telefono "
                    + "FROM telefono "
                    + "WHERE id_docente= " + docente.getId());
            ArrayList<String> telefoni = new ArrayList<>();
            while(resultSet.next()){
                telefoni.add(resultSet.getString("num_telefono"));
            }
            informazioniGeneraliDocente.setTelefono(telefoni);
                        

            resultSet.close(); //non dimenticare 
            statement.close(); //queste due istruzioni!!!
        } catch (SQLException ex) {
            Logger.getLogger(ManagerDocenti.class.getName()).log(Level.SEVERE, null, ex); //cosa fare in caso di errore del database?
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

    public void setInfoGeneraliDocente(Docente docente, InformazioniGeneraliDocente informazioniGeneraliDocente) {
        //TODO inserire nel db
    }

    public InsegnamentiDocente getInsegnamentiDocente(Docente docente) throws Exception {
        InsegnamentiDocente insegnamentiDocente = new InsegnamentiDocente();
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(""
                    + "SELECT id_pagina_insegnamenti "
                    + "FROM docenti "
                    + "WHERE id_docente = " + docente.getId());
            resultSet.next();

            String idPaginaInsegnamenti = resultSet.getString("id_pagina_insegnamenti");

            Document document = Jsoup.connect("http://docenti.unimol.it/index.php?u=" + idPaginaInsegnamenti + "&id=2").get();
            insegnamentiDocente.setTestoFormattatoHtml(document.getElementsByClass("insidebox").html());

            resultSet.close(); //non dimenticare 
            statement.close(); //queste due istruzioni!!!
        } catch (SQLException ex) {
            Logger.getLogger(ManagerDocenti.class.getName()).log(Level.SEVERE, null, ex); //cosa fare in caso di errore del database?
        } finally { //il contenuto del finally è fondamentale per il funzionamento del connection pool
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception ignore) {
                }
            }
        }

        return insegnamentiDocente;

    }

    public CurriculumDocente getCurriculumDocente(Docente docente) throws Exception {
        if (!"Fausto".equals(docente.getNome()) || !"Fasano".equals(docente.getCognome()) || !"1".equals(docente.getId())) {
            throw new Exception();
        }
        return StubFactory.getCurriculumDocenteStub();
    }

    public void setCurriculumDocente(Docente docente, CurriculumDocente curriculumDocente) {
        //TODO inserire nel db
    }

    public PubblicazioniDocente getPubblicazioniDocente(Docente docente) throws Exception {
        if (!"Fausto".equals(docente.getNome()) || !"Fasano".equals(docente.getCognome()) || !"1".equals(docente.getId())) {
            throw new Exception();
        }
        return StubFactory.getPubblicazioniDocenteStub();
    }

    public void setPubblicazioniDocente(Docente docente, PubblicazioniDocente PubblicazioniDocente) {
        //TODO inserire nel db
    }

    public RicevimentoStudenti getRicevimentoStudenti(Docente docente) throws Exception {
        if (!"Fausto".equals(docente.getNome()) || !"Fasano".equals(docente.getCognome()) || !"1".equals(docente.getId())) {
            throw new Exception();
        }
        return StubFactory.getRicevimentoStudentiStub();
    }

    public ElencoSezioniPersonalizzate getElencoSezioniPersonalizzate(Docente docente) throws Exception {
        return StubFactory.getElencoSezioniPersonalizzateStub();
    }

    public SezionePersonalizzata getSezionePersonalizzata(Docente docente, int idSezione) throws Exception {
        if (!"Fausto".equals(docente.getNome()) || !"Fasano".equals(docente.getCognome()) || !"1".equals(docente.getId())) {
            throw new Exception();
        }
        return null;
    }

    public void setSezionePersonalizzata(Docente docente, SezionePersonalizzata sezionePersonalizzata) throws Exception {
        //inserire nel db;
    }

    public String getFotoDocente(Docente docente) throws Exception { //ritorna la posizione della foto

        return StubFactory.getFotoDocenteStub();
    }

    private void inserisciFile(File file, String directory) {
        /*
         salva il file passato nella cartella identificata dalla stringa directory
         */
    }

    public void inserisciDocente() {
        //todo inserire il professore in questione nel db
    }

    public void eliminaDocente() {
        //todo eliminare il professore in questione dal db
    }

    public static String readFile(String path, Charset encoding)
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}
