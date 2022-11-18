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
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SecurityController {
    final private UserService userService;
    public SecurityController(UserService userService) {
        this.userService = userService;
    }

    //register
    @RequestMapping({"/register"})
    public String index(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "/register";
    }

    @PostMapping(value = "/register")
    public String save(User user, Model model) {
        if (userService.getUserByUsername(user.getUsername()) != null) {
            model.addAttribute("message", "Invalid Username! " + user.getUsername() + " is already taken!");
            return "/register";
        }

        if (user.getRecoveryPIN().length() != 4 ) {
            model.addAttribute("message", "Please enter 4(four) characters for security PIN");
            return "/register";
        }

        userService.save(user);
        return "login";
    }

    //login
    @RequestMapping(value = {"", "/", "/login"}, method = RequestMethod.GET)
    public String showLoginPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }
        return "redirect:/registered/";
    }

    //logout
    @RequestMapping({"/logout"})
    public String logout() {
        return "/logout";
    }

    //reset password
    @RequestMapping({"/reset"})
    public String resetPassword(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "/reset";
    }
    @PostMapping(value = {"/reset"})
    public String resetPassword(HttpServletRequest request, Model model) {
        User user = userService.getUserByUsername(request.getParameter("username"));

        if (user == null) {
            model.addAttribute("message", "Invalid User! " + request.getParameter("username") + " Not Found!");
            return "/reset";
        } else {
            if (!request.getParameter("recoveryPIN").equals(user.getRecoveryPIN())){
                model.addAttribute("message", "Wrong PIN");
                return "/reset";
            }

            if (!request.getParameter("password").equals(request.getParameter("confirmPassword"))) {
                model.addAttribute("message", "Passwords do not match");
                return "/reset";
            }

            userService.updatePassword(user, request.getParameter("password"));
            model.addAttribute("successMessage", "You have successfully reset your password.");
        }

        return "/reset-success";
    }


}
