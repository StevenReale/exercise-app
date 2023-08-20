package com.portfolio.exerciseapp.dao;

import com.portfolio.exerciseapp.model.Event;
import com.portfolio.exerciseapp.model.Workout;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class JdbcEventDaoTests extends BaseDaoTests {

    private final int EXERCISE_1_ID = 1;
    private final int USER_1_ID = 101;
    private final LocalDate JULY_3 = LocalDate.parse("2023-07-03");

    private List<Workout> WORKOUT_LIST_1;
    private List<Workout> WORKOUT_LIST_2;
    private List<Workout> WORKOUT_LIST_3;

    private Event EVENT_1;
    private Event EVENT_2;
    private Event EVENT_3;
    private Event EVENT_4;

    private List<Event> ALL_EVENTS;
    private List<Event> USER_1_EVENTS;
    private List<Event> JULY_3_EVENTS;
    private List<Event> EXERCISE_1_EVENTS;

    private JdbcEventDao jdbcEventDao;
    private WorkoutDAO workoutDAO;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        workoutDAO = new JdbcWorkoutDAO(jdbcTemplate);
        jdbcEventDao = new JdbcEventDao(jdbcTemplate, workoutDAO);

        WORKOUT_LIST_1 = Arrays.asList(workoutDAO.getWorkoutById(1), workoutDAO.getWorkoutById(2));
        WORKOUT_LIST_2 = Arrays.asList(workoutDAO.getWorkoutById(1), workoutDAO.getWorkoutById(3));
        WORKOUT_LIST_3 = Arrays.asList(workoutDAO.getWorkoutById(2), workoutDAO.getWorkoutById(3));

        EVENT_1 = new Event(1, USER_1_ID, WORKOUT_LIST_1, JULY_3);
        EVENT_2 = new Event(2, USER_1_ID, WORKOUT_LIST_1, LocalDate.parse("2023-07-04"));
        EVENT_3 = new Event(3, 102, WORKOUT_LIST_2, JULY_3);
        EVENT_4 = new Event(4, 102, WORKOUT_LIST_3, JULY_3);

        ALL_EVENTS = Arrays.asList(EVENT_1, EVENT_2, EVENT_3, EVENT_4);
        USER_1_EVENTS = Arrays.asList(EVENT_1, EVENT_2);
        JULY_3_EVENTS = Arrays.asList(EVENT_1, EVENT_3, EVENT_4);
        EXERCISE_1_EVENTS = Arrays.asList(EVENT_1, EVENT_2, EVENT_3);
    }

    @Test
    public void get_event_by_id_returns_correct_event() {

        //Act
        Event event1 = jdbcEventDao.getEventById(EVENT_1.getEventId());
        Event event10 = jdbcEventDao.getEventById(10);

        //Assert
        assertEventsMatch("Events should match", EVENT_1, event1);
        Assert.assertNull("Event with id=10 should be null", event10);

    }

    @Test
    public void get_all_events_produces_full_list_of_correct_events() {

        //Act
        List<Event> allEvents = jdbcEventDao.getAllEvents();

        //Assert
        Assert.assertEquals("List lengths should match", ALL_EVENTS.size(), allEvents.size());
        assertEventsMatch("First elements should match", ALL_EVENTS.get(0), allEvents.get(0));
        assertEventsMatch("Last elements should match", ALL_EVENTS.get(ALL_EVENTS.size()-1), allEvents.get(allEvents.size()-1));

    }

    @Test
    public void get_events_by_user_id_returns_correct_list() {

        //Act
        List<Event> user1Events = jdbcEventDao.getAllEventsByUser(USER_1_ID);

        //Assert
        Assert.assertEquals("List lengths should match", USER_1_EVENTS.size(), user1Events.size());
        assertEventsMatch("First elements should match", USER_1_EVENTS.get(0), user1Events.get(0));
        assertEventsMatch("Last elements should match", USER_1_EVENTS.get(USER_1_EVENTS.size()-1), user1Events.get(user1Events.size()-1));
    }

    @Test
    public void get_events_by_date_returns_correct_list() {

        //Act
        List<Event> july3Events = jdbcEventDao.getAllEventsByDate(JULY_3);

        //Assert
        Assert.assertEquals("List lengths should match", JULY_3_EVENTS.size(), july3Events.size());
        assertEventsMatch("First elements should match", JULY_3_EVENTS.get(0), july3Events.get(0));
        assertEventsMatch("Last elements should match", JULY_3_EVENTS.get(JULY_3_EVENTS.size()-1), july3Events.get(july3Events.size()-1));

    }

    @Test
    public void get_events_by_exercise_returns_correct_list() {

        //Act
        List<Event> exercise1Events = jdbcEventDao.getAllEventsByExercise(EXERCISE_1_ID);

        //Assert
        Assert.assertEquals("List lengths should match", EXERCISE_1_EVENTS.size(), exercise1Events.size());
        assertEventsMatch("First elements should match", EXERCISE_1_EVENTS.get(0), exercise1Events.get(0));
        assertEventsMatch("Last elements should match", EXERCISE_1_EVENTS.get(EXERCISE_1_EVENTS.size()-1), exercise1Events.get(exercise1Events.size()-1));

    }

    @Test
    public void created_event_reflected_in_database() {
        //Arrange
        Event createdEvent = new Event();
        createdEvent.setUserId(102);
        createdEvent.setWorkouts(WORKOUT_LIST_1);
        createdEvent.setDate(LocalDate.parse("2023-07-05"));

        //Act
        Event createdEventFromDB = jdbcEventDao.createEvent(createdEvent);
        createdEvent.setEventId(createdEventFromDB.getEventId());

        //Assert
        assertEventsMatch("Created event in database should match created event", createdEvent, createdEventFromDB);
    }

    @Test
    public void updated_event_reflected_in_database() {
        //Arrange
        EVENT_1.setUserId(102);
        EVENT_1.setWorkouts(WORKOUT_LIST_2);
        EVENT_1.setDate(LocalDate.parse("2023-07-05"));

        //Act
        boolean isUpdated = jdbcEventDao.updateEvent(EVENT_1);
        Event updatedEvent = jdbcEventDao.getEventById(EVENT_1.getEventId());

        //Assert
        Assert.assertTrue("update query should return true", isUpdated);
        assertEventsMatch("event should be updated in database", EVENT_1, updatedEvent);
    }

    public void deleted_event_does_not_appear_in_database() {
        //Arrange
        int event4Id = EVENT_4.getEventId();

        //Act
        jdbcEventDao.deleteEvent(event4Id);
        Event event4 = jdbcEventDao.getEventById(event4Id);
        Event event3 = jdbcEventDao.getEventById(EVENT_3.getEventId());

        //Assert
        Assert.assertNull("deleted object should return null", event4);
        assertEventsMatch("object not deleted should remain in database", EVENT_3, event3);
    }

    private void assertEventsMatch(String message, Event expected, Event actual) {
        List<Workout> expectedWorkouts = expected.getWorkouts();
        List<Workout> actualWorkouts = actual.getWorkouts();
        int expectedWorkoutsLength = expectedWorkouts.size();
        int actualWorkoutsLength = actualWorkouts.size();

        Assert.assertEquals(message + ": ids should match", expected.getEventId(), actual.getEventId());
        Assert.assertEquals(message + ": users should match", expected.getUserId(), actual.getUserId());
        Assert.assertEquals(message + ": workout lists should match in length", expectedWorkoutsLength, actualWorkoutsLength);
        Assert.assertEquals(message + ": workout ids for first workout should match", expectedWorkouts.get(0).getWorkoutId(), actualWorkouts.get(0).getWorkoutId());
        Assert.assertEquals(message + ": workout ids for last workout should match", expectedWorkouts.get(expectedWorkoutsLength-1).getWorkoutId(), actualWorkouts.get(actualWorkoutsLength-1).getWorkoutId());
        Assert.assertEquals(message + ": dates should match", expected.getDate(), actual.getDate());
    }

}
