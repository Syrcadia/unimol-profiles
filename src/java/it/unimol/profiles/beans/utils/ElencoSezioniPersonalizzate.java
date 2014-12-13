package it.unimol.profiles.beans.utils;

import java.util.ArrayList;

/**
 *
 * @author Stefano
 */
public class ElencoSezioniPersonalizzate extends ArrayList<String>{
    
    public ElencoSezioniPersonalizzate(){
        
    }
    
    public static ElencoSezioniPersonalizzate getStub(){
        
        ElencoSezioniPersonalizzate stub=new ElencoSezioniPersonalizzate();
        stub.add("Sito Web");
        stub.add("Le mie Foto");
        stub.add("Convegno 2015");
        stub.add("La sezione col nome lungo che potrebbe creare broblemi col css");
        stub.add("La sezione dopo quella lunga");
    
        return stub;
    }
    
}
