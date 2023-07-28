package com.portfolio.exerciseapp.controller;

import com.portfolio.exerciseapp.model.Workout;
import com.portfolio.exerciseapp.service.WorkoutService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
@RequestMapping( path = "/workouts" )
public class WorkoutController {

    private WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) { this.workoutService = workoutService; }

    @PreAuthorize("permitAll()")
    @RequestMapping ( path = "/{workoutId}", method = RequestMethod.GET )
    public Workout getByWorkoutId(@PathVariable int workoutId) {
        return workoutService.getWorkoutById(workoutId);
    }

    @RequestMapping ( method = RequestMethod.GET )
    public List<Workout> getAllWorkoutsByUser(Principal principal) {
        return workoutService.getAllWorkoutsByUser(principal);
    }

    @RequestMapping ( method = RequestMethod.POST)
    public List<Workout> createWorkout (@RequestBody Workout workout, Principal principal) {
        Workout createdWorkout = workoutService.createWorkout(workout);
        return workoutService.addWorkoutToUserList(createdWorkout, principal);
    }

    @RequestMapping ( method = RequestMethod.PUT)
    public boolean updateWorkout (@RequestBody Workout workout) {
        return workoutService.updateWorkout(workout);
    }

    @RequestMapping (method = RequestMethod.DELETE)
    public void deleteWorkout(@RequestBody Workout workout) {
        workoutService.deleteWorkout(workout.getWorkoutId());
    }


}
