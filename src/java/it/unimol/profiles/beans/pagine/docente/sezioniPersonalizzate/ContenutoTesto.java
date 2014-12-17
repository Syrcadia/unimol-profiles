package it.unimol.profiles.beans.pagine.docente.sezioniPersonalizzate;

/**
 *
 * @author Stefano
 */
public class ContenutoTesto extends Contenuto{
    
    String linkHtml;

    public String getLinkHtml() {
        return linkHtml;
    }

    public void setLinkHtml(String linkHtml) {
        this.linkHtml = linkHtml;
    }
    
    @Override
    public String toString() {
        return linkHtml;
    }
    
}
