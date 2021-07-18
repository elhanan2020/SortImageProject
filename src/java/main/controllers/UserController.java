package main.controllers;

import javax.annotation.Resource;
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
       ComparingImages sortAction = new ComparingImages(sessionObj.getUserName(),sessionObj.getArrImg());
       sortAction.run();
      model.addAttribute("user", sessionObj.getUserName());
      sessionObj.setArrImg(sortAction.getArrImg());
      return "uploadFile";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "update-user";
        }

        getRepo().save(user);
        model.addAttribute("users", getRepo().findAll());
        return "index";
    }

    @GetMapping("/getListImg")
    public @ResponseBody List<HashMap<Integer, Integer>> getListImg( ) {
        ArrayList<ArrayList<BufferedImage>> temp = sessionObj.getArrImg();
        List<HashMap<Integer, Integer>> myList = new ArrayList<>();
        for (int i = 0 ; i< temp.size() ; i++) {
            HashMap<Integer, Integer> map = new HashMap<>();
            map.put(i,temp.get(i).size());
            myList.add(map);
        }
        return myList;
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

