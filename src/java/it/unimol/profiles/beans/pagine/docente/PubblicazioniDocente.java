package it.unimol.profiles.beans.pagine.docente;

/**
 *
 * @author Stefano
 */
public class PubblicazioniDocente {

    String bibTexLink;

    public PubblicazioniDocente() {

    }

    public String getBibTexLink() {
        return bibTexLink;
    }

    public void setBibTexLink(String bibTexLink) {
        this.bibTexLink = bibTexLink;
    }

    public static PubblicazioniDocente getPubblicazioniDocenteStub() {
        PubblicazioniDocente stub = new PubblicazioniDocente();
        stub.setBibTexLink("../../../Risorse/fausto_fasano_1/pubblicazioni/pubblicazioni_fausto_fasano.bib");
        return stub;
    }
}
