package com.portfolio.exerciseapp.dao;

import com.portfolio.exerciseapp.model.Event;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JdbcEventDao implements EventDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcEventDao (JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }

    @Override
    public Event getEventById(int id) {

        String sql = "SELECT event_id, user_id, workout_id, workout_date " +
                "FROM workout_event " +
                "WHERE event_id = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
        if(result.next()) {
            return mapRowToEvent(result);
        }
        return null;

    }

    @Override
    public List<Event> getAllEvents() {

        List<Event> allEvents = new ArrayList<>();
        String sql = "SELECT event_id, user_id, workout_id, workout_date " +
                "FROM workout_event;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
        while(result.next()) {
            allEvents.add(mapRowToEvent(result));
        }

        return allEvents;
    }

    @Override
    public List<Event> getAllEventsByUser(int userId) {

        List<Event> eventsByUser = new ArrayList<>();
        String sql = "SELECT event_id, user_id, workout_id, workout_date " +
                "FROM workout_event " +
                "WHERE user_id = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
        while(result.next()) {
            eventsByUser.add(mapRowToEvent(result));
        }

        return eventsByUser;

    }

    @Override
    public List<Event> getAllEventsByDate(LocalDate localDate) {

        List<Event> eventsByDate = new ArrayList<>();
        String sql = "SELECT event_id, user_id, workout_id, workout_date " +
                "FROM workout_event " +
                "WHERE workout_date = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, localDate);
        while(result.next()) {
            eventsByDate.add(mapRowToEvent(result));
        }

        return eventsByDate;
    }

    @Override
    public List<Event> getAllEventsByWorkout(int workoutId) {
        return null;
    }

    @Override
    public Event createEvent(Event event) {
        return null;
    }

    @Override
    public boolean updateEvent(Event event) {
        return false;
    }

    @Override
    public void deleteEvent(int id) {

    }

    private Event mapRowToEvent(SqlRowSet result) {
        Event event = new Event(
                result.getInt("event_id"),
                result.getInt("user_id"),
                result.getInt("workout_id"),
                result.getDate("workout_date").toLocalDate()
        );
        return event;
    }
}
