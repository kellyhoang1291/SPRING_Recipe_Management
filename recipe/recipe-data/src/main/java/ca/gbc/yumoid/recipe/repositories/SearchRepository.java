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

import java.util.List;

public interface SearchRepository extends JpaRepository<Recipe, Long> {
    @Query("SELECT r FROM Recipe r WHERE LOWER(CONCAT(r.name, r.ingredients)) LIKE LOWER(concat('%', concat(:keyword, '%')))")
    List<Recipe> search(String keyword);

    @Query("SELECT r FROM Recipe r WHERE r.author.username LIKE %?1%")
    List<Recipe> findByUsername(String userName);

    @Query("SELECT m FROM Meal m WHERE m.user.username LIKE %?1%")
    List<Meal> findByUser(String userName);

}


