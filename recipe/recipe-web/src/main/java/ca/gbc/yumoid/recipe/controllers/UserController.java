/**********************************************************************************
 * Project: < Yumoid >
 * Assignment: < assignment 1 >
 * Author(s): < Robert Kaczur, Phuong Hoang, Truong Thi Bui>
 * Student Number: < 101014890, 101306676, 101300750>
 * Date: October 23rd 2022
 * Description: This java file is used to control user pages available to registered users.
 **********************************************************************************/
package ca.gbc.yumoid.recipe.controllers;

import ca.gbc.yumoid.recipe.repositories.SearchRepository;
import ca.gbc.yumoid.recipe.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
    //View Created & Favorite Recipe
    @RequestMapping({"/view-created-recipes"})
    public String viewUserRecipes(Model model, Authentication authentication) {
        model.addAttribute("userRecipes", searchRepository.findRecipeByLikedUsername(authentication.getName()));
        return "registered/user/view-created-recipes";
    }
}
