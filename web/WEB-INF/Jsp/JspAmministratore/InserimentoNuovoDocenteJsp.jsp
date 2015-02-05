<%-- 
    Document   : InserimentoNuovoDocenteJsp
    Created on : 16-gen-2015, 16.00.04
    Author     : Stefano
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link type="text/css" rel="stylesheet" href="Css/stile.css" />
        <title>
            Inserimento Nuovo Docente
        </title>
    </head>
    <body>
        <%@include file="../../Html/Header.html" %>
        <div id="TITOLO_PAGINA">
            Inserimento Nuovo Docente
        </div>
        <div id="CONTENUTO_PAGINA">
            <%@include file="MenuAmministratore.jsp" %>
            <div id="CONTENUTO_SEZIONE_SELEZIONATA">
                <form name="NuovoDocente" action="LoginAmministratore" method="POST">
                    <div>Nome: <input type="text" name="nome"></div>
                    <div>Cognome: <input type="text" name="cognome"></div>
                    <div><input type="submit" value="Inserisci"></div>
                </form>
            </div>
        </div>
        <%@include file="../../Html/Footer.html" %>
    </body>
</html>
