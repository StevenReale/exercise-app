package com.portfolio.exerciseapp.service;

import com.portfolio.exerciseapp.dao.EventDao;
import com.portfolio.exerciseapp.model.Event;
import org.springframework.stereotype.Component;

@Component
public class EventService {

    private EventDao eventDao;

    public EventService (EventDao eventDao) {
        this.eventDao = eventDao;
    }

    /**
     * Gets event by ID.
     */
    public Event getEventById(int id) {return eventDao.getEventById(id); }
}
