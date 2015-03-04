package it.unimol.profiles.servlet;

import it.unimol.profiles.SQLLayer.ConnectionPool;
import it.unimol.profiles.ManagerDocenti;
import it.unimol.profiles.beans.pagine.docente.InsegnamentiDocente;
import it.unimol.profiles.beans.utils.Docente;
import it.unimol.profiles.exceptions.ErroreConnessioneDocentiUnimol;
import it.unimol.profiles.exceptions.RisorsaNonPresenteException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author Stefano
 */
@WebServlet(name = "InsegnamentiDocente", urlPatterns = {"/InsegnamentiDocente"})
public class InsegnamentiDocenteServlet extends SezioneServlet {

    @Override
    protected void setCustomRequestAttributes(HttpServletRequest request, HttpServletResponse response, Docente docente) throws ServletException, IOException {
        try {
            InsegnamentiDocente insegnamentiDocente = this.getInsegnamentiDocente(docente);
            request.setAttribute("insegnamenti_docente", insegnamentiDocente);
        } catch (RisorsaNonPresenteException | ErroreConnessioneDocentiUnimol ex) {
            request.setAttribute("insegnamenti_docente", null);
        }
    }

    @Override
    protected String getJspToForward() {
        return "WEB-INF/Jsp/JspDocenti/InsegnamentiDocenteJsp.jsp";
    }

    public InsegnamentiDocente getInsegnamentiDocente(Docente docente) throws RisorsaNonPresenteException, ErroreConnessioneDocentiUnimol {

        InsegnamentiDocente insegnamentiDocente = null;
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(""
                    + "SELECT id_pagina_insegnamenti "
                    + "FROM docenti "
                    + "WHERE id = ?");
            preparedStatement.setString(1, docente.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            String idPaginaInsegnamenti = resultSet.getString("id_pagina_insegnamenti");

            if (idPaginaInsegnamenti != null) {
                insegnamentiDocente = new InsegnamentiDocente();
                try {
                    Document document = Jsoup.connect("http://docenti.unimol.it/index.php?u=" + idPaginaInsegnamenti + "&id=2").get();
                    insegnamentiDocente.setTestoFormattatoHtml(document.getElementsByClass("insidebox").html());
                } catch (UnknownHostException ex) {
                    throw new ErroreConnessioneDocentiUnimol();
                }
            } else {
                throw new RisorsaNonPresenteException();
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(ManagerDocenti.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(ManagerDocenti.class.getName()).log(Level.SEVERE, null, "ERRORE DEL DATABASE, CONTROLLARE CHE SIA ATTIVO IL SERVIZIO MYSQL E CHE I PARAMETRI DELLA CLASSE ParametriDatabase SIANO IMPOSTATI CORRETTAMENTE"); //cosa fare in caso di errore del database?
        } catch (IOException ex) {
            Logger.getLogger(ManagerDocenti.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(ManagerDocenti.class.getName()).log(Level.SEVERE, null, "SI E' VERIFICATO UN ERRORE CON LA LIBRERIA JSOUP");
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception ignore) {
                }
            }
        }
        return insegnamentiDocente;
    }

}
