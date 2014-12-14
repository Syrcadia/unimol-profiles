package it.unimol.profiles;

import it.unimol.profiles.beans.utils.*;
import it.unimol.profiles.beans.pagine.*;
import it.unimol.profiles.beans.pagine.docente.*;
import it.unimol.profiles.exceptions.DocenteNonTrovatoException;
import it.unimol.profiles.exceptions.RisorsaNonPresenteException;
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
                    + "id_docente =" + docente.getId() + " "
                    + "AND nome ='" + docente.getNome() + "' "
                    + "AND cognome ='" + docente.getCognome() + "'");
            
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

    public InformazioniGeneraliDocente getInfoGeneraliDocente(Docente docente) throws DocenteNonTrovatoException {
        
        if (!esisteDocente(docente)){
            throw new DocenteNonTrovatoException();
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
                    + "WHERE id_docente = " + docente.getId());
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

    public void setInfoGeneraliDocente(Docente docente, InformazioniGeneraliDocente informazioniGeneraliDocente) throws DocenteNonTrovatoException {
        if (!esisteDocente(docente)){
            throw new DocenteNonTrovatoException();
        }
        //TODO
    }

    public InsegnamentiDocente getInsegnamentiDocente(Docente docente) throws DocenteNonTrovatoException,RisorsaNonPresenteException{

        if (!esisteDocente(docente)){
            throw new DocenteNonTrovatoException();
        }
        
        InsegnamentiDocente insegnamentiDocente = null;
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

    public CurriculumDocente getCurriculumDocente(Docente docente) throws DocenteNonTrovatoException,RisorsaNonPresenteException {
        if (!esisteDocente(docente)){
            throw new DocenteNonTrovatoException();
        }
        
        return StubFactory.getCurriculumDocenteStub();
    }

    public void setCurriculumDocente(Docente docente, CurriculumDocente curriculumDocente) throws DocenteNonTrovatoException {
        //TODO
    }

    public PubblicazioniDocente getPubblicazioniDocente(Docente docente) throws DocenteNonTrovatoException,RisorsaNonPresenteException {
        if (!esisteDocente(docente)){
            throw new DocenteNonTrovatoException();
        }
        
        return StubFactory.getPubblicazioniDocenteStub();
    }

    public void setPubblicazioniDocente(Docente docente, PubblicazioniDocente PubblicazioniDocente) throws DocenteNonTrovatoException {
        //TODO inserire nel db
    }

    public RicevimentoStudenti getRicevimentoStudenti(Docente docente) throws DocenteNonTrovatoException,RisorsaNonPresenteException {
        if (!esisteDocente(docente)){
            throw new DocenteNonTrovatoException();
        }
        
        return StubFactory.getRicevimentoStudentiStub();
    }

    public ElencoSezioniPersonalizzate getElencoSezioniPersonalizzate(Docente docente) throws DocenteNonTrovatoException {
        return StubFactory.getElencoSezioniPersonalizzateStub();
    }

    public SezionePersonalizzata getSezionePersonalizzata(Docente docente, int idSezione) throws DocenteNonTrovatoException,RisorsaNonPresenteException {
        if (!esisteDocente(docente)){
            throw new DocenteNonTrovatoException();
        }
        
        return null;
    }

    public void setSezionePersonalizzata(Docente docente, SezionePersonalizzata sezionePersonalizzata) throws DocenteNonTrovatoException {
        //inserire nel db;
    }

    public String getPercorsoFotoDocente(Docente docente) throws DocenteNonTrovatoException,RisorsaNonPresenteException { //ritorna la posizione della foto
        
        if (!esisteDocente(docente)){
            throw new DocenteNonTrovatoException();
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
                    + "WHERE id_docente = " + docente.getId());
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
                    + "/foto/foto_profilo/"
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

    public void eliminaDocente(Docente docente) throws DocenteNonTrovatoException {
        if (!esisteDocente(docente)){
            throw new DocenteNonTrovatoException();
        }
        //todo eliminare il professore in questione dal db
    }

    public static String readFile(String path, Charset encoding)
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}
