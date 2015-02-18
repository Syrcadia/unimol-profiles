<%-- 
    Document   : InfoGeneraliDocente
    Created on : 26-nov-2014, 12.21.54
    Author     : Stefano
--%>
<%@page import="it.unimol.profiles.beans.utils.Docente"%>
<%@page import="it.unimol.profiles.beans.pagine.docente.InformazioniGeneraliDocente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    InformazioniGeneraliDocente informazioniGeneraliDocente = (InformazioniGeneraliDocente) request.getAttribute("informazioni_generali_docente");
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
                <div id="INFORMAZIONI_GENERALI">
                    <ul>
                        <%  out.println("<li><div class='TITOLO_INFORMAZIONE'>Nome:</div>"
                                + " " + informazioniGeneraliDocente.getNome() + "</li>");
                            out.println("<li><div class='TITOLO_INFORMAZIONE'>Cognome:</div>"
                                    + " " + informazioniGeneraliDocente.getCognome() + "</li>");
                            out.println("<li><div class='TITOLO_INFORMAZIONE'>Dipartimento:</div>"
                                    + " " + informazioniGeneraliDocente.getDipartimento() + "</li>");
                            out.println("<li><div class='TITOLO_INFORMAZIONE'>Ruolo:</div>"
                                    + " " + informazioniGeneraliDocente.getRuolo() + "</li>");

                            if (informazioniGeneraliDocente.getEmail() != null && informazioniGeneraliDocente.getEmail().size() != 0) {
                                out.println("<li><div class='TITOLO_INFORMAZIONE'>Email:</div>");
                                for (int i = 0; i < informazioniGeneraliDocente.getEmail().size(); i++) {
                                    out.println("<div class='email'>" + informazioniGeneraliDocente.getEmail().get(i) + "</div>");
                                }
                                out.println("</li>");
                            }

                            if (informazioniGeneraliDocente.getTelefono() != null && informazioniGeneraliDocente.getTelefono().size() != 0) {
                                out.println("<li><div class='TITOLO_INFORMAZIONE'>Contatti telefonici:</div>");
                                for (int i = 0; i < informazioniGeneraliDocente.getTelefono().size(); i++) {
                                    out.println("<div class='telefono'>" + informazioniGeneraliDocente.getTelefono().get(i) + "</div>");
                                }
                                out.println("</li>");
                            }
                        %>
                    </ul>
                </div>
            </div>

        </div>
        <%@include file="../../Html/Footer.html" %>
    </body>
</html>
