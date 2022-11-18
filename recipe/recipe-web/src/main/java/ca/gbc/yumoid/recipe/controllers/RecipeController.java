/**********************************************************************************
 * Project: < Yumoid >
 * Assignment: < assignment 1 >
 * Author(s): < Robert Kaczur, Phuong Hoang, Truong Thi Bui>
 * Student Number: < 101014890, 101306676, 101300750>
 * Date: October 23rd 2022
 * Description: This java file is used to control recipe activities available to
 * registered users.
 **********************************************************************************/

package ca.gbc.yumoid.recipe.controllers;

import ca.gbc.yumoid.recipe.model.Recipe;
import ca.gbc.yumoid.recipe.model.User;
import ca.gbc.yumoid.recipe.repositories.SearchRepository;
import ca.gbc.yumoid.recipe.services.RecipeService;
import ca.gbc.yumoid.recipe.services.SearchService;
import ca.gbc.yumoid.recipe.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@RequestMapping("/registered/recipe")
@Controller
public class RecipeController {
    final private RecipeService recipeService;
    final private SearchService searchService;
    final private SearchRepository searchRepository;

    final private UserService userService;

    public RecipeController(UserService userService, RecipeService recipeService, SearchService searchService, SearchRepository searchRepository) {
        this.recipeService = recipeService;
        this.searchService = searchService;
        this.searchRepository = searchRepository;
        this.userService = userService;
    }


    //RECIPE ===================================================================================================
    //Load recipes
    @RequestMapping({"/list"})
    public String viewRecipe(Model model, Authentication authentication) {
        List<Recipe> listRecipes = searchService.listAll("");
        model.addAttribute("user", userService.getUserByUsername(authentication.getName()));
        model.addAttribute("recipes", listRecipes);
        return "registered/recipe/list";
    }

    // filter list recipe
    @PostMapping({"/list"})
    public String viewRecipe(Model model, Authentication authentication, HttpServletRequest request) {
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);

        List<Recipe> listRecipes = null;
        String message = "";

        String type = request.getParameter("type");

        if (Objects.equals(type, "all")) {
            listRecipes = searchService.listAll("");
            if (listRecipes.size() == 0)
                message = "Please start creating some recipes...";
        }
        else if (Objects.equals(type, "myRecipe")){
            listRecipes = searchService.listMyRecipe(user);
            if (listRecipes.size() == 0)
                message = "Please start creating some recipes...";
        }

        else if (Objects.equals(type, "favoriteRecipe")){
            listRecipes = searchRepository.findRecipeByLikedUsername(authentication.getName());
            if (listRecipes.size() == 0)
                message = "You have no favorite recipes";
        }

        model.addAttribute("count", listRecipes.size());

        //check if any recipes found
        if (listRecipes.size() > 0) {
            model.addAttribute("recipes", listRecipes);
        } else {
            model.addAttribute("message", message);
        }

        model.addAttribute("type", type);
        return "registered/recipe/list";
    }

    //View recipe


    @GetMapping({"/view"})
    public String view(@RequestParam("id") Long id, Model model) {
        Recipe recipe = recipeService.getRecipeById(id);
        model.addAttribute("recipe", recipe);
        return "registered/recipe/view";
    }

    //Create recipe
    @RequestMapping({"/create"})
    public String create(Model model) {
        Recipe recipe = new Recipe();
        model.addAttribute("recipe", recipe);
        return "registered/recipe/create";
    }

    @PostMapping(value = "/save")
    public String saveRecipe(Model model, Recipe recipe, Authentication authentication) {
        recipeService.save(recipe);
        model.addAttribute("userRecipes", searchRepository.findRecipeByLikedUsername(authentication.getName()));
        return "redirect:/registered/recipe/list";
    }

    //Like Recipe
    @PostMapping( "/update/{id}")
    public String updateRecipe(@PathVariable Long id) {
        recipeService.markedAsFavoriteByID(id);
        return "redirect:/registered/recipe/list";
    }

    //Search Recipe
    @RequestMapping(value = {"/search"}, method = RequestMethod.GET)
    public String search(Model model) {
        model.addAttribute("recipe", new Recipe());
        return "registered/recipe/search";
    }

    @RequestMapping(value = {"/search"}, method = RequestMethod.POST)
    public String search(HttpServletRequest request, Model model) {
        String keyword = request.getParameter("keyword");
        model.addAttribute("keyword", "Keyword: " + keyword);
        List<Recipe> matchedRecipes = searchService.listAll(keyword);
        model.addAttribute("count", matchedRecipes.size());

        //check if any recipes found
        if (matchedRecipes.size() > 0) {
            model.addAttribute("recipes", matchedRecipes);
        } else {
            model.addAttribute("message", "No recipe found. Please try different keyword.");
        }
        return "registered/recipe/search";
    }
}
