package com.portfolio.exerciseapp.service;

import com.portfolio.exerciseapp.dao.*;
import com.portfolio.exerciseapp.model.*;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.List;

@Component
public class ExerciseService {

    private ExerciseDAO exerciseDAO;
    private WorkoutDAO workoutDAO;
    private EventDao eventDao;
    private WorkoutListDao workoutListDao;
    private UserDao userDao;

    public ExerciseService (ExerciseDAO exerciseDAO, WorkoutDAO workoutDAO, UserDao userDao, EventDao eventDao) {
        this.exerciseDAO = exerciseDAO;
        this.workoutDAO = workoutDAO;
        this.userDao = userDao;
        this.eventDao = eventDao;
    }

    /**
     * Gets exercise by ID
     *
     * Business rules: no access restrictions
     */
    public Exercise getExerciseById(int id) {return exerciseDAO.getExerciseById(id);}

    /**
     * Gets all exercises
     *
     * Business rules: no access restrictions
     */
    public List<Exercise> getAllExercises() {return exerciseDAO.getAllExercises();}

    /**
     * Creates a new exercise
     *
     * Business rules: authenticated users and admins only
     */
    public Exercise createExercise(Exercise exercise, Principal principal) throws AccessDeniedException {

        User user;

        try {
            user = getUser(principal);
            return exerciseDAO.createExercise(exercise);
        } catch (NullPointerException e) {
            throw new AccessDeniedException("Registered Users Only");
        }

    }

    /**
     * Updates an exercise
     *
     * Business rules: admins only
     */
    public boolean updateExercise(Exercise exercise, Principal principal) throws AccessDeniedException {

        User user;

        try {
            user = getUser(principal);
            if(!user.getAuthorities().contains(Authority.ADMIN_AUTHORITY)) {
                throw new AccessDeniedException("Must have admin role to update exercise");
            }
            return exerciseDAO.updateExercise(exercise);
        } catch (NullPointerException e) {
            throw new AccessDeniedException("Must have admin role to update exercise");
        }

    }

    /**
     * Deletes an exercise. Use with extreme caution, as it deletes events from user's log
     *
     * Business rules: admins only
     */

    public void deleteExerciseById(int id, Principal principal) {
        List<Workout> workoutsWithThisExercise = workoutDAO.getAllWorkoutsByExerciseId(id);
        List<Event> eventsWithThisExercise = eventDao.getAllEventsByExercise(id);

        //iterates through all workouts with the exercise and deletes them from workout_list and workout tables
        for(Workout workout : workoutsWithThisExercise) {
            int currentWorkoutId = workout.getWorkoutId();
            workoutListDao.deleteAllWorkoutsFromListByWorkoutId(currentWorkoutId);
            workoutDAO.deleteWorkout(currentWorkoutId);
        }

        //iterates through all events with the exercise and deletes them from event table
        for (Event event : eventsWithThisExercise) {
            eventDao.deleteEvent(event.getEventId());
        }

        //delete from exercise table
        exerciseDAO.deleteExerciseById(id);
    }

    /*
     * Helper method to get the User object from the Principal.
     */
    private User getUser(Principal principal) {
        String username = principal.getName();
        User user = userDao.getByUsername(username);
        return user;
    }

    /*
     * Helper method to check if a user is an Admin user.
     */
    private boolean isAdminUser(User user) {
        return user.getAuthorities().contains(Authority.ADMIN_AUTHORITY);
    }
}
