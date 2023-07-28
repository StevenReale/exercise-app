package com.portfolio.exerciseapp.dao;

import com.portfolio.exerciseapp.model.Workout;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcWorkoutListDao implements WorkoutListDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcWorkoutListDao (JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }


    @Override
    public List<Workout> getAllWorkoutsByUserId(int userId) {
        List<Workout> workoutsById = new ArrayList<>();
        String sql = "SELECT w.workout_id, w.exercise_id, w.num_sets, w.num_reps, w.weight, w.workout_time, w.distance " +
                "FROM workout w " +
                "JOIN workout_list wl USING (workout_id)" +
                "WHERE wl.user_id = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
        while(result.next()) {
            Workout thisWorkout = new Workout(
                    result.getInt("workout_id"),
                    result.getInt("exercise_id"),
                    result.getInt("num_sets"),
                    result.getInt("num_reps"),
                    result.getInt("weight"),
                    result.getInt("workout_time"),
                    result.getDouble("distance")
            );
            workoutsById.add(thisWorkout);
        }
        return workoutsById;
    }

    @Override
    public boolean addWorkoutToUserList(int userId, int workoutId) {
        String sql = "INSERT INTO workout_list (user_id, workout_id) " +
                "VALUES (?, ?);";
        return jdbcTemplate.update(sql, userId, workoutId) > 0;
    }

    @Override
    public void deleteWorkoutFromList(int userId, int workoutId) {
        String sql = "DELETE FROM workout_list " +
                "WHERE user_id = ? AND workout_id = ?;";
        jdbcTemplate.update(sql, userId, workoutId);
    }

    @Override
    public void deleteAllWorkoutsFromListByWorkoutId(int workoutId) {
        String sql = "DELETE FROM workout_list " +
                "WHERE workout_id = ?;";
        jdbcTemplate.update(sql, workoutId);
    }
}
