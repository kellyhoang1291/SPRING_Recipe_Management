/**********************************************************************************
 * Project: < Yumoid >
 * Assignment: < assignment 1 >
 * Author(s): < Robert Kaczur, Phuong Hoang, Truong Thi Bui>
 * Student Number: < 101014890, 101306676, 101300750>
 * Date: October 23rd 2022
 * Description: This java file is used to control recipe pages available to registered users.
 **********************************************************************************/

package ca.gbc.yumoid.recipe.controllers;

import ca.gbc.yumoid.recipe.model.Recipe;
import ca.gbc.yumoid.recipe.repositories.SearchRepository;
import ca.gbc.yumoid.recipe.services.RecipeService;
import ca.gbc.yumoid.recipe.services.SearchService;
import ca.gbc.yumoid.recipe.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    //Load recipe
    @RequestMapping({"/view"})
    public String viewRecipe(Model model, Authentication authentication) {
        List<Recipe> listRecipes = searchService.listAll("");
        model.addAttribute("user", userService.getUserByUsername(authentication.getName()));
        model.addAttribute("recipes", listRecipes);
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
        return "redirect:/registered/recipe/view";
    }

    //Like Recipe
    @PostMapping( "/update/{id}")
    public String updateRecipe(@PathVariable Long id) {
        recipeService.markedAsFavoriteByID(id);
        return "redirect:/registered/recipe/view";
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
