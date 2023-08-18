package com.portfolio.exerciseapp.model;

import java.time.LocalDate;

public class Event {
    private int eventId;
    private int userId;
    private Workout workout;
    private LocalDate date;

    public Event() {};

    public Event(int eventId, int userId, Workout workout, LocalDate date) {
        this.eventId = eventId;
        this.userId = userId;
        this.workout = workout;
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

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
