<%-- 
    Document   : PaginaPersonalizzata
    Created on : 26-nov-2014, 12.38.02
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
            Area Riservata Amministratore
        </title>
    </head>
    <body>
        <%@include file="../../Html/Header.html" %>
        <div id="TITOLO_PAGINA">
            Area Riservata Amministratore
        </div>
        <div id="CONTENUTO_PAGINA">
            <%@include file="MenuAmministratore.jsp" %>
            <div id="CONTENUTO_SEZIONE_SELEZIONATA">
                <div id="SCUDO_UNIMOL">
                    <img src="Images/Unimol-eagle-shield.jpg" alt="We are watching you"/>
                </div>
            </div>
        </div>
        <%@include file="../../Html/Footer.html" %>
    </body>
</html>
