package it.unimol.profiles.servlet.sviluppo;

import it.unimol.profiles.ManagerDocenti;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Stefano
 */
public class Log {

    public static void log(String message) {
        Logger.getLogger(Log.class.getName()).log(Level.INFO, message); //cosa fare in caso di errore del database?

    }
}
