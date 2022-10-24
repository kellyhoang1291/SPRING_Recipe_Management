/**********************************************************************************
 * Project: < Yumoid >
 * Assignment: < assignment 1 >
 * Author(s): < Robert Kaczur, Phuong Hoang, Truong Thi Bui>
 * Student Number: < 101014890, 101306676, 101300750>
 * Date: October 23rd 2022
 * Description: This java file implements the user details interface needed for spring
 * security authentication.
 **********************************************************************************/
package ca.gbc.yumoid.recipe.services;

import ca.gbc.yumoid.recipe.model.User;
import ca.gbc.yumoid.recipe.repositories.RoleRepository;
import ca.gbc.yumoid.recipe.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashSet;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

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

    public void saveNewPassword(User user){
        userRepository.save(user);
    }

    public void updateUser(User user){
        user.setRoles(new HashSet<>(roleRepository.findByName("user")));
        user.setEnabled(true);
        userRepository.save(user);
    }

}
