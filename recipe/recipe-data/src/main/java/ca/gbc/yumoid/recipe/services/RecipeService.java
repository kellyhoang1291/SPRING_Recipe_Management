/**********************************************************************************
 * Project: < Yumoid >
 * Assignment: < assignment 1 >
 * Author(s): < Robert Kaczur, Phuong Hoang, Truong Thi Bui>
 * Student Number: < 101014890, 101306676, 101300750>
 * Date: October 23rd 2022
 * Description: This java file is used to set the recipe entity in our h2 database.
 **********************************************************************************/
package ca.gbc.yumoid.recipe.services;

import ca.gbc.yumoid.recipe.model.Recipe;
import ca.gbc.yumoid.recipe.repositories.RecipeRepository;
import ca.gbc.yumoid.recipe.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserRepository userRepository;

    public Recipe getRecipeById(Long id){
        return recipeRepository.getRecipeById(id);
    }

    public void save(Recipe recipe){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        recipe.setCreatedUser(userRepository.getUserByUsername(authentication.getName()));
        recipe.getLikedByUsers().add(userRepository.getUserByUsername(authentication.getName()));
        recipe.setDateAdded(LocalDate.now());
        recipe.setTotalTime(recipe.getPrepTime() + recipe.getCookTime());
        recipeRepository.save(recipe);
    }

    public void markedAsFavoriteByID(Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "Recipe with id " + id + " does not exists"
                ));
        recipe.getLikedByUsers().add(userRepository.getUserByUsername(authentication.getName()));
        recipeRepository.save(recipe);
    }

}
