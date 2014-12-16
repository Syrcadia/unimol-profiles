<%-- 
    Document   : PubblicazioniDocente
    Created on : 26-nov-2014, 12.25.56
    Author     : Stefano
--%>

<%@page import="it.unimol.profiles.beans.utils.Docente"%>
<%@page import="it.unimol.profiles.beans.pagine.docente.PubblicazioniDocente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    PubblicazioniDocente pubblicazioniDocente = (PubblicazioniDocente) request.getAttribute("pubblicazioni_docente");
    Docente docente = (Docente) request.getAttribute("docente");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link type="text/css" rel="stylesheet" href="Css/stile.css" />
        <%
            if (pubblicazioniDocente != null) {
                out.write(""
                        + "<script type='text/javascript' src='Javascript/jquery.min.js'></script>"
                        + "<script type='text/javascript' src='Javascript/bibtex_js.js'></script>"
                );
            }
        %>

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
                <%  if (pubblicazioniDocente != null) {
                        String bibTexLink = "../../../" + pubblicazioniDocente.getBibTexLink();
                        out.print(""
                                + "<div id='PUBBLICAZIONI'>"
                                + "<textarea id='bibtex_input' style='display:none;'>");
                %>
                <jsp:include page='<%=bibTexLink%>' flush='true'/>
                <% out.print(""
                                + "</textarea>"
                                + "<div id='bibtex_display'>"
                                + "</div>"
                                + "</div>"
                        );
                    } else {
                        out.print("<div id='MESSAGGIO_RISORSA_NON_PRESENTE'>");
                        if (docente.getSesso().equalsIgnoreCase("F")) {
                            out.print("Non sono ancora state inserite le pubblicazioni per la Prof.ssa " + docente.getNome() + " " + docente.getCognome());
                        } else {
                            out.print("Non sono ancora state inserite le pubblicazioni per il Prof. " + docente.getNome() + " " + docente.getCognome());
                        }
                        out.print("</div>");
                    }
                %>
            </div>

        </div>
        <%@include file="../../Html/Footer.html" %>
    </body>
</html>
