/**********************************************************************************
 * Project: < Yumoid >
 * Assignment: < assignment 1 >
 * Author(s): < Robert Kaczur, Phuong Hoang, Truong Thi Bui>
 * Student Number: < 101014890, 101306676, 101300750>
 * Date: October 23rd 2022
 * Description: This java file is used to get/set the user entity in H2DB
 **********************************************************************************/
package ca.gbc.yumoid.recipe.services;

import ca.gbc.yumoid.recipe.model.User;
import ca.gbc.yumoid.recipe.repositories.RoleRepository;
import ca.gbc.yumoid.recipe.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;

@Service
public class UserService {
    final private UserRepository userRepository;
    final private RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User getUserByUsername(String username){
        return userRepository.getUserByUsername(username);
    }

    public User getUserById(Long id){
        return userRepository.getUserById(id);
    }

    public void save(User user){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRoles(new HashSet<>(roleRepository.findByName("user")));
        user.setEnabled(true);
        userRepository.save(user);
    }

    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.getUserByUsername(authentication.getName());
    }

    public void updateUserProfile(User user){
        User u = getCurrentUser();
        u.setFirstName(user.getFirstName());
        u.setLastName(user.getLastName());
        u.setAddress(user.getAddress());
        u.setPostalCode(user.getPostalCode());
        userRepository.save(u);
    }

    public void updatePassword(User user, String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }
}
