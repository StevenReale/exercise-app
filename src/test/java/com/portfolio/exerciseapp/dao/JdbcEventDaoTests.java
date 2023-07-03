package com.portfolio.exerciseapp.dao;

import com.portfolio.exerciseapp.model.Event;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class JdbcEventDaoTests extends BaseDaoTests {

    private final int USER_1_ID = 101;

    private final Event EVENT_1 = new Event(1, USER_1_ID, 1, LocalDate.parse("2023-07-03"));
    private final Event EVENT_2 = new Event(2, USER_1_ID, 2, LocalDate.parse("2023-07-04"));
    private final Event EVENT_3 = new Event(3, 102, 1, LocalDate.parse("2023-07-03"));
    private final Event EVENT_4 = new Event(4, 102, 3, LocalDate.parse("2023-07-03"));

    private final List<Event> ALL_EVENTS = Arrays.asList(EVENT_1, EVENT_2, EVENT_3, EVENT_4);
    private final List<Event> USER_1_EVENTS = Arrays.asList(EVENT_1, EVENT_2);

    private JdbcEventDao jdbcEventDao;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcEventDao = new JdbcEventDao(jdbcTemplate);
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

    private void assertEventsMatch(String message, Event expected, Event actual) {
        Assert.assertEquals(message + ": ids should match", expected.getEventId(), actual.getEventId());
        Assert.assertEquals(message + ": users should match", expected.getUserId(), actual.getUserId());
        Assert.assertEquals(message + ": workout ids should match", expected.getWorkoutId(), actual.getWorkoutId());
        Assert.assertEquals(message + ": dates should match", expected.getDate(), actual.getDate());
    }

}
