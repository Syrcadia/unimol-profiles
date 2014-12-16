package it.unimol.profiles.beans.utils;

import java.util.ArrayList;

/**
 *
 * @author Stefano
 */
public class ElencoSezioniPersonalizzate {
    
    ArrayList<String[]> elencoSezioniPersonalizzate;
    
    public ElencoSezioniPersonalizzate(){
        elencoSezioniPersonalizzate=new ArrayList<>();
    }
    
    public void addSezione(String nomeSezione, String idSezione){
        String[] nextSezione={nomeSezione,idSezione};
        elencoSezioniPersonalizzate.add(nextSezione);
    }
    
    public String getIdSezione(int i){
        return elencoSezioniPersonalizzate.get(i)[1];
    }
    
    public String getNomeSezione(int i){
        return elencoSezioniPersonalizzate.get(i)[0];
    }
    
    public int size(){
        return elencoSezioniPersonalizzate.size();
    }
    
}
