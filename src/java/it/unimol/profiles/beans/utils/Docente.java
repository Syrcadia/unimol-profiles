package it.unimol.profiles.beans.utils;

/**
 *
 * @author Stefano
 */
public class Docente {

    private String nome;
    private String cognome;
    private String id;
    private String sesso;

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

    public String getId() {
        return id;
    }

    public void setId(String email) {
        this.id = email;
    }

    public String getSesso() {
        return sesso;
    }

    public void setSesso(String sesso) {
        this.sesso = sesso;
    }
    
    public String getNomeCartella() {
        return this.getId();
    }

    public static Docente getStub() {
        Docente stub = new Docente();
        stub.setNome("Fausto");
        stub.setCognome("Fasano");
        stub.setId("faufas");
        stub.setSesso("m");  
        return stub;
    }
}
