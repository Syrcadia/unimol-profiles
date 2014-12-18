package it.unimol.profiles.servlet;

import it.unimol.profiles.beans.pagine.docente.CurriculumDocente;
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
@WebServlet(name = "CurriculumDocente", urlPatterns = {"/CurriculumDocente"})
public class CurriculumDocenteServlet extends SezioneServlet {

    @Override
    protected void setCustomRequestAttributes(HttpServletRequest request, HttpServletResponse response, Docente docente) throws ServletException, IOException {
        try {
            CurriculumDocente curriculumDocente = this.getCurriculumDocente(docente, getServletConfig().getServletContext().getRealPath(""));
            request.setAttribute("curriculum_docente", curriculumDocente);
        } catch (RisorsaNonPresenteException ex) {
            request.setAttribute("curriculum_docente", null);
        } 
    }

    @Override
    protected String getJspToForward() {
        return "WEB-INF/Jsp/JspDocenti/CurriculumDocenteJsp.jsp";
    }
    
    public CurriculumDocente getCurriculumDocente(Docente docente, String contextPath) throws RisorsaNonPresenteException {
        CurriculumDocente curriculumDocente = new CurriculumDocente();
        String percorsoCurriculumHtml = "Risorse/" + docente.getNome().toLowerCase() + "_" + docente.getCognome().toLowerCase() + "_" + docente.getId() + "/curriculum/curriculum_" + docente.getNome().toLowerCase() + "_" + docente.getCognome().toLowerCase() + ".html";
        String percorsoCurriculumPdf = "Risorse/" + docente.getNome().toLowerCase() + "_" + docente.getCognome().toLowerCase() + "_" + docente.getId() + "/curriculum/curriculum_" + docente.getNome().toLowerCase() + "_" + docente.getCognome().toLowerCase() + ".pdf";

        File curriculumHtml = new File(contextPath + "/" + percorsoCurriculumHtml);
        File curriculumPdf = new File(contextPath + "/" + percorsoCurriculumPdf);

        if (!curriculumHtml.isFile() && !curriculumPdf.isFile()) {
            throw new RisorsaNonPresenteException();
        } else {
            if (curriculumHtml.isFile()) {
                curriculumDocente.setHtmlLink(percorsoCurriculumHtml);
            }
            if (curriculumPdf.isFile()) {
                curriculumDocente.setPdfLink(percorsoCurriculumPdf);
            }
        }
        return curriculumDocente;
    }

    

}
