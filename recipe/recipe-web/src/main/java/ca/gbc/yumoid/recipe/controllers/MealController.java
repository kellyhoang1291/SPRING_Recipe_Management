/**********************************************************************************
 * Project: < Yumoid >
 * Assignment: < assignment 1 >
 * Author(s): < Robert Kaczur, Phuong Hoang, Truong Thi Bui>
 * Student Number: < 101014890, 101306676, 101300750>
 * Date: October 23rd 2022
 * Description: This java file is used to control meal activities available to
 * registered users.
 **********************************************************************************/

package ca.gbc.yumoid.recipe.controllers;

import ca.gbc.yumoid.recipe.model.Meal;
import ca.gbc.yumoid.recipe.model.Recipe;
import ca.gbc.yumoid.recipe.repositories.SearchRepository;
import ca.gbc.yumoid.recipe.services.MealService;
import ca.gbc.yumoid.recipe.services.SearchService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/registered/meal")
@Controller
public class MealController {
    final private SearchService searchService;
    final private MealService mealService;
    final private SearchRepository searchRepository;

    public MealController(SearchService searchService, MealService mealService, SearchRepository searchRepository) {
        this.searchService = searchService;
        this.mealService = mealService;
        this.searchRepository = searchRepository;
    }

    //Load meals
    @RequestMapping({"/view"})
    public String plan(Model model, Authentication authentication) {
        model.addAttribute("userMeals", searchRepository.findMealByUsername(authentication.getName()));
        return "registered/meal/view";
    }
    //Create meals
    @RequestMapping({"/create"})
    public String createMeal(Model model) {
        Meal meal = new Meal();
        model.addAttribute("meal", meal);
        List<Recipe> listRecipes = searchService.listAll("");
        model.addAttribute("recipes", listRecipes);
        return "registered/meal/create";
    }

    @PostMapping(value = "/save")
    public String saveMeal(Meal meal, Authentication authentication, Model model) {
        mealService.save(meal);
        model.addAttribute("userMeals", searchRepository.findMealByUsername(authentication.getName()));
        return "registered/meal/view";
    }
}
