/**********************************************************************************
 * Project: < Yumoid >
 * Assignment: < assignment 1 >
 * Author(s): < Robert Kaczur, Phuong Hoang, Truong Thi Bui>
 * Student Number: < 101014890, 101306676, 101300750>
 * Date: October 23rd 2022
 * Description: This java file is used to control home activities available to
 * registered users.
 **********************************************************************************/
package ca.gbc.yumoid.recipe.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/registered")
@Controller
public class HomeController {
    @RequestMapping({"", "/", "/index"})
    public String index() {
        return "registered/index";
    }

}