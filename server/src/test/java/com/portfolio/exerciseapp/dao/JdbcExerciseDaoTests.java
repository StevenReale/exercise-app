package com.portfolio.exerciseapp.dao;

import com.portfolio.exerciseapp.model.Exercise;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;

public class JdbcExerciseDaoTests extends BaseDaoTests {

    private final Exercise EXERCISE_1 = new Exercise(1, "exercise 1");
    private final Exercise EXERCISE_2 = new Exercise(2, "exercise 2");
    private final Exercise EXERCISE_3 = new Exercise(3, "exercise 3");

    private final List<Exercise> ALL_EXERCISES =Arrays.asList(EXERCISE_1, EXERCISE_2, EXERCISE_3);
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
        Assert.assertEquals("length should be correct", ALL_EXERCISES.size() ,exerciseList.size());
        assertExercisesMatch("first exercise should match", ALL_EXERCISES.get(0), exerciseList.get(0));
        assertExercisesMatch("last exercise should match", ALL_EXERCISES.get(ALL_EXERCISES.size()-1), exerciseList.get(exerciseList.size()-1));
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
        Exercise thisExercise = jdbcExerciseDao.getExerciseById(4);

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
    public void update_exercise_reflected_in_database() {
        //Arrange
        String newName = "new exercise name";
        EXERCISE_1.setName(newName);

        //Act
        boolean result = jdbcExerciseDao.updateExercise(EXERCISE_1);
        Exercise modifiedExercise = jdbcExerciseDao.getExerciseById(EXERCISE_1.getExerciseId());

        //Assert
        Assert.assertTrue("updated succesfully", result);
        Assert.assertEquals("name changed", newName, modifiedExercise.getName());
    }

    @Test
    public void delete_exercise_deletes_from_database() {
        //Act
        jdbcExerciseDao.deleteExerciseById(EXERCISE_3.getExerciseId());
        Exercise returned = jdbcExerciseDao.getExerciseById(EXERCISE_3.getExerciseId());

        //Assert
        Assert.assertNull(returned);
    }

    public void assertExercisesMatch(String message, Exercise expected, Exercise actual) {
        Assert.assertEquals(message + ": exercise_id", expected.getExerciseId(), actual.getExerciseId());
        Assert.assertEquals(message + ": exercise_name", expected.getName(), actual.getName());
    }



}
