package ca.gbc.yumoid.recipe.repositories;

import ca.gbc.yumoid.recipe.model.Event;
import ca.gbc.yumoid.recipe.model.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Long> {
    Event getEventById(Long id);
}
