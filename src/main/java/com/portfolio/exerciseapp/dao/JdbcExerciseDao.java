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

    public JdbcExerciseDao (DataSource dataSource) { jdbcTemplate = new JdbcTemplate(dataSource); }

    @Override
    public Exercise getExerciseById(int id) {
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

    private Exercise mapRowToObject(SqlRowSet result) {
        Exercise exercise = new Exercise();
        exercise.setExerciseId(result.getInt("exercise_id"));
        exercise.setName(result.getString("exercise_name"));

        return exercise;

    }
}
