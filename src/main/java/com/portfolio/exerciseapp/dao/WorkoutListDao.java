package com.portfolio.exerciseapp.dao;

import com.portfolio.exerciseapp.model.Workout;

import java.util.List;

public interface WorkoutListDao {

    public List<Workout> getAllWorkoutsByUserId(int userId);

    public boolean addWorkoutToUserList(int userId, int workoutId);

    public void deleteWorkoutFromList(int userId, int workoutId);

}
