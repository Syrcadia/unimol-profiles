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
                <div id="ELENCO_PROFESSORI_ORDINARI">
                    <div id="TITOLO_ELENCO_PROFESSORI_ORDINARI">Professori Ordinari</div>
                    <ul>
                        <%
                            for (int i = 0; i < elencoDocenti.getListaProfessoriOrdinari().size(); i++) {
                                nomeDocente = elencoDocenti.getListaProfessoriOrdinari().get(i).getCognome() + " " + elencoDocenti.getListaProfessoriOrdinari().get(i).getNome();
                                href = "InformazioniGeneraliDocente?id=" + elencoDocenti.getListaProfessoriOrdinari().get(i).getId() + "&s=" + elencoDocenti.getListaProfessoriOrdinari().get(i).getSesso() + "&nome=" + elencoDocenti.getListaProfessoriOrdinari().get(i).getNome() + "&cognome=" + elencoDocenti.getListaProfessoriOrdinari().get(i).getCognome();
                                out.print("<li><a href='" + href + "'>" + nomeDocente + "</a></li>");
                            }
                        %>
                    </ul>
                </div>
                <div id="ELENCO_PROFESSORI_ASSOCIATI">
                    <div id="TITOLO_ELENCO_PROFESSORI_ASSOCIATI">Professori Associati</div>
                    <ul>
                        <%
                            for (int i = 0; i < elencoDocenti.getListaProfessoriAssociati().size(); i++) {
                                nomeDocente = elencoDocenti.getListaProfessoriAssociati().get(i).getCognome() + " " + elencoDocenti.getListaProfessoriAssociati().get(i).getNome();
                                href = "InformazioniGeneraliDocente?id=" + elencoDocenti.getListaProfessoriAssociati().get(i).getId() + "&s=" + elencoDocenti.getListaProfessoriAssociati().get(i).getSesso() + "&nome=" + elencoDocenti.getListaProfessoriAssociati().get(i).getNome() + "&cognome=" + elencoDocenti.getListaProfessoriAssociati().get(i).getCognome();
                                out.print("<li><a href='" + href + "'>" + nomeDocente + "</a></li>");
                            }
                        %>
                    </ul>
                </div>
                <div id="ELENCO_RICERCATORI">
                    <div id="TITOLO_ELENCO_RICERCATORI">Ricercatori</div>
                    <ul>
                        <%
                            for (int i = 0; i < elencoDocenti.getListaRicercatori().size(); i++) {
                                nomeDocente = elencoDocenti.getListaRicercatori().get(i).getCognome() + " " + elencoDocenti.getListaRicercatori().get(i).getNome();
                                href = "InformazioniGeneraliDocente?id=" + elencoDocenti.getListaRicercatori().get(i).getId() + "&s=" + elencoDocenti.getListaRicercatori().get(i).getSesso() + "&nome=" + elencoDocenti.getListaRicercatori().get(i).getNome() + "&cognome=" + elencoDocenti.getListaRicercatori().get(i).getCognome();
                                out.print("<li><a href='" + href + "'>" + nomeDocente + "</a></li>");
                            }
                        %>
                    </ul>
                </div>
                <div id="ELENCO_RICERCATORI_A_TEMPO_DETERMINATO">
                    <div id="TITOLO_ELENCO_RICERCATORI_A_TEMPO_DETERMIATO">Ricercatori a tempo determinato</div>
                    <ul>
                        <%
                            for (int i = 0; i < elencoDocenti.getListaRicercatoriATempoDeterminato().size(); i++) {
                                nomeDocente = elencoDocenti.getListaRicercatoriATempoDeterminato().get(i).getCognome() + " " + elencoDocenti.getListaRicercatoriATempoDeterminato().get(i).getNome();
                                href = "InformazioniGeneraliDocente?id=" + elencoDocenti.getListaRicercatoriATempoDeterminato().get(i).getId() + "&s=" + elencoDocenti.getListaRicercatoriATempoDeterminato().get(i).getSesso() + "&nome=" + elencoDocenti.getListaRicercatoriATempoDeterminato().get(i).getNome() + "&cognome=" + elencoDocenti.getListaRicercatoriATempoDeterminato().get(i).getCognome();
                                out.print("<li><a href='" + href + "'>" + nomeDocente + "</a></li>");
                            }
                        %>
                    </ul>
                </div>
            </div>
        </div>

        <%@include file="../Html/Footer.html" %>
    </body>
</html>
