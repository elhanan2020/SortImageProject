package main.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/*
  this controller handles all exceptions/errors and displays a friendly
  error page/

  for example try the URL:
            http://localhost:8080/delete/456

  where 456 is an ID that does not exist in the database
 */
@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        return "error";
    }

}