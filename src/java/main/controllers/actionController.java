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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;


@Controller
public class actionController {

    @Resource(name = "sessionBean")
    public MySession sessionObj;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    UploadFileProperties uploadFileProperties;


    @GetMapping("/sortImageAndFiltering")
    public String sortImageAndfiltering(Model model ) throws Exception {
        Path myPath = Paths.get(uploadFileProperties.getUploadDir()).toAbsolutePath().normalize();

        ComparingImages sortAction = new ComparingImages(myPath.toString(),sessionObj.getUserName(),sessionObj.getArrImg(),sessionObj.getArrTemp(),sessionObj.isFinish(),"SortingAndFiltering");
        sortAction.start();
        model.addAttribute("user", sessionObj.getUserName());
        model.addAttribute("connect",true);
        return "gallery";
    }

    @GetMapping("/Filtering")
    public String sortImage(Model model ) throws Exception {
        Path myPath = Paths.get(uploadFileProperties.getUploadDir()).toAbsolutePath().normalize();
        ComparingImages sortAction = new ComparingImages(myPath.toString(),sessionObj.getUserName(),sessionObj.getArrImg(),sessionObj.getArrTemp(),sessionObj.isFinish(),"FILTERING");
        sortAction.start();
        model.addAttribute("user", sessionObj.getUserName());
        model.addAttribute("connect",true);
        return "download";
    }

    @GetMapping("/Sorting")
    public String Sorting(Model model ) throws Exception {
        Path myPath = Paths.get(uploadFileProperties.getUploadDir()).toAbsolutePath().normalize();
        ComparingImages sortAction = new ComparingImages(myPath.toString(),sessionObj.getUserName(),sessionObj.getArrImg(),sessionObj.getArrTemp(),sessionObj.isFinish(),"SORTING");
        sortAction.start();
        model.addAttribute("user", sessionObj.getUserName());
        sessionObj.setArrImg(sortAction.getArrImg());
        model.addAttribute("connect",true);
        return "gallery";
    }

    @GetMapping("/Self-choice")
    public String Selfchoice(Model model) {
        model.addAttribute("connect",false);
        return "choicePage";
    }


    @GetMapping("/isFinish")
    public @ResponseBody void isFinish( ) {
        while (!sessionObj.isFinish()[0]){

        }
    }

}
