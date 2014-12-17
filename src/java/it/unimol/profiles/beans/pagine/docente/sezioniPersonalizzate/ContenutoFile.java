package it.unimol.profiles.beans.pagine.docente.sezioniPersonalizzate;

/**
 *
 * @author Stefano
 */
public class ContenutoFile extends Contenuto{
    String fileLink;
    String descrizioneFile;

    public String getFileLink() {
        return fileLink;
    }

    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }

    public String getDescrizioneFile() {
        return descrizioneFile;
    }

    public void setDescrizioneFile(String descrizioneFile) {
        this.descrizioneFile = descrizioneFile;
    }

    @Override
    public String toString() {
        return descrizioneFile;
    }
   
    
}
