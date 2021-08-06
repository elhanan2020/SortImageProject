package main.controllers;
import main.beans.MySession;
import main.data.FileMetaData;
import main.exeption.FileStorageException;
import main.service.FileStorageService;
import main.utils.UploadFileProperties;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Controller
public class FileUploadController extends PageController{

    @Resource(name = "sessionBean")
    public MySession sessionObj;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    UploadFileProperties uploadFileProperties;

    /**
     * Controller to display the file upload form on the frontend.
     * @param model
     * @return
     */
    @GetMapping("/upload-file")
    public String uploadFile(final Model model){
        model.addAttribute("connect",true);
        return "uploadFile";
    }

    /**
     * POST method to accept the incoming file in the application.This method is designed to accept
     * only 1 file at a time.

     * @return succes page
     */
    @PostMapping("/upload-file")
    public String uploadFile(@RequestParam("files[]") MultipartFile[] files, Model model) throws IOException {
        Path myPath = Paths.get(uploadFileProperties.getUploadDir()).toAbsolutePath().normalize();
        sessionObj.reset(true);
        int counter = 0;
        File myFile = new File (myPath +"\\"+ sessionObj.getUserName());
        for (File subFile : Objects.requireNonNull(myFile.listFiles())) {
            Files.delete(subFile.toPath());
        }
        try {
            for (MultipartFile file :files) {
                String[] img = file.getContentType().split("/");
                if(img[0].equals("image"))
                    {
                        FileMetaData data = fileStorageService.store(file,sessionObj.getUserName());
                        counter++;
                        data.setUrl(fileDownloadUrl(data.getFileName(),"/media/download/"));
                    }
            }
        } catch (FileStorageException e) {
            model.addAttribute("error", "Unable to store the file");
            model.addAttribute("connect",true);
            return "uploadFile";
        }
        model.addAttribute("connect",true);
        return "action";
    }
}
