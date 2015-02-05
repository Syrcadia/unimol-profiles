package it.unimol.profiles;

import it.unimol.profiles.exceptions.UploadNonValidoException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Stefano
 */
public class ManagerFileSystem {
    
    public static final String destinazioneFileProva="Dev/ProvaInserimento/";
    
    public static void inserisciFile(HttpServletRequest request,String destinazioneFile) throws UploadNonValidoException{
        destinazioneFile=destinazioneFileProva;
    }
    
}
