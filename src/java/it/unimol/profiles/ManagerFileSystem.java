package it.unimol.profiles;

import it.unimol.profiles.exceptions.UploadNonValidoException;
import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

/**
 *
 * @author Stefano
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10,
        maxFileSize = 1024 * 1024 * 100,
        maxRequestSize = 1024 * 1024 * 100)

public class ManagerFileSystem {

    public static final String destinazioneFileProva = "/Dev/ProvaInserimento/";

    public static void inserisciFile(HttpServletRequest request, String destinazioneFile) throws IOException, ServletException, UploadNonValidoException {
        destinazioneFile = destinazioneFileProva;

        String appPath = request.getServletContext().getRealPath("");
        String savePath = appPath + File.separator + destinazioneFile;

        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }

        for (Part part : request.getParts()) {
            if ("file".equals(part.getName())) {
                String fileName = extractFileName(part);
                part.write(savePath + File.separator + fileName);
            }
        }
    }

    private static String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return null;
    }
}
