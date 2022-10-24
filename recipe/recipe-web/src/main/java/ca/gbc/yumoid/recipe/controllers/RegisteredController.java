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
import ca.gbc.yumoid.recipe.repositories.SearchRepository;
import ca.gbc.yumoid.recipe.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/registered")
@Controller
public class RegisteredController {

    final private UserService userService;
    final private RecipeService recipeService;
    final private SearchService searchService;
    final private MealService mealService;
    final private SearchRepository searchRepository;

    public RegisteredController(UserService userService, RecipeService recipeService, SearchService searchService, MealService mealService, SearchRepository searchRepository) {
        this.userService = userService;
        this.recipeService = recipeService;
        this.searchService = searchService;
        this.mealService = mealService;
        this.searchRepository = searchRepository;
    }

    @RequestMapping({"", "/", "index", "index.html"})
    public String index() {
        return "registered/index";
    }

    //RECIPE ===================================================================================================
    //Load recipe
    @RequestMapping({"/view-recipe", "view-recipe.html"})
    public String viewRecipe(Model model) {
        List<Recipe> listRecipes = searchService.listAll("");
        model.addAttribute("recipes", listRecipes);
        return "registered/recipe/view";
    }

    //Create recipe
    @RequestMapping({"/create", "/create-recipe", "create-recipe.html"})
    public String create(Model model) {
        Recipe recipe = new Recipe();
        model.addAttribute("recipe", recipe);
        return "registered/recipe/create";
    }

    @PostMapping(value = "/saveRecipe")
    public String saveRecipe(Model model, Recipe recipe, Authentication authentication) {
        recipeService.save(recipe);
        model.addAttribute("userRecipes", searchRepository.findRecipeByUsername(authentication.getName()));
        return "redirect:/registered/view-recipe";
    }

    //Like Recipe
    @PostMapping( "/updateRecipe/{id}")
    public String updateRecipe(@PathVariable Long id) {
        recipeService.markedAsFavoriteByID(id);
        return "redirect:/registered/view-recipe";
    }

    //Search Recipe
    @RequestMapping(value = {"search", "/search-recipe", "/search-recipe.html"}, method = RequestMethod.GET)
    public String search(Model model) {
        model.addAttribute("recipe", new Recipe());
        return "registered/recipe/search";
    }

    @RequestMapping(value = {"search", "/search-recipe", "/search-recipe.html"}, method = RequestMethod.POST)
    public String search(HttpServletRequest request, Model model) {
        String keyword = request.getParameter("name");
        model.addAttribute("searchString", "Keyword: " + keyword);

        List<Recipe> matchedRecipes = searchService.listAll(keyword);
        model.addAttribute("nameCount", -1);
        model.addAttribute("count", matchedRecipes.size());

        //check if any recipes found
        if (matchedRecipes.size() > 0) {
            model.addAttribute("recipes", matchedRecipes);
        } else {
            model.addAttribute("message", "No recipe found. Please try different keyword.");
        }
        return "registered/recipe/search";
    }

    //MEAL
    //Load meals
    @RequestMapping({"/plan", "/plan-meal", "plan-meal.html"})
    public String plan(Model model, Authentication authentication) {
        model.addAttribute("userMeals", searchRepository.findMealByUsername(authentication.getName()));
        return "registered/meal/view";
    }
    //Create meals
    @RequestMapping({"/create-meal", "create-meal.html"})
    public String createMeal(Model model) {
        Meal meal = new Meal();
        model.addAttribute("meal", meal);
        List<Recipe> listRecipes = searchService.listAll("");
        model.addAttribute("recipes", listRecipes);
        return "registered/meal/create";
    }

    @PostMapping(value = "/saveMeal")
    public String saveMeal(Meal meal, Authentication authentication, Model model) {
        mealService.save(meal);
        model.addAttribute("userMeals", searchRepository.findMealByUsername(authentication.getName()));
        return "registered/meal/view";
    }


    //USER
    //View Profile
    @RequestMapping({"/view-profile", "view-profile.html"})
    public String viewProfile(Model model, Authentication authentication) {
        model.addAttribute("user", userService.getUserByUsername(authentication.getName()));
        model.addAttribute("userRecipes", searchRepository.findRecipeByUsername(authentication.getName()));
        return "registered/user/view-profile";
    }
    //View Created & Favorite Recipe
    @RequestMapping({"/view-created-recipes", "view-created-recipes.html"})
    public String viewUserRecipes(Model model, Authentication authentication) {
        model.addAttribute("userRecipes", searchRepository.findRecipeByUsername(authentication.getName()));
        return "registered/user/view-created-recipes";
    }


}