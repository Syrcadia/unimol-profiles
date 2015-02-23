package it.unimol.profiles;

import it.unimol.profiles.exceptions.UploadNonValidoException;
import it.unimol.profiles.servlet.sviluppo.Log;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    private static final String radice="C:/UnimolProfilesData";
    
    public static void inserisciFile(HttpServletRequest request, String destinazioneFile, String nomeFile) throws IOException, ServletException, UploadNonValidoException {

//        String appPath = request.getServletContext().getRealPath("");
        String appPath=radice;
        String savePath = appPath + File.separator + destinazioneFile;
        
        Log.log(savePath);
        
        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }

        for (Part part : request.getParts()) {
            if ("file".equals(part.getName())) {
                String fileName = nomeFile;
//                String fileName = extractFileName(part);
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
