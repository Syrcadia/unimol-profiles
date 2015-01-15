<%-- 
    Document   : ElencoDocenti
    Created on : 26-nov-2014, 10.52.33
    Author     : Stefano
--%>

<%@page import="it.unimol.profiles.beans.utils.ElencoDocenti"%>
<%@page import="it.unimol.profiles.beans.utils.Docente"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    ElencoDocenti elencoDocenti = (ElencoDocenti) request.getAttribute("elenco_docenti");
    String href;
    String nomeDocente;
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link type="text/css" rel="stylesheet" href="Css/stile.css" />
        <title>Elenco Docenti</title>

    </head>
    <body>
        <%@include file="../Html/Header.html" %>

        <div id="TITOLO_PAGINA">
            Elenco Docenti
        </div>
        <div id="CONTENUTO_PAGINA">
            <div id="ELENCO_DOCENTI">

                <%
                    for (int i = 0; i < elencoDocenti.getRuoli().size(); i++) {
                        out.print("<div class=''ELENCO_CATEGORIA''>");
                        out.print("<div class=''TITOLO_CATEGORIA''>" + elencoDocenti.getRuoli().get(i) + "</div>");
                        out.print("<ul>");
                        for (int j = 0; j < elencoDocenti.getElencoDocenti().get(i).size(); j++) {
                            nomeDocente = elencoDocenti.getElencoDocenti().get(i).get(j).getCognome() + " " + elencoDocenti.getElencoDocenti().get(i).get(j).getNome();
                            href = "InformazioniGeneraliDocente?id=" + elencoDocenti.getElencoDocenti().get(i).get(j).getId() + "&s=" + elencoDocenti.getElencoDocenti().get(i).get(j).getSesso() + "&nome=" + elencoDocenti.getElencoDocenti().get(i).get(j).getNome() + "&cognome=" + elencoDocenti.getElencoDocenti().get(i).get(j).getCognome();
                            out.print("<li><a href='" + href + "'>" + nomeDocente + "</a></li>");
                        }
                        out.print("</ul>");
                        out.print("</div>");
                    }
                %>


            </div>
        </div>

        <%@include file="../Html/Footer.html" %>
    </body>
</html>
