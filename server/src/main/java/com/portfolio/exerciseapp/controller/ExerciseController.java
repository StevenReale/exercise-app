package com.portfolio.exerciseapp.controller;

import com.portfolio.exerciseapp.dao.ExerciseDAO;
import com.portfolio.exerciseapp.model.Exercise;
import com.portfolio.exerciseapp.service.ExerciseService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
@RequestMapping(path = "/exercises")
public class ExerciseController {

    private ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @PreAuthorize("permitAll()")
    @RequestMapping(method = RequestMethod.GET)
    public List<Exercise> getAllExercises() {
        return exerciseService.getAllExercises();
    }

    @PreAuthorize("permitAll()")
    @RequestMapping(path = "/{exerciseId}", method = RequestMethod.GET)
    public Exercise getExerciseById(@PathVariable int exerciseId) {
        return exerciseService.getExerciseById(exerciseId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public Exercise addExercise(@RequestBody Exercise exercise, Principal principal) {
        Exercise created = new Exercise();
        try {
            created = exerciseService.createExercise(exercise, principal);
        } catch (java.nio.file.AccessDeniedException e) {
            e.printStackTrace();
        }
        return created;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.PUT)
    public boolean updateExercise(@RequestBody Exercise exercise, Principal principal) {
        try {
            return exerciseService.updateExercise(exercise, principal);
        } catch (java.nio.file.AccessDeniedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteExercise(@RequestBody Exercise exercise, Principal principal) {

            exerciseService.deleteExerciseById(exercise.getExerciseId(), principal);

    }
}

