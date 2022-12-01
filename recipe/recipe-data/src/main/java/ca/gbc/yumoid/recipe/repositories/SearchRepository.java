/**********************************************************************************
 * Project: < Yumoid >
 * Assignment: < assignment 1 >
 * Author(s): < Robert Kaczur, Phuong Hoang, Truong Thi Bui>
 * Student Number: < 101014890, 101306676, 101300750>
 * Date: October 23rd 2022
 * Description: This java file is used to search recipes and meals.
 **********************************************************************************/
package ca.gbc.yumoid.recipe.repositories;

import ca.gbc.yumoid.recipe.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface SearchRepository extends JpaRepository<Recipe, Long> {
    @Query("SELECT r FROM Recipe r WHERE LOWER(CONCAT(r.name, r.steps)) LIKE LOWER(concat('%', concat(:keyword, '%')))")
    List<Recipe> findRecipeByKeyword(String keyword);

    @Query("SELECT r FROM Recipe r WHERE r.userRecipe = ?1")
    List<Recipe> findRecipeByUsername(User user);

    @Query("SELECT r FROM Recipe r JOIN r.likedByUsers u WHERE u.username LIKE %?1%")
    List<Recipe> findRecipeByLikedUsername(String userName);

    @Query("SELECT m FROM Meal m WHERE m.userMeal = ?1")
    List<Meal> findMealByUsername(User user);

    @Query("SELECT e FROM Event e WHERE e.userEvent = ?1")
    List<Event> findEventByUsername(User user);

    @Query("SELECT i FROM Ingredient i JOIN i.ingredientRecipes r WHERE r.id = :id")
    Set<Ingredient> getIngredientsByRecipe(Long id);

}


