<%-- 
    Document   : ModificaDocenteJsp
    Created on : 13-feb-2015, 15.57.17
    Author     : Stefano
--%>
<%@page import="it.unimol.profiles.beans.utils.ElencoSezioniPersonalizzate"%>
<%@page import="java.util.ArrayList"%>
<%@page import="it.unimol.profiles.beans.pagine.docente.InformazioniGeneraliDocente"%>
<%@page import="it.unimol.profiles.beans.utils.Docente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Docente docente = (Docente) request.getAttribute("docente");
    InformazioniGeneraliDocente informazioniGeneraliDocente = (InformazioniGeneraliDocente) request.getAttribute("informazioni_generali_docente");
    ElencoSezioniPersonalizzate elencoSezioniPersonalizzate = (ElencoSezioniPersonalizzate) request.getAttribute("elenco_sezioni_personalizzate");
    ArrayList<String> elencoRuoli = (ArrayList<String>) request.getAttribute("ruoli");
    ArrayList<String> elencoDipartimenti = (ArrayList<String>) request.getAttribute("dipartimenti");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link type="text/css" rel="stylesheet" href="Css/stile.css" />
        <link type="text/css" rel="stylesheet" href="Css/texteditor.css" />
        <script type="text/javascript" src="Javascript/advanced.js"></script>
        <script type="text/javascript" src="Javascript/wysihtml5-0.3.0.min.js"></script>

        <title>
            Modifica Docente
        </title>
    </head>
    <body>
        <%@include file="../../Html/Header.html" %>
        <div id="TITOLO_PAGINA">
            Modifica Docente
        </div>
        <div id="CONTENUTO_PAGINA">
            <%@include file="MenuAmministratore.jsp" %>
            <div id="CONTENUTO_SEZIONE_SELEZIONATA">
                <div id="NOME_DOCENTE_DA_MODIFICARE">
                    <%
                        out.println("Docente: " + docente.getNome() + " " + docente.getCognome());
                    %>
                </div>
                <div class="form_modifica_docente">
                    <form name="modifica_docente" action="ModificaDocente" method="POST">
                        <fieldset>
                            <legend>Aggiungi indirizzo email</legend>
                            <input type="hidden" name="action" value="aggiungi_indirizzo_email">
                            <input type="hidden" name="id_docente" value="<%out.print(docente.getId());%>">
                            <input type="email" name="email" placeholder="email@domain.ext" required>
                            <input type="submit" value="Aggiungi">
                        </fieldset>
                    </form>
                </div>
                <div class="form_modifica_docente">
                    <form name="modifica_docente" action="ModificaDocente" method="POST">
                        <fieldset>
                            <legend>Rimuovi indirizzo email</legend>
                            <input type="hidden" name="action" value="rimuovi_indirizzo_email">
                            <input type="hidden" name="id_docente" value="<%out.print(docente.getId());%>">
                            <select name="email" required>
                                <option value="">Seleziona un indirizzo email</option>
                                <%
                                    for (int i = 0; i < informazioniGeneraliDocente.getEmail().size(); i++) {
                                        out.println("<option value='" + informazioniGeneraliDocente.getEmail().get(i) + "'>" + informazioniGeneraliDocente.getEmail().get(i) + "</option>");
                                    }
                                %>
                            </select>
                            <input type="submit" value="Rimuovi">
                        </fieldset>

                    </form>
                </div>
                <div class="form_modifica_docente">
                    <form name="modifica_docente" action="ModificaDocente" method="POST">
                        <fieldset>
                            <legend>Aggiungi contatto telefonico</legend>
                            <input type="hidden" name="action" value="aggiungi_contatto_telefonico">
                            <input type="text" name="telefono" placeholder="numero telefonico" required>
                            <input type="hidden" name="id_docente" value="<%out.print(docente.getId());%>">
                            <input type="submit" value="Aggiungi">
                        </fieldset>

                    </form>
                </div>
                <div class="form_modifica_docente">
                    <form name="modifica_docente" action="ModificaDocente" method="POST">
                        <fieldset>
                            <legend>Rimuovi contatto telefonico</legend>
                            <input type="hidden" name="action" value="rimuovi_contatto_telefonico">
                            <input type="hidden" name="id_docente" value="<%out.print(docente.getId());%>">
                            <select name="contatti" required>
                                <option value="">Seleziona un contatto telefonico</option>
                                <%
                                    for (int i = 0; i < informazioniGeneraliDocente.getTelefono().size(); i++) {
                                        out.println("<option value='" + informazioniGeneraliDocente.getTelefono().get(i) + "'>" + informazioniGeneraliDocente.getTelefono().get(i) + "</option>");
                                    }
                                %>
                            </select>
                            <input type="submit" value="Rimuovi">
                        </fieldset>

                    </form>
                </div>
                <div class="form_modifica_docente">
                    <form name="modifica_docente" action="ModificaDocente" method="POST">
                        <fieldset>
                            <legend>Modifica Ruolo</legend>
                            <input type="hidden" name="action" value="modifica_ruolo">
                            <input type="hidden" name="id_docente" value="<%out.print(docente.getId());%>">
                            <select name="ruoli" required>
                                <option value="">Seleziona il nuovo ruolo</option>
                                <%
                                    for (int i = 0; i < elencoRuoli.size(); i++) {
                                        out.println("<option value='" + i + "'>" + elencoRuoli.get(i) + "</option>");
                                    }
                                %>
                            </select>
                            <input type="submit" value="Modifica">
                            <a href="#" title="Puoi inserire nuovi ruoli attraverso l'apposita funzionalità">?</a>
                        </fieldset>

                    </form>
                </div>
                <div class="form_modifica_docente">
                    <form name="modifica_docente" action="ModificaDocente" method="POST">
                        <fieldset>
                            <legend>Modifica Dipartimento</legend>
                            <input type="hidden" name="action" value="modifica_dipartimento">
                            <input type="hidden" name="id_docente" value="<%out.print(docente.getId());%>">
                            <select name="dipartimenti" required>
                                <option value="">Seleziona il nuovo dipartimento</option>
                                <%
                                    for (int i = 0; i < elencoDipartimenti.size(); i++) {
                                        out.println("<option value='" + i + "'>" + elencoDipartimenti.get(i) + "</option>");
                                    }
                                %>
                            </select>
                            <input type="submit" value="Modifica">
                            <a href="#" title="Puoi inserire nuovi dipartimenti attraverso l'apposita funzionalità">?</a>
                        </fieldset>

                    </form>
                </div>
                <div class="form_modifica_docente">
                    <form name="modifica_docente" action="ModificaDocente" method="POST">
                        <fieldset>
                            <legend>Configura pagina insegnamenti</legend>
                            <input type="hidden" name="action" value="configura_pagina_insegnamenti">
                            <input type="text" name="pagina_insegnamenti" placeholder="link pagina insegnamenti" required>
                            <input type="hidden" name="id_docente" value="<%out.print(docente.getId());%>">
                            <input type="submit" value="Configura">
                            <a href="#" title="Inserire qui il link alla attuale pagina degli insegnamenti del relativo docente del sito docenti.unimol.it. (Esempio: 'http://docenti.unimol.it/index.php?u=mario.rossi&id=0')">?</a>
                        </fieldset>

                    </form>
                </div>
                <div class="form_modifica_docente">
                    <form name="modifica_docente" action="ModificaDocente" method="POST" enctype="multipart/form-data">
                        <fieldset>
                            <legend>Modifica curriculum</legend>
                            <input type="hidden" name="action" value="modifica_curriculum">
                            <input type="hidden" name="id_docente" value="<%out.print(docente.getId());%>">
                            <input type="hidden" name="nome_docente" value="<%out.print(docente.getNome());%>">
                            <input type="hidden" name="cognome_docente" value="<%out.print(docente.getCognome());%>">
                            
                            <label>File PDF:</label>
                            <input type="file" name="file" required/>
                            <input type="submit" value="Salva" />
                        </fieldset>
                    </form>
                </div>
                <div class="form_modifica_docente">
                    <form name="modifica_docente" action="ModificaDocente" method="POST" enctype="multipart/form-data">
                        <fieldset>
                            <legend>Modifica pubblicazioni</legend>
                            <input type="hidden" name="action" value="modifica_pubblicazioni">
                            <input type="hidden" name="id_docente" value="<%out.print(docente.getId());%>">
                            <input type="hidden" name="nome_docente" value="<%out.print(docente.getNome());%>">
                            <input type="hidden" name="cognome_docente" value="<%out.print(docente.getCognome());%>">
                            <label>File .bib:</label>
                            <input type="file" name="file" required/>
                            <input type="submit" value="Salva" />
                            <a href="#" title="Inserire qui una descrizione su come ottenere il file bib">?</a> 
                        </fieldset>
                    </form>
                </div>
                <div class="form_modifica_docente">
                    <form name="modifica_docente" action="ModificaDocente" method="POST">
                        <fieldset>
                            <legend>Modifica orario di ricevimento</legend>
                            <input type="hidden" name="action" value="modifica_orario_di_ricevimento">
                            <div id="wysihtml5-toolbar" style="display: none;">
                                <a data-wysihtml5-command="bold">bold</a>
                                <a data-wysihtml5-command="italic">italic</a>

                            </div>
                            <textarea rows="20" cols="50" id="wysihtml5-textarea" name="orario_ricevimento" required>
                                
                            </textarea>
                            <input type="submit" value="Salva">
                        </fieldset>

                    </form>
                </div>
                <div class="form_modifica_docente">
                    <form name="modifica_docente" action="ModificaDocente" method="POST">
                        <fieldset>
                            <legend>Modifica una sezione personalizzata</legend>
                            <input type="hidden" name="action" value="modifica_sezione_personalizzata">
                            <select name="dipartimenti" required>
                                <option value="">Seleziona la sezione da modificare</option>
                                <%
                                    for (int i = 0; i < elencoSezioniPersonalizzate.size(); i++) {
                                        out.println("<option value='" + elencoSezioniPersonalizzate.getIdSezione(i) + "'>" + elencoSezioniPersonalizzate.getNomeSezione(i) + "</option>");
                                    }
                                %>
                            </select>
                            <input type="submit" value="Modifica" />
                        </fieldset>

                    </form>
                </div>
                <div class="form_modifica_docente">
                    <form name="modifica_docente" action="ModificaDocente" method="POST" enctype="multipart/form-data">
                        <fieldset>
                            <legend>Modifica immagine profilo</legend>
                            <input type="hidden" name="action" value="modifica_immagine_profilo">
                            <input type="hidden" name="id_docente" value="<%out.print(docente.getId());%>">
                            <input type="hidden" name="nome_docente" value="<%out.print(docente.getNome());%>">
                            <input type="hidden" name="cognome_docente" value="<%out.print(docente.getCognome());%>">
                            
                            <label>File Immagine:</label>
                            <input type="file" name="file" required/>
                            <input type="submit" value="Salva"/>
                            <a href="#" title="Sono accettati solo formati .jpeg e .png">?</a> 
                        </fieldset>
                    </form>
                </div>

            </div>
        </div>
        <%@include file="../../Html/Footer.html" %>
        <script>
            var editor = new wysihtml5.Editor("wysihtml5-textarea", {// id of textarea element
                toolbar: "wysihtml5-toolbar", // id of toolbar element
                parserRules: wysihtml5ParserRules // defined in parser rules set 
            });
        </script>
    </body>
</html>

