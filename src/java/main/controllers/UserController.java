package main.controllers;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.validation.Valid;

import main.beans.MySession;
import main.img.ComparingImages;
import main.repo.UserRepository;
import main.repo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


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
    public String main(User user ) {
        sessionObj.setUserName("elhanan");
        return "uploadFile";
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
    if (!repository.findByUserName(userName).getPassword().equals(password)) {
            model.addAttribute("error",true);
            return "LoginPage";
        }
        sessionObj.setConnected(true);
        sessionObj.setUserName(userName);
        return "uploadFile";
    }



    @GetMapping("/sortImage")
    public String sortImage(Model model ) throws Exception {
       ComparingImages sortAction = new ComparingImages(sessionObj.getUserName(),sessionObj.getArrImg(),sessionObj.getArrTemp());
       sortAction.run();
       model.addAttribute("user", sessionObj.getUserName());
       sessionObj.setArrImg(sortAction.getArrImg());
       return "gallery";
    }


    @PostMapping("/getThePicture")
    @ResponseBody
    public void getPictName(@RequestBody  ArrayList<String> arrOfImg) throws IOException {
    System.out.println(arrOfImg);
        for (String namePic:arrOfImg) {
            String[] temp = namePic.split("\\.");
            File outfile = new File("\\Users\\owner\\myNewFile\\"+namePic.trim());
            ImageIO.write(sessionObj.getArrImg().get(namePic.trim()),temp[temp.length-1] , outfile);
        }
    }


    @GetMapping("/getListImg")
    public @ResponseBody ArrayList<ArrayList<String>> getListImg( ) {

        return sessionObj.getArrTemp();
    }

    @GetMapping(value="/json")
    public String json (Model model) {
        return "json";
    }
    /**
     * a sample controller return the content of the DB in JSON format
     * @param model
     * @return
     */
    @GetMapping(value="/getjson")
    public @ResponseBody List<User> getAll(Model model) {

        return getRepo().findAll();
    }
}

