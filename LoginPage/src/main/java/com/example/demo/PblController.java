package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PblController {
    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "Hello World\n";
    }

    @GetMapping("/users/{username}")
    @ResponseBody
    public String useProfile (@PathVariable String username) {
        return String.format("user: %s", username);
    }

    @RequestMapping(value="/index", method= RequestMethod.GET)
    public String sayHelloForm(Model model) {
        model.addAttribute("userProfile", new UserProfile());
        return "index";
    }


    @RequestMapping(value="/index", method=RequestMethod.POST)
    public String sayHello(@ModelAttribute UserProfile userProfile, Model model) {
        model.addAttribute("userProfile", userProfile);
        return "message";
    }

}
