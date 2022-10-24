/**********************************************************************************
 * Project: < Yumoid >
 * Assignment: < assignment 1 >
 * Author(s): < Robert Kaczur, Phuong Hoang, Truong Thi Bui>
 * Student Number: < 101014890, 101306676, 101300750>
 * Date: October 23rd 2022
 * Description: This java file is used to search recipes in our app.
 **********************************************************************************/
package ca.gbc.yumoid.recipe.repositories;

import ca.gbc.yumoid.recipe.model.Meal;
import ca.gbc.yumoid.recipe.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchRepository extends JpaRepository<Recipe, Long> {
    @Query("SELECT r FROM Recipe r WHERE LOWER(CONCAT(r.name, r.ingredients, r.steps)) LIKE LOWER(concat('%', concat(:keyword, '%')))")
    List<Recipe> search(String keyword);

    @Query("SELECT r FROM Recipe r JOIN r.likedByUsers u WHERE u.username LIKE %?1% ")
    List<Recipe> findRecipeByUsername(String userName);

    @Query("SELECT m FROM Meal m join m.user u WHERE u.username LIKE %?1%")
    List<Meal> findMealByUsername(String userName);

}


