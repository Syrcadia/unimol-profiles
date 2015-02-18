<%-- 
    Document   : TestInserimentoFileJsp
    Created on : 5-feb-2015, 19.01.04
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
            Test inserimento file
        </title>
    </head>
    <body>
        <%@include file="../../Html/Header.html" %>
        <div id="TITOLO_PAGINA">
            Test inserimento file
        </div>
        <div id="CONTENUTO_PAGINA">
            <form method="post" action="TestInserimentoFile" enctype="multipart/form-data">
                <input type="hidden" name="action" value="upload" />
                <label>File:</label>
                <input type="file" name="file" />
                <br/>
                <input type="submit" value="Carica online" />
            </form>
        </div>
        <%@include file="../../Html/Footer.html" %>
    </body>
</html>
