package com.portfolio.exerciseapp.model;

public class UserExerciseList {

    private int userId;
    private int workoutId;

    public UserExerciseList() {};

    public UserExerciseList(int userId, int workoutId) {
        this.userId = userId;
        this.workoutId = workoutId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(int exerciseId) {
        this.workoutId = workoutId;
    }
}
