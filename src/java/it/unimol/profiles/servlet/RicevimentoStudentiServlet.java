package it.unimol.profiles.servlet;

import it.unimol.profiles.ManagerDocenti;
import it.unimol.profiles.beans.pagine.docente.RicevimentoStudenti;
import it.unimol.profiles.beans.utils.Docente;
import it.unimol.profiles.exceptions.DocenteInesistenteException;
import it.unimol.profiles.exceptions.RisorsaNonPresenteException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Stefano
 */
@WebServlet(name = "RicevimentoStudenti", urlPatterns = {"/RicevimentoStudenti"})
public class RicevimentoStudentiServlet extends SezioneServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Docente docente = this.getDocenteDallaUrl(request);
        try {
            request.setAttribute("percorso_foto_profilo", this.getPercorsoFotoProfilo(docente));
            request.setAttribute("elenco_sezioni_personalizzate", this.getElencoSezioniPersonalizzate(docente));
            RicevimentoStudenti ricevimentoStudenti = ManagerDocenti.getInstance().getRicevimentoStudenti(docente, getServletConfig().getServletContext().getRealPath(""));
            request.setAttribute("ricevimento_studenti", ricevimentoStudenti);
            request.setAttribute("docente", docente);
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/Jsp/JspDocenti/RicevimentoStudentiJsp.jsp");
            dispatcher.forward(request, response);
        } catch (DocenteInesistenteException ex) {
            response.sendError(404, this.getMessaggioDocenteNonTrovato(docente));
        } catch (RisorsaNonPresenteException ex) {
            request.setAttribute("ricevimento_studenti", null);
            request.setAttribute("docente", docente);
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/Jsp/JspDocenti/RicevimentoStudentiJsp.jsp");
            dispatcher.forward(request, response);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
