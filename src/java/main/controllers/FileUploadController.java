package main.controllers;
import main.beans.MySession;
import main.data.FileMetaData;
import main.exeption.FileStorageException;
import main.service.FileStorageService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
public class FileUploadController extends PageController{

    @Resource(name = "sessionBean")
    public MySession sessionObj;

    @Autowired
    FileStorageService fileStorageService;



    /**
     * Controller to display the file upload form on the frontend.
     * @param model
     * @return
     */
    @GetMapping("/upload-file")
    public String uploadFile(final Model model){
        return "uploadFile";
    }

    /**
     * POST method to accept the incoming file in the application.This method is designed to accept
     * only 1 file at a time.

     * @return succes page
     */
    @PostMapping("/upload-file")
    public String uploadFile(@RequestParam("files[]") MultipartFile[] files, Model model){

        try {
            for (MultipartFile file :files) {
                String[] img = file.getContentType().split("/");
                if(img[0].equals("image"))
                    {     FileMetaData data = fileStorageService.store(file,sessionObj.getUserName());

                          data.setUrl(fileDownloadUrl(data.getFileName(),"/media/download/"));
                    }
            }
        } catch (FileStorageException e) {
            model.addAttribute("error", "Unable to store the file");
            return "uploadFile";
        }
        return "action";
    }



}
