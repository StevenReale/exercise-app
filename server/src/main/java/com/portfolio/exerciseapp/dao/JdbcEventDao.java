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

        String sql = "SELECT event_id, user_id, workout_date " +
                "FROM event " +
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
        String sql = "SELECT event_id, user_id, workout_date " +
                "FROM event;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
        while(result.next()) {
            allEvents.add(mapRowToEvent(result));
        }

        return allEvents;
    }

    @Override
    public List<Event> getAllEventsByUser(int userId) {

        List<Event> eventsByUser = new ArrayList<>();
        String sql = "SELECT event_id, user_id, workout_date " +
                "FROM event " +
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
        String sql = "SELECT event_id, user_id, workout_date " +
                "FROM event " +
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
        String sql = "SELECT e.event_id, e.user_id, e.workout_date " +
            "FROM event e " +
            "JOIN workout_event we ON (e.event_id = we.event_id) " +
            "JOIN workout w ON (we.workout_id = w.workout_id) " +
            "WHERE w.exercise_id = ? " +
            "GROUP BY e.event_id";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, exerciseId);
        while(result.next()) {
            eventsByExercise.add(mapRowToEvent(result));
        }

        return eventsByExercise;
    }

    @Override
    public Event createEvent(Event event) {

        String sql = "INSERT INTO event (user_id, workout_date) " +
                "VALUES (?, ?) RETURNING event_id;";
        Integer eventId = jdbcTemplate.queryForObject(sql, Integer.class,
                event.getUserId(),
                event.getDate()
        );
        List<Workout> eventWorkouts = event.getWorkouts();
        for (int i = 0; i < eventWorkouts.size(); i++) {
            String innerSql = "INSERT INTO workout_event (event_id, workout_id) " +
                    "VALUES (?, ?);";
            jdbcTemplate.update(innerSql, eventId, eventWorkouts.get(i).getWorkoutId());
        }
        return getEventById(eventId);
    }

    @Override
    public boolean updateEvent(Event event) {

        String sql = "UPDATE event SET user_id = ?, workout_date = ? " +
                "WHERE event_id = ?;";
        boolean updated = jdbcTemplate.update(sql,

                event.getUserId(),
                event.getDate(),
                event.getEventId()

                ) > 0;
        if (!updated) return false;

        sql = "DELETE FROM workout_event WHERE event_id = ?;";
        jdbcTemplate.update(sql, event.getEventId());

        for (int i = 0; i < event.getWorkouts().size(); i++) {
            sql = "INSERT INTO workout_event (event_id, workout_id) VALUES (?,?);";
            jdbcTemplate.update(sql, event.getEventId(), event.getWorkouts().get(i).getWorkoutId());
        }
        return true;
    }

    @Override
    public void deleteEvent(int id) {
        String sql = "DELETE FROM workout_event WHERE event_id = ?;";
        jdbcTemplate.update(sql, id);
        sql = "DELETE FROM event WHERE event_id = ?;";
        jdbcTemplate.update(sql, id);

    }

    private Event mapRowToEvent(SqlRowSet result) {

        int eventId = result.getInt("event_id");
        List<Workout> workouts = workoutDao.getAllWorkoutsByEventId(eventId);

        Event event = new Event(
                eventId,
                result.getInt("user_id"),
                workouts,
                result.getDate("workout_date").toLocalDate()
        );
        return event;
    }
}
