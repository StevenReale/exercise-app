package com.portfolio.exerciseapp.service;

import com.portfolio.exerciseapp.dao.EventDao;
import com.portfolio.exerciseapp.dao.ExerciseDAO;
import com.portfolio.exerciseapp.dao.WorkoutDAO;
import com.portfolio.exerciseapp.dao.WorkoutListDao;
import com.portfolio.exerciseapp.model.Event;
import com.portfolio.exerciseapp.model.Exercise;
import com.portfolio.exerciseapp.model.Workout;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.List;

@Component
public class ExerciseService {

    private ExerciseDAO exerciseDAO;
    private WorkoutDAO workoutDAO;
    private EventDao eventDao;
    private WorkoutListDao workoutListDao;

    public ExerciseService (ExerciseDAO exerciseDAO) {
        this.exerciseDAO = exerciseDAO;
        this.workoutDAO = workoutDAO;
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
    //public Exercise createExercise(Exercise exercise, Principal principal) {return exerciseDAO.createExercise(exercise);}

    /**
     * Updates an exercise
     *
     * Business rules: admins only
     */
    public boolean updateExercise(Exercise exercise, Principal principal) {return exerciseDAO.updateExercise(exercise);}

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
}
