package com.portfolio.exerciseapp.service;

import com.portfolio.exerciseapp.dao.UserDao;
import com.portfolio.exerciseapp.dao.WorkoutDAO;
import com.portfolio.exerciseapp.dao.WorkoutListDao;
import com.portfolio.exerciseapp.model.User;
import com.portfolio.exerciseapp.model.Workout;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.List;

@Component
public class WorkoutService {

    private WorkoutDAO workoutDAO;
    private WorkoutListDao workoutListDao;
    private UserDao userDao;

    public WorkoutService(WorkoutDAO workoutDAO, WorkoutListDao workoutListDao) {

        this.workoutDAO = workoutDAO;
        this.workoutListDao = workoutListDao;
        }

    public Workout getWorkoutById(int workoutId) { return workoutDAO.getWorkoutById(workoutId); }

    public List<Workout> getAllWorkoutsByUser(Principal principal) {
        User user = getUser(principal);

        return workoutListDao.getAllWorkoutsByUserId(user.getId());
    }

    public List<Workout> addWorkoutToUserList(Workout workout, Principal principal) {
        User user = getUser(principal);
        workoutListDao.addWorkoutToUserList(user.getId(), workout.getWorkoutId());
        return getAllWorkoutsByUser(principal);
    }

    public Workout createWorkout(Workout workout) { return workoutDAO.createWorkout(workout); }

    public boolean updateWorkout(Workout workout) { return workoutDAO.updateWorkout(workout); }

    public void deleteWorkout(int id) { workoutDAO.deleteWorkout(id); }

    private User getUser(Principal principal) {
        String username = principal.getName();
        return userDao.getByUsername(username);
    }

}
