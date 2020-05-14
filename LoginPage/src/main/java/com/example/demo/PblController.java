package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PblController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/")
    public String root() {
        return "redirect:/index";
    }

    @GetMapping(value="/index")
    public String sayHelloForm(Model model) {
        model.addAttribute("userProfile", new UserProfile());
        return "index";
    }
    
    @PostMapping(value="/index")
    public String sayHello(@ModelAttribute UserProfile userProfile, Model model) {
        model.addAttribute("userProfile", userProfile);
        if (userRepository.findByName(userProfile.getName()) != null) {
            Integer id = userRepository.findByName(userProfile.getName()).getId();
            userProfile.setId(id);
        }
        userRepository.save(userProfile);
        return "message";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<UserProfile> getAllUsers() {
        return userRepository.findAll();
    }
}
