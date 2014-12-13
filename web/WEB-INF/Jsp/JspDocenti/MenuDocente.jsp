<%-- 
    Document   : MenuDocente
    Created on : 2-dic-2014, 12.51.02
    Author     : Stefano
--%>
<%@page import="it.unimol.profiles.beans.utils.Docente"%>
<%@page import="it.unimol.profiles.beans.utils.ElencoSezioniPersonalizzate"%>
<%@page import="it.unimol.profiles.ManagerDocenti"%>
<%@page import="it.unimol.profiles.beans.pagine.docente.SezionePersonalizzata"%>
<%
    ElencoSezioniPersonalizzate elencoSezioniPersonalizzate = ManagerDocenti.getInstance().getElencoSezioniPersonalizzate((Docente) request.getAttribute("docente"));
%>
<div id="MENU_SEZIONI">
    <ul>
        <li><a href="InformazioniGeneraliDocente?id=<%out.print(((Docente) request.getAttribute("docente")).getId());%>&nome=<%out.print(((Docente) request.getAttribute("docente")).getNome());%>&cognome=<%out.print(((Docente) request.getAttribute("docente")).getCognome());%>">Informazioni Generali</a></li>
        <li><a href="InsegnamentiDocente?id=<%out.print(((Docente) request.getAttribute("docente")).getId());%>&nome=<%out.print(((Docente) request.getAttribute("docente")).getNome());%>&cognome=<%out.print(((Docente) request.getAttribute("docente")).getCognome());%>">Insegnamenti</a></li>
        <li><a href="CurriculumDocente?id=<%out.print(((Docente) request.getAttribute("docente")).getId());%>&nome=<%out.print(((Docente) request.getAttribute("docente")).getNome());%>&cognome=<%out.print(((Docente) request.getAttribute("docente")).getCognome());%>">Curriculum</a></li>
        <li><a href="PubblicazioniDocente?id=<%out.print(((Docente) request.getAttribute("docente")).getId());%>&nome=<%out.print(((Docente) request.getAttribute("docente")).getNome());%>&cognome=<%out.print(((Docente) request.getAttribute("docente")).getCognome());%>">Pubblicazioni</a></li>
        <li><a href="RicevimentoStudenti?id=<%out.print(((Docente) request.getAttribute("docente")).getId());%>&nome=<%out.print(((Docente) request.getAttribute("docente")).getNome());%>&cognome=<%out.print(((Docente) request.getAttribute("docente")).getCognome());%>">Ricevimento Studenti</a></li>
            <%
                for (int i = 0; i < elencoSezioniPersonalizzate.size(); i++) {
                    out.println("<li><a href='SezionePersonalizzata?id=" + ((Docente) request.getAttribute("docente")).getId() + "&nome=" + ((Docente) request.getAttribute("docente")).getNome() + "&cognome=" + ((Docente) request.getAttribute("docente")).getCognome() +"&id_sezione="+ i + "'>" + elencoSezioniPersonalizzate.get(i) + "</a></li>");
                }
            %>
    </ul>
</div>
