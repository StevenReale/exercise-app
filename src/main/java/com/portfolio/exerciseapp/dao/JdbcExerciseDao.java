package com.portfolio.exerciseapp.dao;

import com.portfolio.exerciseapp.model.Exercise;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcExerciseDao implements ExerciseDAO {

    private final JdbcTemplate jdbcTemplate;

    public JdbcExerciseDao (JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }

    @Override
    public Exercise getExerciseById(int id) {

        String sql = "SELECT exercise_id, exercise_name " +
                "FROM exercise WHERE exercise_id = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
        if(result.next()) {
            return mapRowToObject(result);
        }
        return null;
    }

    @Override
    public List<Exercise> getAllExercises() {
        List<Exercise> allExercises = new ArrayList<>();
        String sql = "SELECT exercise_id, exercise_name " +
                "FROM exercise;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
        while (result.next()) {
            allExercises.add(mapRowToObject(result));

        }

        return allExercises;
    }

    @Override
    public Exercise createExercise(Exercise exercise) {
        String sql = "INSERT INTO exercise (exercise_name) " +
                "VALUES (?) RETURNING exercise_id;";
        int newID = jdbcTemplate.queryForObject(sql, Integer.class, exercise.getName());
        return getExerciseById(newID);

    }

    @Override
    public void deleteExerciseById(int id) {
        String sql = "DELETE FROM exercise " +
                "WHERE exercise_id = ?;";
        jdbcTemplate.update(sql, id);
    }

    private Exercise mapRowToObject(SqlRowSet result) {
        Exercise exercise = new Exercise();
        exercise.setExerciseId(result.getInt("exercise_id"));
        exercise.setName(result.getString("exercise_name"));

        return exercise;

    }
}
