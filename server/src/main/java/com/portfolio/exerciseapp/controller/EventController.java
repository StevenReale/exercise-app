package com.portfolio.exerciseapp.controller;

import com.portfolio.exerciseapp.dao.UserDao;
import com.portfolio.exerciseapp.exception.AuthenticationException;
import com.portfolio.exerciseapp.model.Event;
import com.portfolio.exerciseapp.model.User;
import com.portfolio.exerciseapp.model.Workout;
import com.portfolio.exerciseapp.service.EventService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
@RequestMapping(path = "/events")
public class EventController {

    private EventService eventService;
    private UserDao userDao;

    public EventController(EventService eventService, UserDao userDao) {
        this.eventService = eventService;
        this.userDao = userDao;
    }

    @RequestMapping(path = "/{eventId}", method = RequestMethod.GET)
    public Event getEvent(@PathVariable int eventId, Principal principal) throws AuthenticationException {
        User user = getUser(principal);
        Event event = eventService.getEventById(eventId);

        if (!user.getAuthorities().contains("admin") && user.getId() != event.getUserId()) {
            throw new AuthenticationException("Invalid Credentials.");
        } else {
            return eventService.getEventById(eventId);
        }

    }

    @PreAuthorize("hasRole('admin')")
    @RequestMapping(method = RequestMethod.GET)
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @RequestMapping(path = "/user/{userId}", method = RequestMethod.GET)
    public List<Event> getAllEventsByUser(@PathVariable int userId, Principal principal) throws AuthenticationException {

        User user = getUser(principal);
        if (!user.getAuthorities().contains("admin") && user.getId() != userId) {
            throw new AuthenticationException("Invalid Credentials.");
        } else {
            return eventService.getAllEventsByUserId(user.getId());
        }

    }

    @PreAuthorize("hasRole('admin')")
    @RequestMapping(path = "/exercise/{exerciseId}", method = RequestMethod.GET)
    public List<Event> getAllEventsByExercise(@PathVariable int exerciseId) {
        return eventService.getAllEventsByExerciseId(exerciseId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Event createEvent(@RequestBody Event event, Principal principal) throws AuthenticationException{
        User user = getUser(principal);
        if (!user.getAuthorities().contains("admin") && user.getId() != event.getUserId()) {
            throw new AuthenticationException("Invalid Credentials.");
        } else {
            return eventService.createEvent(event);
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    public boolean updateEvent(@RequestBody Event event, Principal principal) throws AuthenticationException {
        User user = getUser(principal);
        if (!user.getAuthorities().contains("admin") && user.getId() != event.getUserId()) {
            throw new AuthenticationException("Invalid Credentials.");
        } else {
            return eventService.updateEvent(event);
        }
    }

    @RequestMapping(path="/add-workout/{eventId}", method = RequestMethod.PUT)
    public boolean addWorkoutToEvent(@PathVariable int eventId, @RequestBody Workout workout, Principal principal) throws AuthenticationException {
        User user = getUser(principal);
        Event event = eventService.getEventById(eventId);

        if (!user.getAuthorities().contains("admin") && user.getId() != event.getUserId()) {
            throw new AuthenticationException("Invalid Credentials.");
        } else {
            return eventService.addWorkoutToEvent(eventId, workout.getWorkoutId());
        }
    }

    @RequestMapping(path="/{eventId}", method = RequestMethod.DELETE)
    public void deleteEvent(@PathVariable int eventId, Principal principal) throws AuthenticationException {
        User user = getUser(principal);
        if (!user.getAuthorities().contains("admin") && user.getId() != eventService.getEventById(eventId).getUserId()) {
            throw new AuthenticationException("Invalid Credentials.");
        } else {
            eventService.deleteEvent(eventId);
        }
    }

    private User getUser(Principal principal) {
        return userDao.getByUsername(principal.getName());
    }



}
