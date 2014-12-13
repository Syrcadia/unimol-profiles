<%-- 
    Document   : CurriculumDocenteJsp
    Created on : 26-nov-2014, 12.24.31
    Author     : Stefano
--%>

<%@page import="it.unimol.profiles.beans.pagine.docente.CurriculumDocente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    CurriculumDocente curriculumDocente = (CurriculumDocente) request.getAttribute("curriculum_docente");
    Docente docente = (Docente)request.getAttribute("docente");
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
                out.print("Prof. " + docente.getNome() + " "
                        + docente.getCognome());
            %>
        </div>
        <div id="CONTENUTO_PAGINA">
            <%@include file="FotoDocente.jsp" %>
            <%@include file="MenuDocente.jsp" %>
            <div id="CONTENUTO_SEZIONE_SELEZIONATA">
                <div id="CURRICULUM_DOCENTE">
                    <div id="LINK_FILE">
                        <% 
                            out.write("<a href='"+ curriculumDocente.getFileLink() +"'>Scarica PDF</a>");
                        %>
                    </div>
                    <div id="TESTO_CURRICULUM">
                        <% 
                            out.write(curriculumDocente.getCurriculumHtml());
                        %>
                    </div>
                </div>
            </div>

        </div>
        <%@include file="../../Html/Footer.html" %>
    </body>
</html>