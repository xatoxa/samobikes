package com.xatoxa.samobikes.controllers;

import com.xatoxa.samobikes.entities.History;
import com.xatoxa.samobikes.entities.Role;
import com.xatoxa.samobikes.entities.SamUserDetails;
import com.xatoxa.samobikes.entities.User;
import com.xatoxa.samobikes.DTO.UserDTO;
import com.xatoxa.samobikes.services.HistoryService;
import com.xatoxa.samobikes.services.RecaptchaService;
import com.xatoxa.samobikes.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    private UserServiceImpl userService;

    private RecaptchaService recaptchaService;

    private HistoryService historyService;

    @Autowired
    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRecaptchaService(RecaptchaService recaptchaService) {
        this.recaptchaService = recaptchaService;
    }

    @Autowired
    public void setHistoryService(HistoryService historyService) {
        this.historyService = historyService;
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
                           @AuthenticationPrincipal SamUserDetails loggedUser,
                           RedirectAttributes redirectAttributes){
        userService.save(user);

        History history = new History(
                userService.findByUserName(loggedUser.getUsername()).getId(),
                -1,
                "Пользователь " + user.getUsername() + " добавлен/изменён",
                LocalDateTime.now());
        historyService.save(history);

        redirectAttributes.addFlashAttribute(
                "message",
                "Пользователь " + user.getUsername() + " сохранён.");
        return "redirect:/users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable(value = "id") Integer id,
                             @AuthenticationPrincipal SamUserDetails loggedUser,
                             RedirectAttributes redirectAttributes){
        String username = userService.getById(id).getUsername();
        userService.deleteById(id);

        History history = new History(
                userService.findByUserName(loggedUser.getUsername()).getId(),
                -1,
                "Пользователь " + username + " удалён",
                LocalDateTime.now());
        historyService.save(history);

        redirectAttributes.addFlashAttribute(
                "message",
                "Пользователь " + username + " удалён.");
        return "redirect:/users";
    }

    @PostMapping("/register-user")
    public String registerUserAccount(Model model,
                                      @ModelAttribute(value = "user")UserDTO userDTO,
                                      @RequestParam(name="g-recaptcha-response") String recaptchaResponse,
                                      HttpServletRequest request,
                                      RedirectAttributes redirectAttributes){
        String ip = request.getRemoteAddr();
        String captchaVerifyMessage =
                recaptchaService.verifyRecaptcha(ip, recaptchaResponse);

        if (!captchaVerifyMessage.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            model.addAttribute("message", "А Вы точно не робот? " + captchaVerifyMessage);
            model.addAttribute("user", userDTO);
            return "registration";
        }

        userService.registerNewUserAccount(userDTO);

        History history = new History(
                userService.findByUserName(userDTO.getUsername()).getId(),
                -1,
                "Пользователь " + userDTO.getUsername() + " зарегистрировался",
                LocalDateTime.now());
        historyService.save(history);

        redirectAttributes.addFlashAttribute("message", "Ваш аккаунт зарегистрирован, можете войти в систему.");
        return "redirect:/login";
    }

    @GetMapping("/users/{id}/enabled/{status}")
    public String setEnabledById (Model model,
                                  @AuthenticationPrincipal SamUserDetails loggedUser,
                                  @PathVariable(value = "id") Integer id,
                                  @PathVariable(value = "status") boolean enabled,
                                  @Param("currentPage") String currentPage,
                                  @Param("sortField") String sortField,
                                  @Param("sortDir") String sortDir,
                                  @Param("keyword") String keyword,
                                  RedirectAttributes redirectAttributes){
        userService.setEnabledById(id, enabled);

        History history = new History(
                userService.findByUserName(loggedUser.getUsername()).getId(),
                -1,
                "Пользователь " + userService.getById(id).getUsername() + (enabled ? " активирован" : " деактивирован"),
                LocalDateTime.now());
        historyService.save(history);

        redirectAttributes.addFlashAttribute("message",
                "Пользователь " +
                        userService.getById(id).getUsername() + " теперь" +
                        (enabled ? " активен" : " не активен"));

        return "redirect:/users/page/" + currentPage + "?sortField=" + sortField + "&sortDir=" + sortDir + (keyword != null ? "&keyword=" + keyword : "");
    }
}
