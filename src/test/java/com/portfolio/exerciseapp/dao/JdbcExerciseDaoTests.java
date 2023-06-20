package com.portfolio.exerciseapp.dao;

import com.portfolio.exerciseapp.model.Exercise;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class JdbcExerciseDaoTests extends BaseDaoTests {

    private final Exercise EXERCISE_1 = new Exercise(1, "exercise 1");
    private final Exercise EXERCISE_2 = new Exercise(2, "exercise 2");
    private JdbcExerciseDao jdbcExerciseDao;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcExerciseDao = new JdbcExerciseDao(jdbcTemplate);
    }

    @Test
    public void get_all_exercises_returns_all_exercises() {
        //Act
        List<Exercise> exerciseList = jdbcExerciseDao.getAllExercises();

        //Assert
        Assert.assertEquals("length should be correct", exerciseList.size(), 2);
        assertExercisesMatch("first exercise should match", EXERCISE_1, exerciseList.get(0));
        assertExercisesMatch("second exercise should match", EXERCISE_2, exerciseList.get(1));
    }

    @Test
    public void get_exercise_by_id_returns_correct_exercise() {
        //Act
        Exercise thisExercise = jdbcExerciseDao.getExerciseById(1);

        //Assert
        assertExercisesMatch("exercise_id = 1 should match", EXERCISE_1, thisExercise);
    }

    @Test
    public void get_exercise_by_id_returns_null_if_not_valid_id() {
        //Act
        Exercise thisExercise = jdbcExerciseDao.getExerciseById(3);

        //Assert
        Assert.assertNull(thisExercise);
    }

    @Test
    public void create_exercise_successfully_adds_new_exercise_to_database() {
        //Arrange
        Exercise newExercise = new Exercise();
        newExercise.setName("new exercise");

        //Act
        Exercise returnedExercise = jdbcExerciseDao.createExercise(newExercise);
        newExercise.setExerciseId(returnedExercise.getExerciseId());

        //Assert
        assertExercisesMatch("exercise returned from database matches created exercise", newExercise, returnedExercise);
    }

    @Test
    public void delete_exercise_deletes_from_database() {
        //Act
        jdbcExerciseDao.deleteExerciseById(EXERCISE_1.getExerciseId());
        Exercise returned = jdbcExerciseDao.getExerciseById(EXERCISE_1.getExerciseId());

        //Assert
        Assert.assertNull(returned);
    }

    public void assertExercisesMatch(String message, Exercise expected, Exercise actual) {
        Assert.assertEquals(message + ": exercise_id", expected.getExerciseId(), actual.getExerciseId());
        Assert.assertEquals(message + ": exercise_name", expected.getName(), actual.getName());
    }



}
