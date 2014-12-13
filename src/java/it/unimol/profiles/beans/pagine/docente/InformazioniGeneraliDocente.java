package it.unimol.profiles.beans.pagine.docente;

import java.util.ArrayList;

/**
 *
 * @author Stefano
 */
public class InformazioniGeneraliDocente {

    private String nome;
    private String cognome;
    private String ruolo;
    private ArrayList<String> email;
    private String dipartimento;
    private ArrayList<String> telefono;

    public InformazioniGeneraliDocente() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public ArrayList<String> getEmail() {
        return email;
    }

    public void setEmail(ArrayList<String> email) {
        this.email = email;
    }

    public ArrayList<String> getTelefono() {
        return telefono;
    }

    public void setTelefono(ArrayList<String> telefono) {
        this.telefono = telefono;
    }

    public String getDipartimento() {
        return dipartimento;
    }

    public void setDipartimento(String dipartimento) {
        this.dipartimento = dipartimento;
    }

    public static InformazioniGeneraliDocente getStub() {
        InformazioniGeneraliDocente stub = new InformazioniGeneraliDocente();
        stub.setNome("Fausto");
        stub.setCognome("Fasano");
        stub.setDipartimento("Bioscienze e Territorio");
        ArrayList<String> emails = new ArrayList();
        emails.add("fausto.fasano@unimol.it");
        emails.add("fausto.fasano@unimol.com");
        
        stub.setEmail(emails);
        stub.setRuolo("Ricercatore");
        
        ArrayList<String> telefono = new ArrayList();
        telefono.add("0865111111");
        telefono.add("3333333333");
        stub.setTelefono(telefono);

        return stub;
    }
}
