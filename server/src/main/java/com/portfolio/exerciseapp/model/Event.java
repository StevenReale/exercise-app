package com.portfolio.exerciseapp.model;

import java.time.LocalDate;
import java.util.List;

public class Event {
    private int eventId;
    private int userId;
    private List<Workout> workouts;
    private LocalDate date;

    public Event() {};

    public Event(int eventId, int userId, List<Workout> workouts, LocalDate date) {
        this.eventId = eventId;
        this.userId = userId;
        this.workouts = workouts;
        this.date = date;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Workout> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(List<Workout> workouts) {
        this.workouts = workouts;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
