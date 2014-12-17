<%-- 
    Document   : PaginaPersonalizzata
    Created on : 26-nov-2014, 12.38.02
    Author     : Stefano
--%>

<%@page import="it.unimol.profiles.beans.pagine.docente.sezioniPersonalizzate.ContenutoFoto"%>
<%@page import="it.unimol.profiles.beans.pagine.docente.sezioniPersonalizzate.ContenutoFile"%>
<%@page import="it.unimol.profiles.beans.pagine.docente.sezioniPersonalizzate.ContenutoTesto"%>
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
                <%  if (sezionePersonalizzata != null) {
                        for (int i = 0; i < sezionePersonalizzata.size(); i++) {
                            if (sezionePersonalizzata.get(i) instanceof ContenutoTesto) {
                                String linkTesto = "../../../" + ((ContenutoTesto) sezionePersonalizzata.get(i)).getLinkHtml();
                                out.print("<div class='contenuto_testo'>");
                                %><jsp:include page='<%=linkTesto%>' flush='true'/><%
                                out.print("</div>");
                            } else if (sezionePersonalizzata.get(i) instanceof ContenutoFile) {
                                String linkFile = ((ContenutoFile) sezionePersonalizzata.get(i)).getFileLink();
                                out.print("<div class='contenuto_file'>"
                                        + "<a href='" + linkFile + "'>"+((ContenutoFile) sezionePersonalizzata.get(i)).getDescrizioneFile()+"</a>"
                                        + "</div>");
                            } else if (sezionePersonalizzata.get(i) instanceof ContenutoFoto) {
                                String linkFoto = ((ContenutoFoto) sezionePersonalizzata.get(i)).getLinkFoto();
                                out.print("<div class='contenuto_foto'>"
                                        + "<img src='" + linkFoto + "' alt='"+((ContenutoFoto) sezionePersonalizzata.get(i)).getDescrizioneFoto() +"'></a>"
                                        + "</div>");
                            }
                        }
                    } else {

                    }
                %>
            </div>
        </div>
        <%@include file="../../Html/Footer.html" %>
    </body>
</html>
