package main.controllers;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import main.beans.Messages;
import main.listeners.SessionListenerCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SpringSessionController {

    @Resource(name="sessionListenerWithMetrics")
    private ServletListenerRegistrationBean<SessionListenerCounter> metrics;

    // named bean injection
    @Resource(name = "sessionBeanExample")
    private Messages messages;

    // OR autowired by type
    //@Autowired
    //private Messages messageBean;

    @GetMapping("/session")
    public String process(Model model) {

        model.addAttribute("sessionMessages", messages.getMessages());
        return "session";
    }

    @PostMapping("/persistMessage")
    public String persistMessage(@RequestParam(required = false) String msg ) {
        if (msg == null)
            System.out.println("null message !");
        else
            messages.add(msg);

        return "redirect:/session";
    }

    @PostMapping("/destroy")
    public String destroySession(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/session";
    }
}