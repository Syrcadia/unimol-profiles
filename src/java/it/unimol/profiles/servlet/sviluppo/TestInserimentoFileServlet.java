package it.unimol.profiles.servlet.sviluppo;

import it.unimol.profiles.ManagerFileSystem;
import it.unimol.profiles.exceptions.UploadNonValidoException;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Stefano
 */
@WebServlet(name = "TestInserimentoFile", urlPatterns = {"/TestInserimentoFile"})
@MultipartConfig
public class TestInserimentoFileServlet extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            ManagerFileSystem.inserisciFile(request,null);
            RequestDispatcher dispatcher = request.getRequestDispatcher("ElencoDocenti");
            dispatcher.forward(request, response);
        } catch (UploadNonValidoException ex) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/Jsp/JspSviluppo/TestInserimentoFileJsp.jsp");
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/Jsp/JspSviluppo/TestInserimentoFileJsp.jsp");
        dispatcher.forward(request, response);
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
