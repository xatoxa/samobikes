package com.xatoxa.samobikes.controllers;

import com.xatoxa.samobikes.entities.Role;
import com.xatoxa.samobikes.entities.User;
import com.xatoxa.samobikes.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {
    private UserServiceImpl userService;

    @Autowired
    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String showUsers(Model model, RedirectAttributes redirectAttributes){
        model.addAttribute("users", userService.getAllUsers());
        redirectAttributes.addFlashAttribute("message", "Успешно выполнено.");

        return "users";
    }

    @GetMapping("/users/add")
    public String showAddUserForm(Model model){
        User user = new User();
        user.setEnabled(true);
        List<Role> roles = userService.getAllRoles();
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);

        return "user-add";
    }

    @PostMapping("/users/save")
    public String saveUser(@ModelAttribute(value = "user") User user,
                           RedirectAttributes redirectAttributes){
        userService.save(user);
        redirectAttributes.addFlashAttribute("message", "Успешно выполнено.");
        return "redirect:/users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable(value = "id") Integer id){
        userService.deleteById(id);
        return "redirect:/users";
    }
}
