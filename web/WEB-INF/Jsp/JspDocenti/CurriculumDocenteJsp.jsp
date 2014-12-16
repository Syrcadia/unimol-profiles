<%-- 
    Document   : CurriculumDocenteJsp
    Created on : 26-nov-2014, 12.24.31
    Author     : Stefano
--%>

<%@page import="it.unimol.profiles.beans.pagine.docente.CurriculumDocente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    CurriculumDocente curriculumDocente = (CurriculumDocente) request.getAttribute("curriculum_docente");
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
                <%  if (curriculumDocente != null) {
                        out.print(""
                                + "<div id='CURRICULUM_DOCENTE'>");
                        if (curriculumDocente.getPdfLink() != null) {
                            out.print(""
                                    + "<div id='LINK_FILE'>"
                                    + "<a href='" + curriculumDocente.getPdfLink() + "'>Scarica PDF</a>"
                                    + "</div>");
                        }
                        if (curriculumDocente.getHtmlLink() != null) {
                            out.print(""
                                    + "<div id='TESTO_CURRICULUM'>");
                            String curriculumHtmlLink = curriculumHtmlLink = "../../../" + curriculumDocente.getHtmlLink();
                %>
                <jsp:include page='<%=curriculumHtmlLink%>' flush='true'/>
                <%
                            out.print(""
                                    + "</div>");
                        }
                        out.print("</div>");
                    } else {
                        out.print(""
                                + "<div id='MESSAGGIO_RISORSA_NON_PRESENTE'>"
                                + "Non è presente alcun curriculum per il Prof. " + docente.getNome() + " " + docente.getCognome()
                                + "</div>");
                    }
                %>
            </div>

        </div>
        <%@include file="../../Html/Footer.html" %>
    </body>
</html>