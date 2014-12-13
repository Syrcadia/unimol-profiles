package it.unimol.profiles.beans.pagine;

import it.unimol.profiles.beans.utils.Docente;
import java.util.ArrayList;

/**
 *
 * @author Stefano
 */
public class RisultatiRicerca {

    private ArrayList<Docente> listaDocenti;

    public ArrayList getListaDocenti() {
        return listaDocenti;
    }

    public void setListaDocenti(ArrayList listaDocenti) {
        this.listaDocenti = listaDocenti;
    }


    public static RisultatiRicerca getStub() {

        ArrayList<Docente> listaDocenti = new ArrayList();
        for (int i = 0; i < 10; i++) {
            Docente nextDocente = Docente.getStub();
            listaDocenti.add(nextDocente);
        }

        RisultatiRicerca stub = new RisultatiRicerca();
        stub.setListaDocenti(listaDocenti);

        return stub;
    }

}
