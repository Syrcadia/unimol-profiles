<%-- 
    Document   : FotoDocente
    Created on : 2-dic-2014, 21.24.03
    Author     : Stefano
--%>

<%@page import="it.unimol.profiles.beans.utils.Docente"%>
<%@page import="it.unimol.profiles.ManagerDocenti"%>
<div id="FOTO_DOCENTE">
    <img src="<%
        out.print(ManagerDocenti.getInstance().getPercorsoFotoDocente((Docente) request.getAttribute("docente"))); %>" alt="Foto di <% out.print(((Docente) request.getAttribute("docente")).getNome() + " " + ((Docente) request.getAttribute("docente")).getCognome());
         %>"/>
</div>