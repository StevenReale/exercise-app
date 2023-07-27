package com.portfolio.exerciseapp.dao;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.portfolio.exerciseapp.model.Workout;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;

public class JdbcWorkoutListDaoTests extends BaseDaoTests {

    private JdbcWorkoutListDao jdbcWorkoutListDao;
    private JdbcWorkoutDAO jdbcWorkoutDao;

    private List<Workout> USER_101_WORKOUTS;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcWorkoutListDao = new JdbcWorkoutListDao(jdbcTemplate);
        jdbcWorkoutDao = new JdbcWorkoutDAO(jdbcTemplate);
        USER_101_WORKOUTS = Arrays.asList(jdbcWorkoutDao.getWorkoutById(1), jdbcWorkoutDao.getWorkoutById(2));
    }

    @Test
    public void get_all_workouts_by_user_id_returns_correct_list() {
        //Arrange
        List<Workout> user101Workouts = jdbcWorkoutListDao.getAllWorkoutsByUserId(101);

        //Assert
        Assert.assertEquals("lengths should match", USER_101_WORKOUTS.size(), user101Workouts.size());
        Assert.assertEquals("workout ids of first elements should match", USER_101_WORKOUTS.get(0).getWorkoutId(), user101Workouts.get(0).getWorkoutId());
        Assert.assertEquals("workout ids of last elements should match", USER_101_WORKOUTS.get(USER_101_WORKOUTS.size()-1).getWorkoutId(), user101Workouts.get(user101Workouts.size()-1).getWorkoutId());
    }

    @Test
    public void adding_workout_to_list_updates_database() {
        //Arrange
        final int ADDED_WORKOUT_ID = 4;
        boolean isAdded = jdbcWorkoutListDao.addWorkoutToUserList(101, ADDED_WORKOUT_ID);
        List<Workout> user101Workouts = jdbcWorkoutListDao.getAllWorkoutsByUserId(101);

        //Assert
        Assert.assertTrue("creating event should return true", isAdded);
        Assert.assertEquals("list of workouts should be size 3", user101Workouts.size(), 3);
        Assert.assertEquals("last workout on list should have correct ID", user101Workouts.get(user101Workouts.size()-1).getWorkoutId(), ADDED_WORKOUT_ID);
    }

    @Test
    public void deleting_workout_from_list_removes_from_database() {
        //Arrange
        jdbcWorkoutListDao.deleteWorkoutFromList(102, 1);
        List<Workout> user102Workouts = jdbcWorkoutListDao.getAllWorkoutsByUserId(102);

        //Assert
        Assert.assertEquals("list should be length 1", user102Workouts.size(), 1);
        Assert.assertEquals("workout id of first element should be 3", 3, user102Workouts.get(0).getWorkoutId());
    }

    @Test
    public void deleting_all_workouts_by_workout_id_correct_updates_database() {
        //Arrange
        jdbcWorkoutListDao.deleteAllWorkoutsFromListByWorkoutId(1);
        List<Workout> user1WorkoutsWithDeletedWorkout1 = jdbcWorkoutListDao.getAllWorkoutsByUserId(101);

        //Assert
        Assert.assertEquals("list should have one element", 1, user1WorkoutsWithDeletedWorkout1.size());


    }

}
