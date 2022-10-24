/**********************************************************************************
 * Project: < Yumoid >
 * Assignment: < assignment 1 >
 * Author(s): < Robert Kaczur, Phuong Hoang, Truong Thi Bui>
 * Student Number: < 101014890, 101306676, 101300750>
 * Date: October 23rd 2022
 * Description: This java file is used to set the recipe entity in our h2 database.
 **********************************************************************************/
package ca.gbc.yumoid.recipe.services;

import ca.gbc.yumoid.recipe.model.Meal;
import ca.gbc.yumoid.recipe.repositories.MealRepository;
import ca.gbc.yumoid.recipe.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class MealService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MealRepository mealRepository;

    public void save(Meal meal){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        meal.setUser(userRepository.getUserByUsername(authentication.getName()));
        mealRepository.save(meal);
    }

    public List<Meal> findAll(){
        return mealRepository.findAll();
    }
}
