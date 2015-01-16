<%-- 
    Document   : InsegnamentiDocente
    Created on : 26-nov-2014, 12.23.01
    Author     : Stefano
--%>
<%@page import="it.unimol.profiles.beans.utils.Docente"%>
<%@page import="it.unimol.profiles.beans.pagine.docente.InsegnamentiDocente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    InsegnamentiDocente insegnamentiDocente = (InsegnamentiDocente) request.getAttribute("insegnamenti_docente");
    Docente docente = (Docente) request.getAttribute("docente");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link type="text/css" rel="stylesheet" href="Css/stile.css" />
        <title>
            <%@include file="JspCondivise/NomeDocente.jsp" %>
        </title>
    </head>
    <body>
        <%@include file="../../Html/Header.html" %>
        <div id="TITOLO_PAGINA">
            <%@include file="JspCondivise/NomeDocente.jsp" %>
        </div>
        <div id="CONTENUTO_PAGINA">
            <%@include file="JspCondivise/FotoDocente.jsp" %>
            <%@include file="JspCondivise/MenuDocente.jsp" %>
            <div id="CONTENUTO_SEZIONE_SELEZIONATA">
                <%  if (insegnamentiDocente != null) {
                        out.print("<div id='INSEGNAMENTI'>" + insegnamentiDocente.getTestoFormattatoHtml() + "</div>");
                    } else {
                        out.print("<div id='MESSAGGIO_RISORSA_NON_PRESENTE'>");
                        if (docente.getSesso().equalsIgnoreCase("F")) {
                            out.print("Non è attualmente disponibile la pagina degli insegnamenti per la Prof.ssa " + docente.getNome() + " " + docente.getCognome());
                        } else {
                            out.print("Non è attualmente disponibile la pagina degli insegnamenti per il Prof. " + docente.getNome() + " " + docente.getCognome());
                        }
                        out.print("</div>");
                    }
                %>
            </div>
        </div>
        <%@include file="../../Html/Footer.html" %>
    </body>
</html>
