package it.unimol.profiles.servlet;

import it.unimol.profiles.beans.pagine.docente.RicevimentoStudenti;
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
@WebServlet(name = "RicevimentoStudenti", urlPatterns = {"/RicevimentoStudenti"})
public class RicevimentoStudentiServlet extends SezioneServlet {

    @Override
    protected void setCustomRequestAttributes(HttpServletRequest request, HttpServletResponse response, Docente docente) throws ServletException, IOException {
        try {
            RicevimentoStudenti ricevimentoStudenti = this.getRicevimentoStudenti(docente, getServletConfig().getServletContext().getRealPath(""));
            request.setAttribute("ricevimento_studenti", ricevimentoStudenti);
        } catch (RisorsaNonPresenteException ex) {
            request.setAttribute("ricevimento_studenti", null);
        }
    }

    @Override
    protected String getJspToForward() {
        return "WEB-INF/Jsp/JspDocenti/RicevimentoStudentiJsp.jsp";
    }

    public RicevimentoStudenti getRicevimentoStudenti(Docente docente, String contextPath) throws RisorsaNonPresenteException {
        RicevimentoStudenti ricevimentoStudenti = new RicevimentoStudenti();
        String percorsoOrarioRicevimentoHtml = "Risorse/" + docente.getNome().toLowerCase() + "_" + docente.getCognome().toLowerCase() + "_" + docente.getId() + "/orario_ricevimento/orario_ricevimento_" + docente.getNome().toLowerCase() + "_" + docente.getCognome().toLowerCase() + ".html";

        File orarioRicevimentoHtml = new File(contextPath + "/" + percorsoOrarioRicevimentoHtml);

        if (!orarioRicevimentoHtml.isFile()) {
            throw new RisorsaNonPresenteException();
        } else {
            ricevimentoStudenti.setRicevimentoStudentiLink(percorsoOrarioRicevimentoHtml);
        }

        return ricevimentoStudenti;
    }

}
