/**********************************************************************************
 * Project: < Yumoid >
 * Assignment: < assignment 1 >
 * Author(s): < Robert Kaczur, Phuong Hoang, Truong Thi Bui>
 * Student Number: < 101014890, 101306676, 101300750>
 * Date: October 23rd 2022
 * Description: This java file is used to control user activities available to
 * registered users.
 **********************************************************************************/
package ca.gbc.yumoid.recipe.controllers;

import ca.gbc.yumoid.recipe.model.User;
import ca.gbc.yumoid.recipe.repositories.SearchRepository;
import ca.gbc.yumoid.recipe.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/registered/user")
@Controller
public class UserController {
    final private UserService userService;
    final private SearchRepository searchRepository;

    public UserController(UserService userService, SearchRepository searchRepository) {
        this.userService = userService;
        this.searchRepository = searchRepository;
    }

    //View Profile
    @RequestMapping({"/view-profile"})
    public String viewProfile(Model model, Authentication authentication) {
        model.addAttribute("user", userService.getUserByUsername(authentication.getName()));
        model.addAttribute("userRecipes", searchRepository.findRecipeByLikedUsername(authentication.getName()));
        return "registered/user/view-profile";
    }

    //Update Profile
    @RequestMapping({"/update-profile"})
    public String updateUser(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);
        return "registered/user/update-profile";
    }

    @PostMapping(value = "/update-profile")
    public String updateUser(User user, Model model) {
        userService.updateUserProfile(user);
        return "registered/user/view-profile";
    }

    //Change Password
    @RequestMapping({"/change-password"})
    public String changePassword() {
        return "registered/user/change-password";
    }

    @PostMapping(value = "/change-password")
    public String changePassword(HttpServletRequest request, Model model) {
        User user = userService.getCurrentUser();

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(request.getParameter("oldPassword"),user.getPassword())){
            model.addAttribute("message", "Incorrect old password ");
            return "registered/user/change-password";
        }

        if (!request.getParameter("password").equals(request.getParameter("confirmPassword"))){
            model.addAttribute("message", "Passwords do not match");
            return "registered/user/change-password";
        }

        userService.updatePassword(user, request.getParameter("password"));
        model.addAttribute("user", user);
        return "registered/user/view-profile";
    }

    //View Created & Favorite Recipe
    @RequestMapping({"/view-created-recipes"})
    public String viewUserRecipes(Model model, Authentication authentication) {
        model.addAttribute("userRecipes", searchRepository.findRecipeByLikedUsername(authentication.getName()));
        return "registered/user/view-created-recipes";
    }
}
