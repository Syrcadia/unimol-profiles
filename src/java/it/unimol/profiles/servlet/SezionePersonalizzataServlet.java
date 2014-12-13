package it.unimol.profiles.servlet;

import it.unimol.profiles.ManagerDocenti;
import it.unimol.profiles.beans.pagine.docente.SezionePersonalizzata;
import it.unimol.profiles.beans.utils.Docente;
import java.io.IOException;
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
@WebServlet(name = "SezionePersonalizzata", urlPatterns = {"/SezionePersonalizzata"})
public class SezionePersonalizzataServlet extends HttpServlet {

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

        Docente docente = new Docente();
        docente.setId(((String) request.getParameter("id")));
        docente.setNome(((String) request.getParameter("nome")));
        docente.setCognome(((String) request.getParameter("cognome")));
        try {
            SezionePersonalizzata sezionePersonalizzata;
            sezionePersonalizzata = ManagerDocenti.getInstance().getSezionePersonalizzata(docente, (int) Integer.parseInt(request.getParameter("id_sezione")));
            request.setAttribute("sezione_personalizzata_docente", sezionePersonalizzata);
            request.setAttribute("docente", docente);

            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/Jsp/JspDocenti/SezionePersonalizzataJsp.jsp");
            dispatcher.forward(request, response);
        } catch (Exception ex) {
            response.sendError(404,"Mi dispiace,\nNessun docente corrisponde ai parametri specificati");
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
