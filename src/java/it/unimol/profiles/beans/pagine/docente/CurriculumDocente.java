package it.unimol.profiles.beans.pagine.docente;

/**
 *
 * @author Stefano
 */
public class CurriculumDocente {

    private String htmlLink;
    private String fileLink;

    public CurriculumDocente() {

    }

    public String getHtmlLink() {
        return htmlLink;
    }

    public void setHtmlLink(String curriculumHtml) {
        this.htmlLink = curriculumHtml;
    }

    public String getPdfLink() {
        return fileLink;
    }

    public void setPdfLink(String fileLink) {
        this.fileLink = fileLink;
    }
}
