package it.unimol.profiles.beans.utils;

import java.util.ArrayList;

/**
 *
 * @author Stefano
 */
public class ElencoDocenti {
    private ArrayList<Docente> listaProfessoriOrdinari;
    private ArrayList<Docente> listaProfessoriAssociati;
    private ArrayList<Docente> listaRicercatori;
    private ArrayList<Docente> listaRicercatoriATempoDeterminato;
    
    public ElencoDocenti(){
        
    }

    public ArrayList<Docente> getListaProfessoriOrdinari() {
        return listaProfessoriOrdinari;
    }

    public void setListaProfessoriOrdinari(ArrayList<Docente> listaProfessoriOrdinari) {
        this.listaProfessoriOrdinari = listaProfessoriOrdinari;
    }

    public ArrayList<Docente> getListaProfessoriAssociati() {
        return listaProfessoriAssociati;
    }

    public void setListaProfessoriAssociati(ArrayList<Docente> listaProfessoriAssociati) {
        this.listaProfessoriAssociati = listaProfessoriAssociati;
    }

    public ArrayList<Docente> getListaRicercatori() {
        return listaRicercatori;
    }

    public void setListaRicercatori(ArrayList<Docente> listaRicercatori) {
        this.listaRicercatori = listaRicercatori;
    }

    public ArrayList<Docente> getListaRicercatoriATempoDeterminato() {
        return listaRicercatoriATempoDeterminato;
    }

    public void setListaRicercatoriATempoDeterminato(ArrayList<Docente> listaRicercatoriATempoDeterminato) {
        this.listaRicercatoriATempoDeterminato = listaRicercatoriATempoDeterminato;
    }
    
    
}
