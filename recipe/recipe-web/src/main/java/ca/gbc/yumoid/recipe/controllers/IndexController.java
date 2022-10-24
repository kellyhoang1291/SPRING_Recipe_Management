/**********************************************************************************
 * Project: < Yumoid >
 * Assignment: < assignment 1 >
 * Author(s): < Robert Kaczur, Phuong Hoang, Truong Thi Bui>
 * Student Number: < 101014890, 101306676, 101300750>
 * Date: October 23rd 2022
 * Description: This java file is used to control our pages for non-registered users.
 **********************************************************************************/
package ca.gbc.yumoid.recipe.controllers;

import ca.gbc.yumoid.recipe.model.User;
import ca.gbc.yumoid.recipe.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

    @Autowired
    private UserService userService;

    @RequestMapping({"/register", "register.html"})
    public String index(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "/register";
    }

    @PostMapping(value = "/save")
    public String save(User user, Model model) {
        if (userService.getUserByUsername(user.getUsername()) != null) {
            model.addAttribute("message", "Invalid User! " + user.getUsername() + " Username Already Taken!");
            return "/register";
        }
        userService.save(user);
        return "/login";
    }

    @RequestMapping(value = {"", "/", "/login", "/login.html"}, method = RequestMethod.GET)
    public String showLoginPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "/login";
        }
        return "redirect:/registered/";
    }

    @RequestMapping({"/logout", "logout.html"})
    public String logout() {
        return "/logout";
    }


}
