package it.unimol.profiles.servlet;

import it.unimol.profiles.ManagerDocenti;
import it.unimol.profiles.beans.utils.Docente;
import it.unimol.profiles.beans.utils.ElencoSezioniPersonalizzate;
import it.unimol.profiles.exceptions.DocenteInesistenteException;
import it.unimol.profiles.exceptions.RisorsaNonPresenteException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Stefano
 */
public abstract class SezioneServlet extends HttpServlet {

    public static final String PERCORSO_FOTO_PROFILO_DEFAULT = "Images/profilo-default.png";

    protected Docente getDocenteDallaUrl(HttpServletRequest request) {
        Docente docente = new Docente();
        docente.setId(((String) request.getParameter("id")));
        docente.setNome(((String) request.getParameter("nome")));
        docente.setCognome(((String) request.getParameter("cognome")));
        return docente;
    }

    protected String getMessaggioDocenteNonTrovato(Docente docente) {
        return "Il docente richiesto "
                + "(nome = " + docente.getNome() + ", "
                + "cognome = " + docente.getCognome() + ", "
                + "id = " + docente.getId() + ") "
                + "non è presente nel database";
    }

    protected String getPercorsoFotoProfilo(Docente docente) throws DocenteInesistenteException {
        try {
            return ManagerDocenti.getInstance().getPercorsoFotoDocente(docente);
        } catch (RisorsaNonPresenteException ex) {
            return PERCORSO_FOTO_PROFILO_DEFAULT;
        }
    }

    protected ElencoSezioniPersonalizzate getElencoSezioniPersonalizzate(Docente docente) throws DocenteInesistenteException{
        return ManagerDocenti.getInstance().getElencoSezioniPersonalizzate(docente);
    }
    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
