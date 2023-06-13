package com.portfolio.exerciseapp.dao;

import com.portfolio.exerciseapp.model.Exercise;

import java.util.List;

public interface ExerciseDAO {

    public Exercise getExerciseById(int id);

    public List<Exercise> getAllExercises();
}
