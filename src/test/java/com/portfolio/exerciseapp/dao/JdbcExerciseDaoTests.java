package com.portfolio.exerciseapp.dao;

import com.portfolio.exerciseapp.model.Exercise;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class JdbcExerciseDaoTests extends BaseDaoTests {

    private JdbcExerciseDao jdbcExerciseDao;

    @Before
    public void setup() {
        jdbcExerciseDao = new JdbcExerciseDao(dataSource);
    }

    @Test
    public void get_all_exercises_returns_all_exercises() {
        //Act
        List<Exercise> exerciseList = jdbcExerciseDao.getAllExercises();

        //Assert
        Assert.assertEquals("length should be correct", exerciseList.size(), 2);
    }



}
