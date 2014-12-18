package it.unimol.profiles.servlet;

import it.unimol.profiles.beans.pagine.docente.PubblicazioniDocente;
import it.unimol.profiles.beans.utils.Docente;
import it.unimol.profiles.exceptions.RisorsaNonPresenteException;
import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Stefano
 */
@WebServlet(name = "PubblicazioniDocente", urlPatterns = {"/PubblicazioniDocente"})
public class PubblicazioniDocenteServlet extends SezioneServlet {

    @Override
    protected void setCustomRequestAttributes(HttpServletRequest request, HttpServletResponse response, Docente docente) throws ServletException, IOException {
        try {
            PubblicazioniDocente pubblicazioniDocente = this.getPubblicazioniDocente(docente, getServletConfig().getServletContext().getRealPath(""));
            request.setAttribute("pubblicazioni_docente", pubblicazioniDocente);
        } catch (RisorsaNonPresenteException ex) {
            request.setAttribute("pubblicazioni_docente", null);
        }
    }

    @Override
    protected String getJspToForward() {
        return "WEB-INF/Jsp/JspDocenti/PubblicazioniDocenteJsp.jsp";
    }

    public PubblicazioniDocente getPubblicazioniDocente(Docente docente, String contextPath) throws RisorsaNonPresenteException {

        PubblicazioniDocente pubblicazioniDocente = new PubblicazioniDocente();
        String percorsoPubblicazioniBibTex = "Risorse/" + docente.getNome().toLowerCase() + "_" + docente.getCognome().toLowerCase() + "_" + docente.getId() + "/pubblicazioni/pubblicazioni_" + docente.getNome().toLowerCase() + "_" + docente.getCognome().toLowerCase() + ".bib";

        File pubblicazioniBibTex = new File(contextPath + "/" + percorsoPubblicazioniBibTex);

        if (!(pubblicazioniBibTex.isFile())) {
            throw new RisorsaNonPresenteException();
        } else {
            pubblicazioniDocente.setBibTexLink(percorsoPubblicazioniBibTex);
        }

        return pubblicazioniDocente;

    }

}
