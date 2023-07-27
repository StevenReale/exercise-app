package com.portfolio.exerciseapp.service;

import com.portfolio.exerciseapp.dao.WorkoutDAO;
import com.portfolio.exerciseapp.model.Workout;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WorkoutService {

    private WorkoutDAO workoutDAO;

    public WorkoutService(WorkoutDAO workoutDAO) {
        this.workoutDAO = workoutDAO;
    }

    public Workout getWorkoutById(int workoutId) { return workoutDAO.getWorkoutById(workoutId); }

    public List<Workout> getAllWorkoutsByUser(int userId) { return workoutDAO.getAllWorkoutsByUser(userId);}

}
