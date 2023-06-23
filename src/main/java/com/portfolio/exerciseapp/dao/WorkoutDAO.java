package com.portfolio.exerciseapp.dao;

import com.portfolio.exerciseapp.model.Workout;

import java.util.List;

public interface WorkoutDAO {

    public Workout getWorkoutById(int id);

    public List<Workout> getAllWorkouts();

    public List<Workout> getAllWorkoutsByExerciseId(int id);

    public Workout createWorkout(Workout workout);

    public boolean updateWorkout(Workout workout);

    public void deleteWorkout(int id);


}
