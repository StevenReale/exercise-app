package com.portfolio.exerciseapp.dao;

import com.portfolio.exerciseapp.model.Workout;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcWorkoutDAO implements WorkoutDAO{

    private final JdbcTemplate jdbcTemplate;

    public JdbcWorkoutDAO (JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }

    @Override
    public Workout getWorkoutById(int id) {
        String sql = "SELECT workout_id, exercise_id, num_sets, num_reps, weight, workout_time, distance " +
                "FROM workout WHERE workout_id = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
        if (result.next()) {
            return mapRowToWorkout(result);
        }
        return null;
    }

    @Override
    public List<Workout> getAllWorkouts() {
        List<Workout> newList = new ArrayList<>();
        String sql = "SELECT workout_id, exercise_id, num_sets, num_reps, weight, workout_time, distance " +
                "FROM workout;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
        while (result.next()){
            newList.add(mapRowToWorkout(result));
        }
        return newList;
    }

    @Override
    public List<Workout> getAllWorkoutsByExerciseId(int id) {
        List<Workout> newList = new ArrayList<>();
        String sql = "SELECT workout_id, exercise_id, num_sets, num_reps, weight, workout_time, distance " +
                "FROM workout WHERE exercise_id = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
        while (result.next()){
            newList.add(mapRowToWorkout(result));
        }
        return newList;
    }

    @Override
    public Workout createWorkout(Workout workout) {
        String sql = "INSERT INTO workout (exercise_id, num_sets, num_reps, weight, workout_time, distance) " +
                "VALUES (?, ?, ?, ?, ?, ?) RETURNING workout_id;";
        Integer workoutID = jdbcTemplate.queryForObject(sql, Integer.class,
                workout.getExerciseId(),
                workout.getSets(),
                workout.getReps(),
                workout.getWeight(),
                workout.getTime(),
                workout.getDistance());
        return getWorkoutById(workoutID);
    }

    @Override
    public boolean updateWorkout(Workout workout) {
        String sql = "UPDATE workout SET exercise_id = ?, num_sets = ?, num_reps = ?, weight = ?, workout_time = ?, distance = ? " +
                "WHERE workout_id = ?;";
        return jdbcTemplate.update(sql,
                workout.getExerciseId(),
                workout.getSets(),
                workout.getReps(),
                workout.getWeight(),
                workout.getTime(),
                workout.getDistance(),
                workout.getWorkoutId()) > 0;
    }

    @Override
    public void deleteWorkout(int id) {
        String sql = "DELETE FROM workout WHERE workout_id = ?;";
        jdbcTemplate.update(sql, id);

    }

    private Workout mapRowToWorkout(SqlRowSet result) {
        return new Workout(
                result.getInt("workout_id"),
                result.getInt("exercise_id"),
                result.getInt("num_sets"),
                result.getInt("num_reps"),
                result.getInt("weight"),
                result.getInt("workout_time"),
                result.getDouble("distance")
        );
    }
}
