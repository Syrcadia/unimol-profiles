package it.unimol.profiles.beans.pagine.docente;

import java.util.ArrayList;

/**
 *
 * @author Stefano
 */
public class InsegnamentiDocente {

    private String testoFormattatoHtml;

    public String getTestoFormattatoHtml() {
        return testoFormattatoHtml;
    }

    public void setTestoFormattatoHtml(String testoFormattatoHtml) {
        this.testoFormattatoHtml = testoFormattatoHtml;
    }

    public InsegnamentiDocente() {
    }

    public static InsegnamentiDocente getStub() {
        InsegnamentiDocente stub = new InsegnamentiDocente();
        return stub;
    }

}
