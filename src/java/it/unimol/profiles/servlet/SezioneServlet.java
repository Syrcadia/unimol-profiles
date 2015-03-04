package it.unimol.profiles.servlet;

import it.unimol.profiles.beans.utils.Docente;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Stefano
 */
public abstract class SezioneServlet extends HandlerDocenteServlet {

    public static final String PERCORSO_FOTO_PROFILO_DEFAULT = "Images/profilo-default.png";

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Docente docente = this.getDocenteDallaUrl(request);
        if (!esisteDocente(docente)) {
            response.sendError(404, this.getMessaggioDocenteNonTrovato(docente));
        } else {
            request.setAttribute("percorso_foto_profilo", this.getPercorsoFotoProfilo(docente));
            request.setAttribute("elenco_sezioni_personalizzate", this.getElencoSezioniPersonalizzate(docente));
            request.setAttribute("docente", docente);
            this.setCustomRequestAttributes(request, response, docente);
            RequestDispatcher dispatcher = request.getRequestDispatcher(this.getJspToForward());
            dispatcher.forward(request, response);
        }
    }
    
    protected abstract String getJspToForward();
    
    protected abstract void setCustomRequestAttributes(HttpServletRequest request, HttpServletResponse response, Docente docente) throws ServletException, IOException;
    
   

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
