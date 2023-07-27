package com.portfolio.exerciseapp.model;

import java.time.LocalDate;

public class Event {
    private int eventId;
    private int userId;
    private int workoutId;
    private LocalDate date;

    public Event() {};

    public Event(int eventId, int userId, int workoutId, LocalDate date) {
        this.eventId = eventId;
        this.userId = userId;
        this.workoutId = workoutId;
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

    public int getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(int workoutId) {
        this.workoutId = workoutId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
