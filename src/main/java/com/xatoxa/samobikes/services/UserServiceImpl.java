package com.xatoxa.samobikes.services;

import com.xatoxa.samobikes.entities.Role;
import com.xatoxa.samobikes.entities.User;
import com.xatoxa.samobikes.entities.UserDTO;
import com.xatoxa.samobikes.repositories.RoleRepository;
import com.xatoxa.samobikes.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    public static final int USERS_PER_PAGE = 6;
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserRepository (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository (RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User findByUserName(String username) {
        return userRepository.findOneByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findOneByUsername(username);
        if(user == null)
            throw new UsernameNotFoundException("Invalid username or password");
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    public User getById(Integer id){
        return userRepository.findById(id).get();
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    /*public List<User> getAllUsers(){
        return userRepository.findAll();
    }*/

    public Page<User> getAllByPage(int pageNum, String sortField, String sortDir, String keyword){
        Sort sort = Sort.by(sortField);
        if (sortDir.equals("asc")){
            sort = sort.ascending();
        } else{
            sort = sort.descending();
        }

        Pageable pageable = PageRequest.of(pageNum - 1, USERS_PER_PAGE, sort);
        if (keyword != null){
            return userRepository.findAll(keyword, pageable);
        }
        return userRepository.findAll(pageable);
    }

    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }

    public void deleteById(Integer id){
        userRepository.deleteById(id);
    }

    public boolean isUsernameUnique(Integer id, String username){
        User user = userRepository.findOneByUsername(username);

        if (user == null) return true;

        //если создаётся
        if(id == null){
            if (user != null) {
                return false;
            }
        }else {
            if (user.getId() != id){
                return false;
            }
        }

        return true;
    }

    public void save(User user){
        if (user.getId() != null){
            User existingUser = userRepository.findById(user.getId()).get();
            if (user.getPassword().isEmpty()){
                user.setPassword(existingUser.getPassword());
            }else {
                encodePassword(user);
            }
        }else{
            encodePassword(user);
        }
        userRepository.save(user);
    }

    public User updateAccount(User oldUser){
        User newUser = userRepository.findOneByUsername(oldUser.getUsername());

        if (!oldUser.getPassword().isEmpty()) {
            newUser.setPassword(oldUser.getPassword());
            encodePassword(newUser);
        }

        newUser.setFirstName(oldUser.getFirstName());
        newUser.setLastName(oldUser.getLastName());

        return userRepository.save(newUser);
    }

    private void encodePassword(User user){
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    @Override
    public void registerNewUserAccount(UserDTO userDTO){
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEnabled(true);

        Collection<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findOneByName("ROLE_USER"));
        user.setRoles(roles);

        user.setPassword(userDTO.getPassword());
        encodePassword(user);

        userRepository.save(user);
    }
}
