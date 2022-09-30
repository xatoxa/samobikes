package com.xatoxa.samobikes.controllers;

import com.xatoxa.samobikes.entities.SamUserDetails;
import com.xatoxa.samobikes.entities.User;
import com.xatoxa.samobikes.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AccountController {
    private UserServiceImpl userService;

    @Autowired
    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/account")
    public String showAccount(Model model, @AuthenticationPrincipal SamUserDetails loggedUser){
        String username = loggedUser.getUsername();
        User user = userService.findByUserName(username);
        model.addAttribute("user", user);

        return "account";
    }

    @PostMapping("/account/save")
    public String saveAccount(Model model, @ModelAttribute(value = "user") User user,
                           @AuthenticationPrincipal SamUserDetails loggedUser,
                           RedirectAttributes redirectAttributes){
        userService.updateAccount(user);
        loggedUser.setFirstName(user.getFirstName());
        loggedUser.setLastName(user.getLastName());
        redirectAttributes.addFlashAttribute(
                "message",
                "Изменения сохранены.");
        return "redirect:/bikes";
    }
}
