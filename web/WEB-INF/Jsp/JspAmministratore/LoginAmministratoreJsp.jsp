<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link type="text/css" rel="stylesheet" href="Css/stile.css" />
        <title>
            Login Amministratore
        </title>
    </head>
    <body>
        <%@include file="../../Html/Header.html" %>
        <div id="TITOLO_PAGINA">
            Login Amministratore
        </div>
        <div id="CONTENUTO_PAGINA">
            <div id="FORM_LOGIN_AMMINISTRATORE">
                <form name="Login" action="LoginAmministratoreServlet" method="POST">
                    <div id="CAMPO_NOME_AMMINISTRATORE">Nome Amministratore: <input type="text" name="nome_amministratore"></div>
                    <div id="CAMPO_PASSWORD">Password: <input type="password" name="password"></div>
                    <div id="BOTTONE_SUBMIT"><input type="submit" value="Login"></div>
                </form>
            </div>
        </div>
        <%@include file="../../Html/Footer.html" %>
    </body>
</html>
