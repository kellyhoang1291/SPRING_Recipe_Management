package ca.gbc.yumoid.recipe.controllers;

import ca.gbc.yumoid.recipe.model.Event;
import ca.gbc.yumoid.recipe.services.EventService;
import ca.gbc.yumoid.recipe.services.SearchService;
import ca.gbc.yumoid.recipe.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/registered/event")
public class EventController {
    final private EventService eventService;
    final private SearchService searchService;
    final private UserService userService;

    public EventController(EventService eventService, SearchService searchService, UserService userService) {
        this.eventService = eventService;
        this.searchService = searchService;
        this.userService = userService;
    }

    @GetMapping("/list")
    public String listEvents(Model model) {

        // get current logged in user event from db
        List<Event> events = searchService.listMyEvents(userService.getCurrentUser());

        // add to the spring model
        model.addAttribute("events", events);

        return "/registered/event/list";
    }

    @GetMapping("/add")
    public String add(Model model) {

        // create model attribute to bind form data
        Event event = new Event();

        model.addAttribute("event", event);

        return "/registered/event/update";
    }

    @GetMapping("/update")
    public String update(@RequestParam("id") Long id,
                                    Model model) {

        // get the event from the service
        Event event = eventService.findById(id);

        // set event as a model attribute to pre-populate the form
        model.addAttribute("event", event);

        // send over to our form
        return "/registered/event/update";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("event") Event event) {

        // save the event
        eventService.save(event);

        // use a redirect to prevent duplicate submissions
        return "redirect:/registered/event/list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id) {

        // delete the employee
        eventService.deleteById(id);

        // redirect to /employees/list
        return "redirect:/employees/list";

    }
}