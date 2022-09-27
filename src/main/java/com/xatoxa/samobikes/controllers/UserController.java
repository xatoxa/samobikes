package com.xatoxa.samobikes.controllers;

import com.xatoxa.samobikes.entities.Role;
import com.xatoxa.samobikes.entities.User;
import com.xatoxa.samobikes.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
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
    public String showUsers(Model model){
        return showUsersByPage(model, 1, "username", "asc", null);
    }

    @GetMapping("/users/page/{pageNum}")
    public String showUsersByPage(Model model,
                                  @PathVariable(name = "pageNum") int pageNum,
                                  @Param("sortField") String sortField,
                                  @Param("sortDir") String sortDir,
                                  @Param("keyword") String keyword){
        Page<User> page = userService.getAllByPage(pageNum, sortField, sortDir, keyword);
        List<User> users = page.getContent();

        model.addAttribute("users", users);

        long startCount = (long) (pageNum - 1) * UserServiceImpl.USERS_PER_PAGE + 1;
        long endCount = startCount + UserServiceImpl.USERS_PER_PAGE - 1;
        if (endCount > page.getTotalElements()){
            endCount = page.getTotalElements();
        }
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);

        model.addAttribute("keyword", keyword);

        return "users";
    }

    @GetMapping("/users/add")
    public String showAddUserForm(Model model){
        User user = new User();
        user.setEnabled(true);
        List<Role> roles = userService.getAllRoles();
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);

        return "user-edit";
    }

    //возможно добавить exception на несуществующий id
    @GetMapping("/users/edit/{id}")
    public String editUser(Model model, @PathVariable(value = "id") Integer id){
        User user = userService.getById(id);
        List<Role> roles = userService.getAllRoles();
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);

        return "user-edit";
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
