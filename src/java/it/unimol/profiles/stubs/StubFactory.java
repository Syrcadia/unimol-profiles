package it.unimol.profiles.stubs;

import it.unimol.profiles.beans.pagine.RisultatiRicerca;
import it.unimol.profiles.beans.pagine.docente.*;
import it.unimol.profiles.beans.utils.*;
import java.util.ArrayList;

/**
 *
 * @author Stefano
 */
public class StubFactory {

    private StubFactory() {

    }

    public static InformazioniGeneraliDocente getInformazioniGeneraliDocenteStub() {
        return InformazioniGeneraliDocente.getStub();
    }

    public static ElencoDocenti getElencoDocentiStub() {
        ElencoDocenti elencoDocenti = new ElencoDocenti();
        
        
            ArrayList<Docente> listaProfessoriOrdinari= new ArrayList();
            ArrayList<Docente> listaProfessoriAssociati= new ArrayList();
            ArrayList<Docente> listaRicercatori= new ArrayList();
            ArrayList<Docente> listaRicercatoriATempoDeterminato= new ArrayList();
            
        for (int i = 0; i < 10; i++) {
            Docente nextDocente = Docente.getStub();
            listaProfessoriOrdinari.add(nextDocente);
            listaProfessoriAssociati.add(nextDocente);
            listaRicercatori.add(nextDocente);
            listaRicercatoriATempoDeterminato.add(nextDocente);
        }
        
        elencoDocenti.setListaProfessoriAssociati(listaProfessoriAssociati);
        elencoDocenti.setListaProfessoriOrdinari(listaProfessoriOrdinari);
        elencoDocenti.setListaRicercatori(listaRicercatori);
        elencoDocenti.setListaRicercatoriATempoDeterminato(listaRicercatoriATempoDeterminato);
        
        return elencoDocenti;
    }

    public static RisultatiRicerca getRisultatiRicercaStub() {
        return RisultatiRicerca.getStub();
    }
    
    public static InsegnamentiDocente getInsegnamentiDocenteStub() {
        return InsegnamentiDocente.getStub();
    }
    
    public static CurriculumDocente getCurriculumDocenteStub() {
        return CurriculumDocente.getStub();
    }
    
    public static PubblicazioniDocente getPubblicazioniDocenteStub() {
        return PubblicazioniDocente.getPubblicazioniDocenteStub();
    }
    
    public static RicevimentoStudenti getRicevimentoStudentiStub() {
        return RicevimentoStudenti.getStub();
    }
    
    public static String getFotoDocenteStub(){
        return "Risorse/fausto_fasano_1/foto/foto_profilo/wedding.jpg";
    }
}
