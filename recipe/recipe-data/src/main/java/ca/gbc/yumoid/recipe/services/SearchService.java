/**********************************************************************************
 * Project: < Yumoid >
 * Assignment: < assignment 1 >
 * Author(s): < Robert Kaczur, Phuong Hoang, Truong Thi Bui>
 * Student Number: < 101014890, 101306676, 101300750>
 * Date: October 23rd 2022
 * Description: This java file is used to get the recipes based on search keyword
 **********************************************************************************/
package ca.gbc.yumoid.recipe.services;

import ca.gbc.yumoid.recipe.model.Event;
import ca.gbc.yumoid.recipe.model.Meal;
import ca.gbc.yumoid.recipe.model.Recipe;
import ca.gbc.yumoid.recipe.model.User;
import ca.gbc.yumoid.recipe.repositories.SearchRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
