package com.webatrio.test.service;

import com.webatrio.test.models.Events;
import com.webatrio.test.models.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface EventsService {
    Events addEvent(Events events);
    Events getEventById(Long id);
    Events cancelEvent(Long eventId);
    Events addUserToEvent(Long userId, Long eventId);
    Events deleteUserFromEvent(Long userId, Long eventId);
    Page<Events> findEvents(Integer page, Integer size);
    Page<Events> findEventsBylieu(String lieu, Integer page, Integer size);
    List<User> findUsersByEvent(Long id);
    Page<Events> getEventsForUser(Long userId, Integer page, Integer size);
}
