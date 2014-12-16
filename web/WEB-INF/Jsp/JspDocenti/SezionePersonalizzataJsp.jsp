<%-- 
    Document   : PaginaPersonalizzata
    Created on : 26-nov-2014, 12.38.02
    Author     : Stefano
--%>

<%@page import="it.unimol.profiles.beans.utils.Docente"%>
<%@page import="it.unimol.profiles.beans.pagine.docente.sezioniPersonalizzate.SezionePersonalizzata"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    SezionePersonalizzata sezionePersonalizzata = (SezionePersonalizzata) request.getAttribute("sezione_personalizzata_docente");
    Docente docente = (Docente) request.getAttribute("docente");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link type="text/css" rel="stylesheet" href="Css/stile.css" />
        <title>
            <%@include file="NomeDocente.jsp" %>
        </title>
    </head>
    <body>
        <%@include file="../../Html/Header.html" %>
        <div id="TITOLO_PAGINA">
            <%@include file="NomeDocente.jsp" %>
        </div>
        <div id="CONTENUTO_PAGINA">
            <%@include file="FotoDocente.jsp" %>
            <%@include file="MenuDocente.jsp" %>
            <div id="CONTENUTO_SEZIONE_SELEZIONATA">

            </div>

        </div>
        <%@include file="../../Html/Footer.html" %>
    </body>
</html>
