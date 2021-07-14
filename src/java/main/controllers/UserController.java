package main.controllers;

import javax.validation.Valid;

import main.repo.UserRepository;
import main.repo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class UserController {

    /* we inject a property from the application.properties  file */
    @Value( "${demo.coursename}" )
    private String someProperty;

    /* we need it so  inject the User repo bean */
    @Autowired
    private UserRepository repository;

    private UserRepository getRepo() {
        return repository;
    }

    @GetMapping("/")
    public String main(User user ) {

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

        return "MainPage";
    }

    @PostMapping("/adduser")
    public String addUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-user";
        }
        getRepo().save(user);
        model.addAttribute("users", getRepo().findAll());
        return "index";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {

        User user = getRepo().findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "update-user";
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

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {

        User user = getRepo().findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        getRepo().delete(user);
        model.addAttribute("users", getRepo().findAll());
        return "index";
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

