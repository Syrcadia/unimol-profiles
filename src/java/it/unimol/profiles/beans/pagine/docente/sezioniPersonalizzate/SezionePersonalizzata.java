package it.unimol.profiles.beans.pagine.docente.sezioniPersonalizzate;

import java.util.ArrayList;

/**
 *
 * @author Stefano
 */
public class SezionePersonalizzata extends ArrayList<Contenuto> {

    private String nomeSezione;
    private String idSezione;

    public String getIdSezione() {
        return idSezione;
    }

    public void setIdSezione(String idSezione) {
        this.idSezione = idSezione;
    }

    public String getNomeSezione() {
        return nomeSezione;
    }

    public void setNomeSezione(String nomeSezione) {
        this.nomeSezione = nomeSezione;
    }

}
