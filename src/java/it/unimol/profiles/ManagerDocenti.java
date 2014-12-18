package it.unimol.profiles;

import it.unimol.profiles.beans.pagine.docente.sezioniPersonalizzate.SezionePersonalizzata;
import it.unimol.profiles.beans.utils.*;
import it.unimol.profiles.beans.pagine.docente.*;
import it.unimol.profiles.exceptions.DocenteInesistenteException;
import java.io.File;

/**
 *
 * @author Stefano
 */
public class ManagerDocenti {

    private static ManagerDocenti singletonManagerDocenti;

    private ManagerDocenti() {
    }

    public static ManagerDocenti getInstance() {
        if (singletonManagerDocenti == null) {
            singletonManagerDocenti = new ManagerDocenti();
        }
        return singletonManagerDocenti;
    }

    public void setInfoGeneraliDocente(Docente docente, InformazioniGeneraliDocente informazioniGeneraliDocente) throws DocenteInesistenteException {

    }

    public void setCurriculumDocente(Docente docente, CurriculumDocente curriculumDocente) throws DocenteInesistenteException {
        //TODO
    }

    public void setPubblicazioniDocente(Docente docente, PubblicazioniDocente PubblicazioniDocente) throws DocenteInesistenteException {
        //TODO inserire nel db
    }

    public void setSezionePersonalizzata(Docente docente, SezionePersonalizzata sezionePersonalizzata) throws DocenteInesistenteException {
        //inserire nel db;
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

        //todo eliminare il professore in questione dal db
    }
}
