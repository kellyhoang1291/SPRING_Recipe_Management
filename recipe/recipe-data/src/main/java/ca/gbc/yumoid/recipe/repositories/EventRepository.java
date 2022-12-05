/**********************************************************************************
 * Project: < Yumoid >
 * Assignment: < assignment 2 >
 * Author(s): < Robert Kaczur, Phuong Hoang, Truong Thi Bui>
 * Student Number: < 101014890, 101306676, 101300750>
 * Date: December 4th 2022
 * Description: This java file is used to save event in our application.
 **********************************************************************************/
package ca.gbc.yumoid.recipe.repositories;

import ca.gbc.yumoid.recipe.model.Event;
import ca.gbc.yumoid.recipe.model.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Long> {
    Event getEventById(Long id);
}
