package it.unimol.profiles;

import it.unimol.profiles.exceptions.UploadNonValidoException;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Gerardo - Ilaria
 */
public class ManagerFileSystem {
    
   //private static final String UPLOAD_DIRECTORY = "/Risorse/upload";
    private static final int THRESHOLD_SIZE = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB
     public static final String destinazioneFileProva = "/Dev/ProvaInserimento/";
       
    public static void inserisciFile(HttpServletRequest request,String destinazioneFile, HttpServlet servlet) throws UploadNonValidoException{
        destinazioneFile = destinazioneFileProva; 
        if (!ServletFileUpload.isMultipartContent(request)) {
           //eccezione
           return; 
        }
        
        
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(THRESHOLD_SIZE);
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(MAX_FILE_SIZE);
        upload.setSizeMax(MAX_REQUEST_SIZE);

        String uploadPath = servlet.getServletContext().getRealPath("")
                + File.separator + destinazioneFile;
        // creates the directory if it does not exist
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        
        try {
            List formItems = upload.parseRequest(request);
            Iterator iter = formItems.iterator();

            // iterates over form's fields
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                // processes only fields that are not form fields
                if (!item.isFormField()) {
                    String fileName = new File(item.getName()).getName();
                    String filePath = uploadPath + File.separator + fileName;
                    File storeFile = new File(filePath);

                    // saves the file on disk
                    item.write(storeFile);
                }
            }
            request.setAttribute("message", "Upload avvenuto con successo!");
        } catch (Exception ex) {
            request.setAttribute("message", "C'è un errore: " + ex.getMessage());
        }
    }
    }
    

