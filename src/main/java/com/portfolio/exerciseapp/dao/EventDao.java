package com.portfolio.exerciseapp.dao;

import com.portfolio.exerciseapp.model.Event;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface EventDao {

    public Event getEventById(int id);

    public List<Event> getAllEvents();

    public List<Event> getAllEventsByUser(int userId);

    public List<Event> getAllEventsByDate(LocalDate localDate);

    public List<Event> getAllEventsByWorkout(int workoutId);

    public Event createEvent(Event event);

    public boolean updateEvent(Event event);

    public void deleteEvent(int id);
}
