<%
    if (docente.getSesso().equalsIgnoreCase("F")) {
        out.print("Prof.ssa ");
    } else {
        out.print("Prof. ");
    }
    out.print(docente.getNome() + " "
            + docente.getCognome());
%>