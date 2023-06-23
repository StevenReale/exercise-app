package com.portfolio.exerciseapp.dao;

import com.portfolio.exerciseapp.model.Workout;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;

public class JdbcWorkoutDaoTests extends BaseDaoTests{

    private final Workout WORKOUT_1 = new Workout(1, 1, 2, 2, 2, 2, 10.5);
    private final Workout WORKOUT_2 = new Workout(2, 1, 3, 3, 3, 3, 100.2);
    private final Workout WORKOUT_3 = new Workout(3, 2, 1, 1, 1, 1, 200.6);
    private final Workout WORKOUT_4 = new Workout(4, 2, 3, 4, 5, 6, 7.8);

    private final List<Workout> ALL_WORKOUTS = Arrays.asList(WORKOUT_1, WORKOUT_2, WORKOUT_3, WORKOUT_4);
    private final List<Workout> EXERCISE_1_WORKOUTS = Arrays.asList(WORKOUT_1, WORKOUT_2);

    private JdbcWorkoutDAO jdbcWorkoutDao;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcWorkoutDao = new JdbcWorkoutDAO(jdbcTemplate);
    }

    @Test
    public void get_workout_by_id_returns_correct_workout() {
        //Act
        Workout retrievedWorkout1 = jdbcWorkoutDao.getWorkoutById(1);
        Workout retrievedWorkout2 = jdbcWorkoutDao.getWorkoutById(2);

        //Assert
        assertWorkoutsMatch("workout 1", WORKOUT_1, retrievedWorkout1);
        assertWorkoutsMatch("workout 2", WORKOUT_2, retrievedWorkout2);
    }

    @Test
    public void get_all_workouts_returns_complete_list_of_workouts() {
        //Act
        List<Workout> retrievedList = jdbcWorkoutDao.getAllWorkouts();

        //Assert
        Assert.assertEquals("list lengths should match", ALL_WORKOUTS.size(), retrievedList.size());
        assertWorkoutsMatch("first workouts should match", ALL_WORKOUTS.get(0), retrievedList.get(0));
        assertWorkoutsMatch("last workouts should match", ALL_WORKOUTS.get(ALL_WORKOUTS.size()-1), retrievedList.get(retrievedList.size()-1));
    }

    @Test
    public void get_all_workouts_by_exercise_id_returns_correct_subset() {
        //Act
        List<Workout> retrievedList = jdbcWorkoutDao.getAllWorkoutsByExerciseId(1);

        //Assert
        Assert.assertEquals("list lengths should match", EXERCISE_1_WORKOUTS.size(), retrievedList.size());
        assertWorkoutsMatch("first workouts should match", EXERCISE_1_WORKOUTS.get(0), retrievedList.get(0));
        assertWorkoutsMatch("last workouts should match", EXERCISE_1_WORKOUTS.get(EXERCISE_1_WORKOUTS.size()-1), retrievedList.get(retrievedList.size()-1));
    }

    @Test
    public void create_workout_correctly_populates_with_weight_data() {
        //Arrange
        Workout createdWorkout = new Workout();
        createdWorkout.setExerciseId(1);
        createdWorkout.setSets(3);
        createdWorkout.setReps(10);;
        createdWorkout.setWeight(50);

        //Act
        Workout returnedWorkout = jdbcWorkoutDao.createWorkout(createdWorkout);
        createdWorkout.setWorkoutId(returnedWorkout.getWorkoutId());

        //Assert
        assertWorkoutsMatch("created workout same as returned workout", createdWorkout, returnedWorkout);

    }

    @Test
    public void create_workout_correctly_populates_with_distance_data() {
        //Arrange
        Workout createdWorkout = new Workout();
        createdWorkout.setExerciseId(2);
        createdWorkout.setTime(20);
        createdWorkout.setDistance(10.5);;

        //Act
        Workout returnedWorkout = jdbcWorkoutDao.createWorkout(createdWorkout);
        createdWorkout.setWorkoutId(returnedWorkout.getWorkoutId());

        //Assert
        assertWorkoutsMatch("created workout same as returned workout", createdWorkout, returnedWorkout);

    }

    @Test
    public void updating_workout_reflected_in_database() {
        //Arrange
        Workout updateWorkout = new Workout();
        updateWorkout.setWorkoutId(WORKOUT_1.getWorkoutId());
        updateWorkout.setExerciseId(2);
        updateWorkout.setTime(60);
        updateWorkout.setDistance(30.2);

        //Act
        boolean success = jdbcWorkoutDao.updateWorkout(updateWorkout);
        Workout returnedWorkout = jdbcWorkoutDao.getWorkoutById(updateWorkout.getWorkoutId());

        //Assert
        Assert.assertTrue("should return successful update", success);
        assertWorkoutsMatch("workout should return updated data", updateWorkout, returnedWorkout);
    }

    @Test
    public void deleted_workout_returns_null_from_database() {

        //Act
        jdbcWorkoutDao.deleteWorkout(WORKOUT_1.getWorkoutId());
        Workout deletedWorkout = jdbcWorkoutDao.getWorkoutById(WORKOUT_1.getWorkoutId());

        //Assert
        Assert.assertNull("deleted workout should be null", deletedWorkout);
    }

    private void assertWorkoutsMatch(String message, Workout expected, Workout actual) {
        Assert.assertEquals(message + ": workout ids match", expected.getWorkoutId(), actual.getWorkoutId());
        Assert.assertEquals(message + ": exercise ids match", expected.getExerciseId(), actual.getExerciseId());
        Assert.assertEquals(message + ": num_sets match", expected.getSets(), actual.getSets());
        Assert.assertEquals(message + ": reps match", expected.getReps(), actual.getReps());
        Assert.assertEquals(message + ": weights match", expected.getWeight(), actual.getWeight());
        Assert.assertEquals(message + ": times match", expected.getTime(), actual.getTime());
        Assert.assertEquals(message + ": distances match", expected.getDistance(), actual.getDistance(), 0);
    }

}
