package main.controllers;
import main.beans.MySession;
import main.data.FileMetaData;
import main.exeption.FileStorageException;
import main.img.ComparingImages;
import main.service.FileStorageService;
import main.utils.UploadFileProperties;
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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
public class navBarController {

    @Resource(name = "sessionBean")
    public MySession sessionObj;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    UploadFileProperties uploadFileProperties;


    @GetMapping("/logOut")
    public String logOut( ) {
        Path myPath = Paths.get(uploadFileProperties.getUploadDir()).toAbsolutePath().normalize();
        File file = new File (myPath+"\\" + sessionObj.getUserName());
        for (File subFile : Objects.requireNonNull(file.listFiles())) {
            subFile.delete();
        }
        file.delete();
        sessionObj.reset(false);
        sessionObj.setUserName("");
        return "redirect:/";
    }

    @GetMapping("/about")
    public String about(Model model ) {

        model.addAttribute("connect",sessionObj.getConnected());
        return "about";
    }
}
