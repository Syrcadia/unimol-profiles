<%-- 
    Document   : NomeDocente
    Created on : 16-dic-2014, 16.20.21
    Author     : Stefano
--%>

<%
    if (docente.getSesso().equalsIgnoreCase("F")) {
        out.print("Prof.ssa ");
    } else {
        out.print("Prof. ");
    }
    out.print(docente.getNome() + " "
            + docente.getCognome());
%>