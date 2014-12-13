package it.unimol.profiles.beans.pagine.docente;

/**
 *
 * @author Stefano
 */

public class RicevimentoStudenti {

    private String ricevimentoStudenti;

    public String getRicevimentoStudenti() {
        return ricevimentoStudenti;
    }

    public void setRicevimentoStudenti(String ricevimentoStudenti) {
        this.ricevimentoStudenti = ricevimentoStudenti;
    }

    public static RicevimentoStudenti getStub() {
        RicevimentoStudenti stub = new RicevimentoStudenti();
        stub.setRicevimentoStudenti("<div>\n"
                + "	Orario in vigore dal 1 Ottobre 2013</div>\n"
                + "<div>\n"
                + "	Luned&igrave; 14:00 - 15:00</div>\n"
                + "<div>\n"
                + "	Gioved&igrave; 14:00 - 15:00&nbsp;</div>");
        return stub;
    }

    @Override
    public String toString() {
        return ricevimentoStudenti;
    }
    
    
    
}
