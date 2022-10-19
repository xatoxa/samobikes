package com.xatoxa.samobikes.controllers;

import com.xatoxa.samobikes.DTO.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("")
    public String showHomePage(){
        return "redirect:/bikes";
    }

    @GetMapping("/login")
    public String showLoginPage(){

        return "login";
    }

    @GetMapping("/registration")
    public String showRegistrationPage(Model model){
        UserDTO userDTO = new UserDTO();
        model.addAttribute("user", userDTO);
        return "registration";
    }


}
