package main.controllers;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import main.beans.MySession;
import main.img.ComparingImages;
import main.repo.UserRepository;
import main.repo.User;
import main.utils.UploadFileProperties;
import org.apache.commons.io.IOUtils;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


@Controller
public class UserController {

    /* we inject a property from the application.properties  file */
    @Value( "${demo.coursename}" )
    private String someProperty;

    @Resource(name = "sessionBean")
    public MySession sessionObj;

    @Autowired
    UploadFileProperties uploadFileProperties;

    /* we need it so  inject the User repo bean */
    @Autowired
    private UserRepository repository;

    private UserRepository getRepo() {
        return repository;
    }

    @GetMapping("/")
    public String main(User user ,Model model) throws IOException {
       /* if(sessionObj.getConnected()){
            model.addAttribute("connect",true);
            model.addAttribute("toDownload" ,sessionObj.getArrImg().size() > 0);
            return "uploadFile";
        }
        model.addAttribute("connect",false);*/
        File file = new File("/Users/owner/myNewFile/" +"\\"+ sessionObj.getUserName());
        if (file.mkdir())
            System.out.println("Folder is created!");
        else
            System.out.println("Folder already exists.");
        sessionObj.setUserName("elhanan");
        model.addAttribute("connect",sessionObj.getConnected());
        model.addAttribute("toDownload", false);
        return "uploadFile";
    }

    @GetMapping("/download")
    public String download(Model model) {
        model.addAttribute("connect",true);
        return "download";
    }



    @GetMapping("/gallery")
    public String gallery(Model model ) {
        model.addAttribute("connect",true);
        return "gallery";
    }



    @PostMapping("/signUp")
    public String showSignUpForm(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("connect",false);
            return "SignUp";
        }
        else if(repository.existsByUserName(user.getUserName())){
            model.addAttribute("connect",false);
            model.addAttribute("error",true);
            return "SignUp";
        }

        repository.save(user);
        model.addAttribute("connect",false);
        return "LoginPage";
    }


    @PostMapping ("/logIn")
    public String logIn(@RequestParam String userName, @RequestParam String password, Model model) {
          Path myPath = Paths.get(uploadFileProperties.getUploadDir()).toAbsolutePath().normalize();
        if (!repository.existsByUserName(userName)){
                model.addAttribute("error1",true);
                model.addAttribute(" connected",false);
                return "LoginPage";
            }
        else if (!repository.findByUserName(userName).getPassword().equals(password)){
            model.addAttribute("error2",true);
            model.addAttribute(" connected",false);
            return "LoginPage";
        }
        sessionObj.setConnected(true);
        sessionObj.setUserName(userName);
        File file = new File(myPath +"\\"+ userName);
        if (file.mkdir())
             System.out.println("Folder is created!");
        else
            System.out.println("Folder already exists.");
        model.addAttribute("connect",true);
        return "uploadFile";
    }





    @GetMapping("/inscription")
    public String inscription(@Valid User user, BindingResult result,Model model ) {
        model.addAttribute("connect",false);
        return "SignUp";
    }


    @PostMapping("/getThePicture")
    @ResponseBody
    public String getPictName(@RequestBody  ArrayList<String> arrOfImg,Model model) throws IOException {
        Path myPath = Paths.get(uploadFileProperties.getUploadDir()).toAbsolutePath().normalize();
        for (String namePic:arrOfImg) {
            String[] temp = namePic.split("\\.");
            File outfile = new File(myPath + sessionObj.getUserName() +"\\"+ namePic.trim());
            ImageIO.write(sessionObj.getArrImg().get(namePic.trim()),temp[temp.length-1] , outfile);
        }
        sessionObj.setArrImg(new HashMap<>());
        model.addAttribute("connect",true);
        return "download";
    }


    @PostMapping("/getAllThePicture")
    @ResponseBody
    public String getAllThePicture(@RequestBody  ArrayList<String> arrOfImg,Model model) throws IOException {
        Path myPath = Paths.get(uploadFileProperties.getUploadDir()).toAbsolutePath().normalize();
        ArrayList<String> cont = new ArrayList<>(Arrays.asList(Objects.requireNonNull(new File ("\\Users\\owner\\myNewFile\\" + sessionObj.getUserName()).list())));
        for (String imageName:cont) {
            if(!arrOfImg.contains(imageName))
                Files.delete(Paths.get(myPath + "\\" + sessionObj.getUserName() + "\\"+imageName ));
        }
        model.addAttribute("connect",false);
        return "download";
    }


    @GetMapping("/getListImg")
    public @ResponseBody ArrayList<ArrayList<String>> getListImg( ) {
    while (!sessionObj.isFinish()[0]){
    }
        return sessionObj.getArrTemp();
    }



    @GetMapping("/getAllImg")
    public @ResponseBody ArrayList<String> getAllImg () {
        Path myPath = Paths.get(uploadFileProperties.getUploadDir()).toAbsolutePath().normalize();
        return new ArrayList<>(Arrays.asList(Objects.requireNonNull(new File (myPath +"\\" + sessionObj.getUserName() ).list())));
    }
    @GetMapping("/getNumberImg")
    public @ResponseBody ArrayList<Integer> getNumberImg () {
        Path myPath = Paths.get(uploadFileProperties.getUploadDir()).toAbsolutePath().normalize();
        return new ArrayList<Integer>(Arrays.asList(sessionObj.getNumOfInitialPicture(),new ArrayList<>(Arrays.asList(Objects.requireNonNull(new File (myPath +"\\" + sessionObj.getUserName() ).list()))).size()));
    }


    @RequestMapping(value="/myPhotos.zip", produces="application/zip")
    public void zipFiles(HttpServletResponse response) throws IOException {
        Path myPath = Paths.get(uploadFileProperties.getUploadDir()).toAbsolutePath().normalize();

        //setting headers
        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader("Content-Disposition", "attachment; filename=\""+sessionObj.getUserName() + ".zip\"");

        ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());

        // create a list to add files to be zipped
        File dir = new File(myPath +"\\" + sessionObj.getUserName()+"\\");
        File[] filesArray = dir.listFiles();

        // package files
        assert filesArray != null;
        for (File file : filesArray) {
            //new zip entry and copying inputstream with file to zipOutputStream, after all closing streams
            zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
            FileInputStream fileInputStream = new FileInputStream(file);

            IOUtils.copy(fileInputStream, zipOutputStream);

            fileInputStream.close();
            zipOutputStream.closeEntry();
        }

        zipOutputStream.close();
    }
}

