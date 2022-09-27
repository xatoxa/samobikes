package com.xatoxa.samobikes.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("")
    public String showHomePage(){
        return "bikes";
    }

    @GetMapping("/login")
    public String showLoginPage(){

        return "login";
    }
}
