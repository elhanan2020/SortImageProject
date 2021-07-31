package main.controllers;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import main.beans.MySession;
import main.img.ComparingImages;
import main.repo.UserRepository;
import main.repo.User;
import org.apache.commons.io.IOUtils;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
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


    /* we need it so  inject the User repo bean */
    @Autowired
    private UserRepository repository;

    private UserRepository getRepo() {
        return repository;
    }

    @GetMapping("/")
    public String main(User user ) throws IOException {
        sessionObj.setUserName("Elhanan");
        File file = new File("\\Users\\owner\\myNewFile\\" + sessionObj.getUserName());
        if (file.mkdir())
        {
            System.out.println("Folder is created!");
        } else {
            System.out.println("Folder already exists.");
        }

        return "uploadFile";
        //return "LoginPage";
    }
    @GetMapping("/about")
    public String about( ) {
        return "about";
    }
    @GetMapping("/logOut")
    public String logOut( ) {
        File file = new File ("\\Users\\owner\\myNewFile\\" + sessionObj.getUserName());
        for (File subFile : Objects.requireNonNull(file.listFiles())) {
            subFile.delete();
        }
        sessionObj.reset();
        return "redirect:/";
    }
    @GetMapping("/gallery")
    public String gallery( ) {
        return "gallery";
    } 
    @GetMapping("/Self-choice")
    public String Selfchoice( ) {
        return "choicePage";
    }


    @PostMapping("/signUp")
    public String showSignUpForm(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "SignUp";
        }
        repository.save(user);
        return "LoginPage";
    }
    @PostMapping ("/logIn")
    public String logIn(@RequestParam String userName, @RequestParam String password, Model model) {
        if (!repository.existsByUserName(userName)||!repository.findByUserName(userName).getPassword().equals(password)) {
                model.addAttribute("error",true);
                return "LoginPage";
            }
        sessionObj.setConnected(true);
        sessionObj.setUserName(userName);
        File file = new File("\\Users\\owner\\myNewFile\\" + userName);
        if (file.mkdir())
        {
            System.out.println("Folder is created!");
        } else {
            System.out.println("Folder already exists.");
        }
        return "uploadFile";
    }



    @GetMapping("/sortImageAndFiltering")
    public String sortImageAndfiltering(Model model ) throws Exception {
       ComparingImages sortAction = new ComparingImages(sessionObj.getUserName(),sessionObj.getArrImg(),sessionObj.getArrTemp(),sessionObj.isFinish(),"SortingAndFiltering");
       sortAction.start();
       model.addAttribute("user", sessionObj.getUserName());
       return "gallery";
    }
    @GetMapping("/Filtering")
    public String sortImage(Model model ) throws Exception {
        ComparingImages sortAction = new ComparingImages(sessionObj.getUserName(),sessionObj.getArrImg(),sessionObj.getArrTemp(),sessionObj.isFinish(),"FILTERING");
        sortAction.start();
        model.addAttribute("user", sessionObj.getUserName());
        return "download";
    }
    @GetMapping("/Sorting")
    public String Sorting(Model model ) throws Exception {
        ComparingImages sortAction = new ComparingImages(sessionObj.getUserName(),sessionObj.getArrImg(),sessionObj.getArrTemp(),sessionObj.isFinish(),"SORTING");
        sortAction.start();
        model.addAttribute("user", sessionObj.getUserName());
        sessionObj.setArrImg(sortAction.getArrImg());
        return "gallery";
    }

    @GetMapping("/inscription")
    public String inscription(Model model ) {
       return "SignUp";
    }


    @PostMapping("/getThePicture")
    @ResponseBody
    public String getPictName(@RequestBody  ArrayList<String> arrOfImg) throws IOException {
    System.out.println(arrOfImg);
        for (String namePic:arrOfImg) {
            String[] temp = namePic.split("\\.");
            File outfile = new File("\\Users\\owner\\myNewFile\\" + sessionObj.getUserName() +"\\"+ namePic.trim());
            ImageIO.write(sessionObj.getArrImg().get(namePic.trim()),temp[temp.length-1] , outfile);
        }
        sessionObj.setArrImg(new HashMap<>());
        return "download";
    }
    @PostMapping("/getAllThePicture")
    @ResponseBody
    public String getAllThePicture(@RequestBody  ArrayList<String> arrOfImg) throws IOException {
        ArrayList<String> cont = new ArrayList<>(Arrays.asList(Objects.requireNonNull(new File ("\\Users\\owner\\myNewFile\\" + sessionObj.getUserName()).list())));
        for (String imageName:cont) {
            if(!arrOfImg.contains(imageName))
                Files.delete(Paths.get("\\Users\\owner\\myNewFile\\" + sessionObj.getUserName() + "\\"+imageName ));
        }
        return "download";
    }


    @GetMapping("/getListImg")
    public @ResponseBody ArrayList<ArrayList<String>> getListImg( ) {
    while (!sessionObj.isFinish()[0]){
    }
        return sessionObj.getArrTemp();
    }
    @GetMapping("/isFinish")
    public @ResponseBody void isFinish( ) {
    while (!sessionObj.isFinish()[0]){

    }
    }

    @GetMapping("/getAllImg")
    public @ResponseBody ArrayList<String> getAllImg () {
        return new ArrayList<>(Arrays.asList(Objects.requireNonNull(new File ("\\Users\\owner\\myNewFile\\" + sessionObj.getUserName() ).list())));
    }

    /*@RequestMapping(value = "/myPhotos.zip")
    @ResponseBody
    byte[] filesZip() throws IOException {
        File dir = new File("C:\\Users\\owner\\myNewFile\\" + sessionObj.getUserName()+"\\");
        File[] filesArray = dir.listFiles();
        if (filesArray == null || filesArray.length == 0)
            System.out.println(dir.getAbsolutePath() + " have no file!");
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ZipOutputStream zipOut= new ZipOutputStream(bo);
        assert filesArray != null;
        for(File xlsFile:filesArray){
            if(!xlsFile.isFile())continue;
            ZipEntry zipEntry = new ZipEntry(xlsFile.getName());
            zipOut.putNextEntry(zipEntry);
            zipOut.write(IOUtils.toByteArray(new FileInputStream(xlsFile)));
            zipOut.closeEntry();
        }
        bo.close();
        zipOut.close();

       *//*File file = new File ("\\Users\\owner\\myNewFile\\" + sessionObj.getUserName());
        for (File subFile : Objects.requireNonNull(file.listFiles())) {
            Files.delete(subFile.toPath());
        }*//*
        return bo.toByteArray();
    }*/
    @RequestMapping(value="/myPhotos.zip", produces="application/zip")
    public void zipFiles(HttpServletResponse response) throws IOException {

        //setting headers
        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader("Content-Disposition", "attachment; filename=\""+sessionObj.getUserName() + ".zip\"");

        ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());

        // create a list to add files to be zipped
        File dir = new File("C:\\Users\\owner\\myNewFile\\" + sessionObj.getUserName()+"\\");
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
        File file = new File ("\\Users\\owner\\myNewFile\\" + sessionObj.getUserName());
        for (File subFile : Objects.requireNonNull(file.listFiles())) {
            Files.delete(subFile.toPath());
        }
    }

}

