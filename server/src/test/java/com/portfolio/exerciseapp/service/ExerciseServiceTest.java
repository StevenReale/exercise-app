package com.portfolio.exerciseapp.service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.portfolio.exerciseapp.dao.ExerciseDAO;
import com.portfolio.exerciseapp.dao.UserDao;
import com.portfolio.exerciseapp.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;


import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExerciseServiceTest {

    private final Exercise EXERCISE_1 = new Exercise(1, "Exercise 1");
    private final Exercise EXERCISE_2 = new Exercise(2, "Exercise 2");
    private final Exercise EXERCISE_3 = new Exercise(3, "Exercise 3");
    private final Exercise EXERCISE_4 = new Exercise(4, "Exercise 4");

    private final Workout WORKOUT_1 = new Workout(1, 1, 5, 5, 5, 5, 5.5);
    private final Workout WORKOUT_2 = new Workout(2, 1, 15, 15, 15, 15, 15.5);
    private final Workout WORKOUT_3 = new Workout(3, 2, 5, 5, 5, 5, 5.5);

    private final Event EVENT_1 = new Event(1, 101, 1, LocalDate.parse("2023-07-04"));
    private final Event EVENT_2 = new Event(2, 101, 2, LocalDate.parse("2023-07-04"));

    private final User USER_1 = new User(101, "user", "password", "first name", "last name", "ROLE_USER");
    private final User ADMIN_1 = new User(100, "user", "password", "first name", "last name", "ROLE_ADMIN");

    private final UserExerciseList USER_101_LIST_ELEMENT_1 = new UserExerciseList(101, 1);
    private final UserExerciseList USER_101_LIST_ELEMENT_2 = new UserExerciseList(101, 2);

    @Mock
    private Principal principal;

    @Mock
    private ExerciseDAO mockExerciseDAO;

    @Mock
    private UserDao mockUserDao;

    @InjectMocks
    private ExerciseService exServe;

    @Test
    public void get_exercise_by_id_returns_correct_exercise() {

        //Arrange
        when(mockExerciseDAO.getExerciseById(EXERCISE_1.getExerciseId())).thenReturn(EXERCISE_1);

        //Act
        Exercise retrievedExercise = exServe.getExerciseById(1);

        //Assert
        assertExercisesMatch("retrieved by ID:", EXERCISE_1, retrievedExercise);
    }

    @Test
    public void get_all_exercises_returns_complete_list() {
        //Arrange
        List<Exercise> allExercises = Arrays.asList(EXERCISE_1, EXERCISE_2, EXERCISE_3, EXERCISE_4);
        when(mockExerciseDAO.getAllExercises()).thenReturn(allExercises);

        //Act
        List<Exercise> retrievedExercises = exServe.getAllExercises();

        //Assert
        Assert.assertEquals("lengths should match", allExercises.size(), retrievedExercises.size());
        assertExercisesMatch("first should match", allExercises.get(0), retrievedExercises.get(0));
        assertExercisesMatch("last should match", allExercises.get(allExercises.size()-1), retrievedExercises.get(retrievedExercises.size()-1));

    }

    @Test
    public void creating_exercise_by_registered_user_does_not_return_null() {
        //Arrange
        setupMocksForUser(USER_1);
        Exercise newExercise = new Exercise(5, "exercise 5");
        when(mockExerciseDAO.createExercise(refEq(newExercise))).thenReturn(newExercise);
        Exercise created = new Exercise();

        //Act
        try {
            created = exServe.createExercise(newExercise, principal);
        } catch (AccessDeniedException e) {
            Assert.fail("Could not create entry: " + e);
        }

        //Assert--using mock, so only need to check that it's not null
        Assert.assertNotNull("should not return null", created);

    }

    @Test(expected = AccessDeniedException.class)
    public void creating_exercise_without_authentication_throws_error() throws AccessDeniedException {
        //Arrange
        principal = null;
        Exercise newExercise = new Exercise(6, "Exercise 6");
        Exercise created = new Exercise();
        Mockito.lenient().when(mockExerciseDAO.createExercise(refEq(newExercise))).thenReturn(newExercise);

        //Act
        created = exServe.createExercise(newExercise, principal);

        //Assert - asserted when exception is thrown. Handled by annotation

    }

    @Test
    public void updating_exercise_successful_when_accessed_by_admin() throws AccessDeniedException {
        //Arrange
        setupMocksForUser(ADMIN_1);
        EXERCISE_1.setName("Modified Exercise 1");
        when(mockExerciseDAO.updateExercise(EXERCISE_1)).thenReturn(true);
        when(mockExerciseDAO.getExerciseById(EXERCISE_1.getExerciseId())).thenReturn(EXERCISE_1);

        //Act
        boolean isUpdated = exServe.updateExercise(EXERCISE_1, principal);
        Exercise updated = exServe.getExerciseById(EXERCISE_1.getExerciseId());

        //Assert
        Assert.assertTrue("update method in DAO should return true", isUpdated);
        assertExercisesMatch("Record should be correctly updated", EXERCISE_1, updated);

    }

    @Test(expected = AccessDeniedException.class)
    public void updating_exercise_by_user_should_throw_access_denied() throws AccessDeniedException {
        //Arrange
        setupMocksForUser(USER_1);
        EXERCISE_1.setName("Modified Exercise 1");
        Mockito.lenient().when(mockExerciseDAO.updateExercise(EXERCISE_1)).thenReturn(true);

        //Act
        boolean isUpdated = exServe.updateExercise(EXERCISE_1, principal);

        //Assert - passes if exception thrown. Handled by test annotation
    }

    @Test(expected = AccessDeniedException.class)
    public void updating_exercise_without_authentication_should_throw_access_denied() throws AccessDeniedException {
        //Arrange
        principal = null;
        EXERCISE_1.setName("Modified Exercise 1");
        Mockito.lenient().when(mockExerciseDAO.updateExercise(EXERCISE_1)).thenReturn(true);

        //Act
        boolean isUpdated = exServe.updateExercise(EXERCISE_1, principal);

        //Assert - passes if exception thrown. Handled by test annotation
    }

    //Helper methods
    private void assertExercisesMatch(String message, Exercise expected, Exercise actual) {
        Assert.assertEquals("Ids should match", expected.getExerciseId(), actual.getExerciseId());
        Assert.assertEquals("names should match", expected.getName(), actual.getName());

    }

    private void setupMocksForUser(User user) {
        String username = user.getUsername();
        when(principal.getName()).thenReturn(username);
        when(mockUserDao.getByUsername(username)).thenReturn(user);
    }

}
