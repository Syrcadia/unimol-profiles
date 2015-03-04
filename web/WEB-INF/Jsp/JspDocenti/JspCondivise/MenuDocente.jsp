<%@page import="java.net.URLEncoder"%>
<%@page import="it.unimol.profiles.beans.utils.ElencoSezioniPersonalizzate"%>
<%
    ElencoSezioniPersonalizzate elencoSezioniPersonalizzate = (ElencoSezioniPersonalizzate) request.getAttribute("elenco_sezioni_personalizzate");
    String stringaAttributiUrl = ""
            + "id=" + URLEncoder.encode(docente.getId(), "UTF-8")
            + "&s=" + URLEncoder.encode(docente.getSesso(), "UTF-8")
            + "&nome=" + URLEncoder.encode(docente.getNome(), "UTF-8")
            + "&cognome=" + URLEncoder.encode(docente.getCognome(), "UTF-8");


%>
<div id="MENU_SEZIONI">
    <ul>
        <li><a href="InformazioniGeneraliDocente?<%=stringaAttributiUrl%>">Informazioni Generali</a></li>
        <li><a href="InsegnamentiDocente?<%=stringaAttributiUrl%>">Insegnamenti</a></li>
        <li><a href="CurriculumDocente?<%=stringaAttributiUrl%>">Curriculum</a></li>
        <li><a href="PubblicazioniDocente?<%=stringaAttributiUrl%>">Pubblicazioni</a></li>
        <li><a href="RicevimentoStudenti?<%=stringaAttributiUrl%>">Ricevimento Studenti</a></li>
            <%
                for (int i = 0; i < elencoSezioniPersonalizzate.size(); i++) {
                    out.println("<li><a href='SezionePersonalizzata?" + stringaAttributiUrl + "&id_sezione=" + elencoSezioniPersonalizzate.getIdSezione(i) + "'>" + elencoSezioniPersonalizzate.getNomeSezione(i) + "</a></li>");
                }
            %>
    </ul>
</div>
