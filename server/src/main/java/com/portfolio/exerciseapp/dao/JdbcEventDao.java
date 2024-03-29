package com.portfolio.exerciseapp.dao;

import com.portfolio.exerciseapp.model.Event;
import com.portfolio.exerciseapp.model.Workout;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcEventDao implements EventDao {

    private final JdbcTemplate jdbcTemplate;
    private final WorkoutDAO workoutDao;

    public JdbcEventDao (JdbcTemplate jdbcTemplate, WorkoutDAO workoutDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.workoutDao = workoutDao;
    }

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
    public List<Event> getAllEventsByExercise(int exerciseId) {

        List<Event> eventsByExercise = new ArrayList<>();
        String sql = "SELECT e.event_id, e.user_id, e.workout_id, e.workout_date " +
                "FROM workout_event e " +
                "JOIN workout w USING (workout_id) " +
                "WHERE w.exercise_id = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, exerciseId);
        while(result.next()) {
            eventsByExercise.add(mapRowToEvent(result));
        }

        return eventsByExercise;
    }

    @Override
    public Event createEvent(Event event) {

        String sql = "INSERT INTO workout_event (user_id, workout_id, workout_date) " +
                "VALUES (?, ?, ?) RETURNING event_id;";
        Integer eventId = jdbcTemplate.queryForObject(sql, Integer.class,
                event.getUserId(),
                event.getWorkout().getWorkoutId(),
                event.getDate()
        );
        return getEventById(eventId);
    }

    @Override
    public boolean updateEvent(Event event) {

        String sql = "UPDATE workout_event SET user_id = ?, workout_id = ?, workout_date = ? " +
                "WHERE event_id = ?;";
        return jdbcTemplate.update(sql,

                event.getUserId(),
                event.getWorkout().getWorkoutId(),
                event.getDate(),
                event.getEventId()

                ) > 0;
    }

    @Override
    public void deleteEvent(int id) {
        String sql = "DELETE FROM workout_event WHERE event_id = ?;";
        jdbcTemplate.update(sql, id);
    }

    private Event mapRowToEvent(SqlRowSet result) {
        int workoutId = result.getInt("workout_id");
        Workout workout = workoutDao.getWorkoutById(workoutId);

        Event event = new Event(
                result.getInt("event_id"),
                result.getInt("user_id"),
                workout,
                result.getDate("workout_date").toLocalDate()
        );
        return event;
    }
}
