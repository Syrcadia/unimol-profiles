package it.unimol.profiles.servlet;

import it.unimol.profiles.SQLLayer.ConnectionPool;
import it.unimol.profiles.ManagerDocenti;
import it.unimol.profiles.beans.pagine.docente.sezioniPersonalizzate.ContenutoFile;
import it.unimol.profiles.beans.pagine.docente.sezioniPersonalizzate.ContenutoFoto;
import it.unimol.profiles.beans.pagine.docente.sezioniPersonalizzate.ContenutoTesto;
import it.unimol.profiles.beans.pagine.docente.sezioniPersonalizzate.SezionePersonalizzata;
import it.unimol.profiles.beans.utils.Docente;
import it.unimol.profiles.exceptions.RisorsaNonPresenteException;
import java.io.IOException;
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

/**
 *
 * @author Stefano
 */
@WebServlet(name = "SezionePersonalizzata", urlPatterns = {"/SezionePersonalizzata"})
public class SezionePersonalizzataServlet extends SezioneServlet {

    @Override
    protected void setCustomRequestAttributes(HttpServletRequest request, HttpServletResponse response, Docente docente) throws ServletException, IOException {
        try {
            SezionePersonalizzata sezionePersonalizzata = this.getSezionePersonalizzata(docente, (int) Integer.parseInt(request.getParameter("id_sezione")));
            request.setAttribute("sezione_personalizzata_docente", sezionePersonalizzata);

        } catch (RisorsaNonPresenteException ex) {
            request.setAttribute("sezione_personalizzata_docente", null);
        }
    }

    @Override
    protected String getJspToForward() {
        return "WEB-INF/Jsp/JspDocenti/SezionePersonalizzataJsp.jsp";
    }

    public SezionePersonalizzata getSezionePersonalizzata(Docente docente, int idSezione) throws RisorsaNonPresenteException {

        Connection connection = null;
        SezionePersonalizzata sezionePersonalizzata = new SezionePersonalizzata();

        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(""
                    + "SELECT * "
                    + "FROM sezioni_docenti "
                    + "WHERE id_sezione = ? "
                    + "AND id_docente = ?");
            preparedStatement.setString(1, Integer.toString(idSezione));
            preparedStatement.setString(2, docente.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new RisorsaNonPresenteException();
            }
            sezionePersonalizzata.setNomeSezione(resultSet.getString("nome_sezione"));
            sezionePersonalizzata.setIdSezione(resultSet.getString("id_sezione"));
            String percorsoCartellaSezione = "Risorse/" + docente.getId() + "/sezione_" + sezionePersonalizzata.getIdSezione();

            preparedStatement = connection.prepareStatement(""
                    + "SELECT id_risorsa, tipo_risorsa, ordine "
                    + "FROM contenuti "
                    + "WHERE id_sezione = ? "
                    + "ORDER BY ordine");
            preparedStatement.setString(1, Integer.toString(idSezione));

            resultSet = preparedStatement.executeQuery();

            ResultSet auxResultSet = null;
            PreparedStatement auxPreparedStatement;

            while (resultSet.next()) {
                switch (resultSet.getString("tipo_risorsa")) {
                    case "testo":
                        auxPreparedStatement = connection.prepareStatement(""
                                + "SELECT nome_testo "
                                + "FROM testi "
                                + "WHERE id = ?");
                        auxPreparedStatement.setString(1, resultSet.getString("id_risorsa"));
                        auxResultSet = auxPreparedStatement.executeQuery();
                        auxResultSet.next();
                        ContenutoTesto contenutoTesto = new ContenutoTesto();
                        contenutoTesto.setLinkHtml(percorsoCartellaSezione + "/" + auxResultSet.getString("nome_testo"));
                        contenutoTesto.setOrdine(Integer.parseInt(resultSet.getString("ordine")));
                        sezionePersonalizzata.add(contenutoTesto);
                        break;
                    case "file":
                        auxPreparedStatement = connection.prepareStatement(""
                                + "SELECT nome_file, descrizione, estensione "
                                + "FROM files "
                                + "WHERE id = ?");
                        auxPreparedStatement.setString(1, resultSet.getString("id_risorsa"));
                        auxResultSet = auxPreparedStatement.executeQuery();
                        auxResultSet.next();
                        ContenutoFile contenutoFile = new ContenutoFile();
                        contenutoFile.setFileLink(percorsoCartellaSezione + "/" + auxResultSet.getString("nome_file"));
                        contenutoFile.setDescrizioneFile(auxResultSet.getString("descrizione"));
                        contenutoFile.setOrdine(Integer.parseInt(resultSet.getString("ordine")));
                        sezionePersonalizzata.add(contenutoFile);
                        break;
                    case "foto":
                        auxPreparedStatement = connection.prepareStatement(""
                                + "SELECT nome_foto, descrizione "
                                + "FROM foto "
                                + "WHERE id= ?");
                        auxPreparedStatement.setString(1, resultSet.getString("id_risorsa"));
                        auxResultSet = auxPreparedStatement.executeQuery();
                        auxResultSet.next();
                        ContenutoFoto contenutoFoto = new ContenutoFoto();
                        contenutoFoto.setLinkFoto(percorsoCartellaSezione + "/" + auxResultSet.getString("nome_foto"));
                        contenutoFoto.setDescrizioneFoto(auxResultSet.getString("descrizione"));
                        contenutoFoto.setOrdine(Integer.parseInt(resultSet.getString("ordine")));
                        sezionePersonalizzata.add(contenutoFoto);
                        break;
                }
            }
            if (auxResultSet != null) {
                auxResultSet.close();
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(ManagerDocenti.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(ManagerDocenti.class.getName()).log(Level.SEVERE, null, "ERRORE DEL DATABASE, CONTROLLARE CHE SIA ATTIVO IL SERVIZIO MYSQL E CHE I PARAMETRI DELLA CLASSE ParametriDatabase SIANO IMPOSTATI CORRETTAMENTE"); //cosa fare in caso di errore del database?
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception ignore) {
                }
            }
        }
        return sezionePersonalizzata;
    }

}
