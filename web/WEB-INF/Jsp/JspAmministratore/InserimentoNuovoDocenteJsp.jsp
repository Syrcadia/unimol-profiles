<%--     Document   : InserimentoNuovoDocenteJsp
    Created on : 16-gen-2015, 16.00.04
    Author     : Stefano
--%>

<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    ArrayList<String> ruoli = (ArrayList<String>) request.getAttribute("ruoli");
    ArrayList<String> dipartimenti = (ArrayList<String>) request.getAttribute("dipartimenti");
%>
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

                <%                    if ((String) request.getAttribute("messaggio") != null) {
                        out.println("<div id='MESSAGGIO'> " + (String) request.getAttribute("messaggio") + "</div>");
                    }
                %>
                <form name="NuovoDocente" action="InserimentoNuovoDocente" method="POST">

                    <input type="hidden" name="action" value="inserisci">
                    <fieldset>
                        <div id="NOME_INSERIMENTO_DOCENTE">
                            <input type="text" name="nome" placeholder="Nome" required>
                        </div>
                        <div id="COGNOME_INSERIMENTO_DOCENTE">
                            <input type="text" name="cognome" placeholder="Cognome" required>
                        </div>
                        <div id="RUOLO_INSERIMENTO_DOCENTE">
                            <select name="ruolo" required>
                                <option value="">Ruolo Docente</option>
                                <%                                    for (int i = 0; i < ruoli.size(); i++) {
                                        out.println("<option value='" + i + "'>" + ruoli.get(i) + "</option>");
                                    }
                                %>
                            </select> <a href="" title="Puoi inserire nuovi ruoli attraverso l'apposita funzionalità">?</a>
                        </div>
                        <div id="DIPARTIMENTO_INSERIMENTO_DOCENTE">
                            <select name="dipartimento" required>
                                <option value="">Dipartimento</option>
                                <%
                                    for (int i = 0; i < dipartimenti.size(); i++) {
                                        out.println("<option value='" + i + "'>" + dipartimenti.get(i) + "</option>");
                                    }
                                %>
                            </select> <a href="" title="Puoi inserire nuovi dipartimenti attraverso l'apposita funzionalità">?</a>
                        </div>
                        <div id="SESSO_INSERIMENTO_DOCENTE">
                            <select name="sesso" required>
                                <option value="">Sesso</option>
                                <option value='M'>Maschio</option>
                                <option value='F'>Femmina</option>
                            </select>
                        </div>
                        <div id="PASSWORD_INSERIMENTO_DOCENTE">
                            <input name="password" pattern=".{8,}" placeholder="Password Utente" required> <a href="" title="La password deve contenere almeno 8 caratteri">?</a>
                        </div>
                        <div id="INSERISCI">
                            <input type="submit" value="Inserisci">
                        </div>

                    </fieldset>

                </form>
            </div>
        </div>
        <%@include file="../../Html/Footer.html" %>
    </body>
</html>
