package com.portfolio.exerciseapp.model;

public class List {
    private int listId;
    private int userId;
    private int exerciseId;

    public List() {};

    public List(int listId, int userId, int exerciseId) {
        this.listId = listId;
        this.userId = userId;
        this.exerciseId = exerciseId;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }
}
