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
    String bibTexLink = pubblicazioniDocente.getBibTexLink();
    Docente docente = (Docente) request.getAttribute("docente");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link type="text/css" rel="stylesheet" href="Css/stile.css" />
        <!--
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
        <script type="text/javascript" src="http://bibtex-js.googlecode.com/svn/trunk/src/bibtex_js.js"></script>
        -->
        <%
            if (pubblicazioniDocente != null) {
                out.write(""
                        + "<script type='text/javascript' src='Javascript/jquery.min.js'></script>"
                        + "<script type='text/javascript' src='Javascript/bibtex_js.js'></script>"
                );
            }
        %>

        <title>
            <%
                out.print("Prof. " + docente.getNome() + " " + docente.getCognome());
            %>
        </title>
    </head>
    <body>
        <%@include file="../../Html/Header.html" %>
        <div id="TITOLO_PAGINA">
            <%
                out.print("Prof. " + docente.getNome() + " "
                        + docente.getCognome());
            %>
        </div>
        <div id="CONTENUTO_PAGINA">
            <%@include file="FotoDocente.jsp" %>
            <%@include file="MenuDocente.jsp" %>
            <div id="CONTENUTO_SEZIONE_SELEZIONATA">
                <%  if (pubblicazioniDocente != null) {
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
                        out.print(""
                                + "<div id='MESSAGGIO_RISORSA_NON_PRESENTE'>"
                                + "Non sono presenti pubblicazioni per il Prof. " + docente.getNome() + " " + docente.getCognome()
                                + "</div>");
                    }
                %>

                <div id='PUBBLICAZIONI'>

                    <textarea id='bibtex_input' style='display:none;'>
                        <jsp:include page='<%=bibTexLink%>' flush='true'/>
                    </textarea>
                    <div id='bibtex_display'>

                    </div>

                </div>
            </div>

        </div>
        <%@include file="../../Html/Footer.html" %>
    </body>
</html>
