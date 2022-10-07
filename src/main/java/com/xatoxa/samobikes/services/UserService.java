package com.xatoxa.samobikes.services;

import com.xatoxa.samobikes.entities.User;
import com.xatoxa.samobikes.entities.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findByUserName(String username);

    void registerNewUserAccount(UserDTO userDTO);
}
