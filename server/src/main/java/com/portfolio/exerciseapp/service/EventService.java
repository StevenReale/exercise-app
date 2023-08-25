package com.portfolio.exerciseapp.service;

import com.portfolio.exerciseapp.dao.EventDao;
import com.portfolio.exerciseapp.model.Event;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventService {

    private EventDao eventDao;

    public EventService (EventDao eventDao) {
        this.eventDao = eventDao;
    }

    /**
     * Gets event by ID.
     */
    public Event getEventById(int id) {return eventDao.getEventById(id); }

    public List<Event> getAllEvents() {
        return eventDao.getAllEvents();
    }

    public List<Event> getAllEventsByUserId(int id) {
        return eventDao.getAllEventsByUser(id);
    }

    public List<Event> getAllEventsByExerciseId(int exerciseId) {
        return eventDao.getAllEventsByExercise(exerciseId);
    }

    public Event createEvent(Event event) {return eventDao.createEvent(event); }

    public boolean updateEvent (Event event) {return eventDao.updateEvent(event); }

    public boolean addWorkoutToEvent(int eventId, int workoutId) {
        return eventDao.addWorkoutToEvent(eventId, workoutId);
    }

    public void deleteEvent(int eventId) {
        eventDao.deleteEvent(eventId);
    }
}
