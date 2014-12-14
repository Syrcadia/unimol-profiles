<%-- 
    Document   : RicevimentoStudentiJsp
    Created on : 26-nov-2014, 12.26.33
    Author     : Stefano
--%>

<%@page import="it.unimol.profiles.beans.utils.Docente"%>
<%@page import="it.unimol.profiles.beans.pagine.docente.RicevimentoStudenti"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    RicevimentoStudenti ricevimentoStudenti = (RicevimentoStudenti) request.getAttribute("ricevimento_studenti");
    Docente docente = (Docente) request.getAttribute("docente");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link type="text/css" rel="stylesheet" href="Css/stile.css" />
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
                out.print("Prof. " + docente.getNome() + " " + docente.getCognome());
            %>
        </div>
        <div id="CONTENUTO_PAGINA">
            <%@include file="FotoDocente.jsp" %>
            <%@include file="MenuDocente.jsp" %>
            <div id="CONTENUTO_SEZIONE_SELEZIONATA">
                <%  if (ricevimentoStudenti != null) {
                        out.print(""
                                + "<div id='RICEVIMENTO_STUDENTI'>"
                                + ricevimentoStudenti
                                + "</div>");
                    } else {
                        out.print(""
                                + "<div id='MESSAGGIO_RISORSA_NON_PRESENTE'>"
                                + "Non è ancora presente l'orario di ricevimento del Prof. "+docente.getNome()+" "+docente.getCognome()
                                + "</div>");
                    }
                %>
            </div>
        </div>
        <%@include file="../../Html/Footer.html" %>
    </body>
</html>