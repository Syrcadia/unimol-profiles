<div id="FOTO_DOCENTE">
    <img src="<%
        out.print((String) request.getAttribute("percorso_foto_profilo")); %>" alt="Foto di <% out.print(docente.getNome() + " " + docente.getCognome());
         %>"/>
</div>