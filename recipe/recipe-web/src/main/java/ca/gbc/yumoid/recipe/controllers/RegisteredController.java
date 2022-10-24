/**********************************************************************************
 * Project: < Yumoid >
 * Assignment: < assignment 1 >
 * Author(s): < Robert Kaczur, Phuong Hoang, Truong Thi Bui>
 * Student Number: < 101014890, 101306676, 101300750>
 * Date: October 23rd 2022
 * Description: This java file is used to control all pages available to registered users.
 **********************************************************************************/
package ca.gbc.yumoid.recipe.controllers;

import ca.gbc.yumoid.recipe.model.Meal;
import ca.gbc.yumoid.recipe.model.Recipe;
import ca.gbc.yumoid.recipe.model.User;
import ca.gbc.yumoid.recipe.repositories.*;
import ca.gbc.yumoid.recipe.repositories.SearchRepository;
import ca.gbc.yumoid.recipe.services.*;
import ca.gbc.yumoid.recipe.services.*;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

@RequestMapping("/registered")
@Controller
public class RegisteredController {

    @Autowired
    private UserService userService;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private SearchService searchService;

    @Autowired
    private MealService mealService;

    @Autowired
    SearchRepository searchRepository;

    @RequestMapping({"", "/", "index", "index.html"})
    public String index() {
        return "registered/index";
    }

    @RequestMapping({"/create", "/create-recipe", "create-recipe.html"})
    public String create(Model model) {
        Recipe recipe = new Recipe();
        model.addAttribute("recipe", recipe);
        return "registered/create-recipe";
    }

    @PostMapping(value = "/saveRecipe")
    public String saveRecipe(Model model, Recipe recipe, Authentication authentication) {
        recipeService.save(recipe);
        model.addAttribute("userRecipes", searchRepository.findByUsername(authentication.getName()));
        return "/registered/view-recipe";
    }

    @RequestMapping({"/plan", "/plan-meal", "plan-meal.html"})
    public String plan(Model model, Authentication authentication) {
        model.addAttribute("userMeals", searchRepository.findByUser(authentication.getName()));
        return "registered/plan-meal";
    }

    @RequestMapping({"/create-meal", "create-meal.html"})
    public String createMeal(Model model) {
        Meal meal = new Meal();
        model.addAttribute("meal", meal);
        List<Recipe> listRecipes = searchService.listAll("");
        model.addAttribute("recipes", listRecipes);
        return "registered/create-meal";
    }

    @PostMapping(value = "/saveMeal")
    public String saveMeal(Meal meal, Authentication authentication, Model model) {
        mealService.save(meal);
        model.addAttribute("userMeals", searchRepository.findByUser(authentication.getName()));
        return "/registered/plan-meal";
    }

    @RequestMapping(value = {"search", "/search-recipe", "/search-recipe.html"}, method = RequestMethod.GET)
    public String search(Model model) {
        model.addAttribute("recipe", new Recipe());
        return "registered/search-recipe";
    }

    @RequestMapping(value = {"search", "/search-recipe", "/search-recipe.html"}, method = RequestMethod.POST)
    public String search(HttpServletRequest request, Model model) {
        String searchName = request.getParameter("name");
        model.addAttribute("searchString", "You searched for " + searchName);
        List<Recipe> resp = searchService.listAll(searchName);
        model.addAttribute("nameCount", -1);
        model.addAttribute("count", resp.size());
        if (resp.size() > 0) {
            model.addAttribute("recipes", resp);
        } else {
            model.addAttribute("message", "No record Found");
        }
        return "/registered/search-recipe";
    }


    @RequestMapping({"/view-profile", "view-profile.html"})
    public String viewProfile(Model model, Authentication authentication) {
        model.addAttribute("user", userService.getUserByUsername(authentication.getName()));
        model.addAttribute("userRecipes", searchRepository.findByUsername(authentication.getName()));
        return "registered/view-profile";
    }



    @RequestMapping(value = "/saveUser")
    public String saveUser(User user, Model model, Authentication authentication) {
        if (userService.getUserByUsername(user.getUsername()) != null) {
            if (!Objects.equals(authentication.getName(), user.getUsername())){
                model.addAttribute("message", "Invalid User! " + user.getUsername() + " Username Already Taken!");
                return "/registered/view-recipe";
            }
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setPassword(((UserDetails)principal).getPassword());
        userService.updateUser(user);
        model.addAttribute("message", "Profile updated, please login again to see all changes.");
        return "registered/view-profile";
    }

    @RequestMapping({"/view-recipe", "view-recipe.html"})
    public String viewRecipe(Model model) {
        List<Recipe> listRecipes = searchService.listAll("");
        model.addAttribute("recipes", listRecipes);
        return "registered/view-recipe";
    }

    @PostMapping(value = "/updateRecipe")
    public String updateRecipe(Recipe recipe, Model model) {
        recipeService.save(recipe);
        List<Recipe> listRecipes = searchService.listAll("");
        model.addAttribute("recipes", listRecipes);
        return "/registered/view-recipes";
    }

}