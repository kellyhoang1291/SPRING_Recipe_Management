package ca.gbc.yumoid.recipe.services;

import ca.gbc.yumoid.recipe.model.Event;
import ca.gbc.yumoid.recipe.repositories.EventRepository;
import ca.gbc.yumoid.recipe.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    final private UserRepository userRepository;
    final private EventRepository eventRepository;

    public EventService(UserRepository userRepository, EventRepository eventRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    public void save(Event event){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        event.setUserEvent(userRepository.getUserByUsername(authentication.getName()));
        eventRepository.save(event);
    }

    public List<Event> findAll(){ return (List<Event>) eventRepository.findAll(); }

    public Event findById(Long id){
        return eventRepository.getEventById(id);
    }

    public void deleteById(Long id) {
        eventRepository.deleteById(id);
    }
}
