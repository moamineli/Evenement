package com.webatrio.test.serviceImpl;

import com.webatrio.test.models.Events;
import com.webatrio.test.models.User;
import com.webatrio.test.repository.EventsRepository;
import com.webatrio.test.service.UserService;
import junit.framework.TestCase;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {EventsServiceImpl.class})
public class EventsServiceImplTest extends TestCase {


    @Mock
    private EventsRepository repository;

    @InjectMocks
    private EventsServiceImpl eventsService;

    @Mock
    private UserService userDetailservice;

    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddEvent() {
        Events events = new Events();
        events.setId(1L);

        when(repository.save(events)).thenReturn(events);

        Events result = eventsService.addEvent(events);

        assertNotNull(result);
        assertTrue(result.getAnnuler());
        verify(repository, times(1)).save(events);
    }

    @Test
    public void testEditEvent() {
        Events events = new Events();
        events.setId(1L);
        when(repository.save(events)).thenReturn(events);

        Events result = eventsService.editEvent(events);

        assertNotNull(result);
        assertTrue(result.getAnnuler());
        verify(repository, times(1)).save(events);
    }

    @Test
    public void testCancelEvent() {
        // Given
        Long eventId = 1L;
        Events event = new Events();
        event.setId(eventId);
        when(repository.findById(eventId)).thenReturn(Optional.of(event));
        when(repository.save(event)).thenReturn(event);
        Events result = eventsService.cancelEvent(eventId);
        assertNotNull(result);
        assertTrue(result.getAnnuler()); // Vérifie que le drapeau annuler est défini sur true
        verify(repository, times(1)).findById(eventId); // Vérifie que la méthode findById a été appelée une fois avec l'ID de l'événement
        verify(repository, times(1)).save(event); // Vérifie que la méthode save a été appelée une fois avec l'objet event
    }

    @Test
    public void testAddUserToEvent() {
        Long userId = 1L;
        Long eventId = 1L;
        Events event = new Events();
        event.setId(eventId);
        event.setCapacite(10L);
        event.setListeUser(new HashSet<>());
        User user = new User();
        user.setId(userId);
        when(repository.findById(eventId)).thenReturn(Optional.of(event));
        when(userDetailservice.findUserById(userId)).thenReturn(user);
        eventsService.addUserToEvent(userId, eventId);
//        assertNotNull(result);
//        assertEquals(Optional.of(9), result.getCapacite());
//        assertTrue(result.getListeUser().contains(user));
        verify(repository, times(1)).findById(eventId);
        verify(repository, times(1)).save(event);
    }

    public void testDeleteUserFromEvent() {
    }

    public void testFindEvents() {
    }

    public void testFindEventsBylieu() {
    }

    public void testFindUsersByEvent() {
    }

    public void testGetEventsForUser() {
    }
}
