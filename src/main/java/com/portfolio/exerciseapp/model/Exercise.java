package com.portfolio.exerciseapp.model;

public class Exercise {

    private int exerciseId;
    private String name;

    public Exercise() {};

    public Exercise(int exerciseId, String name) {
        this.exerciseId = exerciseId;
        this.name = name;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
