/**********************************************************************************
 * Project: < Yumoid >
 * Assignment: < assignment 1 >
 * Author(s): < Robert Kaczur, Phuong Hoang, Truong Thi Bui>
 * Student Number: < 101014890, 101306676, 101300750>
 * Date: October 23rd 2022
 * Description: This java file is used to get the recipes based on search keyword
 **********************************************************************************/
package ca.gbc.yumoid.recipe.services;

import ca.gbc.yumoid.recipe.model.*;
import ca.gbc.yumoid.recipe.repositories.SearchRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class SearchService {

    final private SearchRepository searchRepository;

    public SearchService(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    public List<Recipe> listAll(String keyword){
        if(keyword != null){
            return searchRepository.findRecipeByKeyword(keyword);
        }
        return searchRepository.findAll();
    }

    public List<Recipe> listMyRecipe(User user){
        return searchRepository.findRecipeByUsername(user);
    }

    public List<Event> listMyEvents(User user){
        return searchRepository.findEventByUsername(user);
    }

    public List<Meal> listMyMeals(User user){
        return searchRepository.findMealByUsername(user);
    }

    public Set<Ingredient> ingredientSet(Long recipeId) { return searchRepository.getIngredientsByRecipe(recipeId); }

    public List<ShoppingList> listMyShoppings(User user) { return searchRepository.findShoppingListByUsername(user); }

    public Set<Ingredient> listMyIngredients(String username) { return searchRepository.findIngredientsByUsername(username); }

}
