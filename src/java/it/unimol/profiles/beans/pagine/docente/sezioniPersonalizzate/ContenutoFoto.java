package it.unimol.profiles.beans.pagine.docente.sezioniPersonalizzate;

/**
 *
 * @author Stefano
 */
public class ContenutoFoto extends Contenuto{
    
    private String linkFoto;
    private String descrizioneFoto;

    public String getLinkFoto() {
        return linkFoto;
    }

    public void setLinkFoto(String linkFoto) {
        this.linkFoto = linkFoto;
    }

    public String getDescrizioneFoto() {
        return descrizioneFoto;
    }

    public void setDescrizioneFoto(String descrizioneFoto) {
        this.descrizioneFoto = descrizioneFoto;
    }
    
    @Override
    public String toString() {
        return descrizioneFoto;
    }
    
}
