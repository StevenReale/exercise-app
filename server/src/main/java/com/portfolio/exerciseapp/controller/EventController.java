package com.portfolio.exerciseapp.controller;

import com.portfolio.exerciseapp.model.Event;
import com.portfolio.exerciseapp.service.EventService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
@RequestMapping(path = "/events")
public class EventController {

    private EventService eventService;

    public EventController(EventService eventService) {this.eventService = eventService;}

    @PreAuthorize("permitAll()")
    @RequestMapping(path = "/{eventId}", method = RequestMethod.GET)
    public Event getEvent(@PathVariable int eventId) {
        return eventService.getEventById(eventId);
    }


}
