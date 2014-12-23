package it.unimol.profiles.beans.utils;

import java.util.ArrayList;

/**
 *
 * @author Stefano
 */
public class ElencoDocenti {
    private ArrayList<ArrayList<Docente>> elencoDocenti;
    private ArrayList<String> ruoli;
                
    
    public ElencoDocenti(){
        
    }

    public ArrayList<ArrayList<Docente>> getElencoDocenti() {
        return elencoDocenti;
    }

    public void setElencoDocenti(ArrayList<ArrayList<Docente>> elencoDocenti) {
        this.elencoDocenti = elencoDocenti;
    }

    public ArrayList<String> getRuoli() {
        return ruoli;
    }

    public void setRuoli(ArrayList<String> ruoli) {
        this.ruoli = ruoli;
    }
    

}
