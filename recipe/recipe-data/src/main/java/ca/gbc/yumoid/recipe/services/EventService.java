/**********************************************************************************
 * Project: < Yumoid >
 * Assignment: < assignment 2 >
 * Author(s): < Robert Kaczur, Phuong Hoang, Truong Thi Bui>
 * Student Number: < 101014890, 101306676, 101300750>
 * Date: December 4th 2022
 * Description: This java file is used to get/set the event entity in H2DB.
 **********************************************************************************/
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
