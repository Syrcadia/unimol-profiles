package it.unimol.profiles;

/**
 *
 * @author Stefano
 */
public class ManagerAutenticazione {

    private static ManagerAutenticazione singletonManagerAutenticazione;

    private ManagerAutenticazione() {
    }

    public static ManagerAutenticazione getInstance() {
        if (singletonManagerAutenticazione == null) {
            singletonManagerAutenticazione = new ManagerAutenticazione();
        }
        return singletonManagerAutenticazione;
    }

    public boolean isAmministratore(String userID) {
        return true;
    }
}
