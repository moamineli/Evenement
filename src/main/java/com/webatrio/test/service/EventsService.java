package com.webatrio.test.service;

import com.webatrio.test.models.Events;
import com.webatrio.test.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

public interface EventsService {
    Events addEvent(Events events);
    Events editEvent(Events events);
    Events getEventById(Long id);

    Events cancelEvent(Long eventId);

    Events addUserToEvent(Long userId, Long eventId);

    Events deleteUserFromEvent(Long userId, Long eventId);


    List<Events> findEvents( );

    List<Events> findEventsBylieu(String lieu);


    Set<User> findUsersByEvent(Long id);

    List<Events> getEventsForUser(Long userId);
}
