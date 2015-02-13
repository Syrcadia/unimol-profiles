package it.unimol.profiles;

import it.unimol.profiles.exceptions.UploadNonValidoException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Stefano
 */
public class ManagerFileSystem {
    
    public static final String destinazioneFileProva="Dev/ProvaInserimento/";
    public static void inserisciFile(HttpServletRequest request, String destinazioneFile, HttpServlet servlet) throws UploadNonValidoException{
        destinazioneFile=destinazioneFileProva;
    }
}