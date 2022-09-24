package com.xatoxa.samobikes.controllers;

import com.xatoxa.samobikes.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {
    private UserServiceImpl userService;

    @Autowired
    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/users/check_username")
    public String checkUsername(@Param("id") Integer id, @Param("username") String username){
        boolean unique = userService.isUsernameUnique(id, username);
        if(unique) return "OK";
        else return "Duplicated";
    }
}
