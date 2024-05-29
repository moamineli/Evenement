package com.webatrio.test.controllers;

import com.webatrio.test.utils.RestPage;
import com.webatrio.test.models.Events;
import com.webatrio.test.models.User;
import com.webatrio.test.service.EventsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Api(value = "testWebatrioEvent", description = "Events")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/events")
public class EventsController {

    private final EventsService eventsService;

    public EventsController(EventsService eventsService) {
        this.eventsService = eventsService;
    }

    @ApiOperation(value = "Update an Event")
    @PutMapping("/update")
    @PreAuthorize("hasRole('ROLE_ORGANISATEUR')")

    public Events updateEvent(@Valid @RequestBody Events events) {
        if (events.getId() == null) {
            throw new IllegalArgumentException("Key does not Exist");
        }
        try {
            Events result = eventsService.editEvent(events);
            return result;
        } catch (DuplicateKeyException e) {
            throw new IllegalArgumentException("Key already Exist");
        }
    }

    @ApiOperation(value = "Add a new Event")
    @PreAuthorize("hasRole('ROLE_ORGANISATEUR')")
    @PostMapping("/add")
    public Events addEvent(@Valid @RequestBody Events events) {
        try {
            Events result = eventsService.addEvent(events);
            return result;
        } catch (DuplicateKeyException e) {
            throw new IllegalArgumentException("Key already Exist");
        }
    }

    @ApiOperation(value = "Get Events with Pagination")
    @PostMapping()
    public Page<Events> getEventsWithPagination(@RequestBody Map<String, Integer> paginationParams ) {
        int page = paginationParams.getOrDefault("page", 0);
        int size = paginationParams.getOrDefault("size", 10);
        Page<Events> events = eventsService.findEvents(page,size);
            return events;
    }

    @ApiOperation(value = "Get Events with Pagination")
    @GetMapping("/getById")
    public Events getEventById(@PathVariable Long id) {
        return eventsService.getEventById(id);
    }
    @ApiOperation(value = "Get Events by lieu")
    @PostMapping("/byLieu/{lieu}")
    public Page<Events> getEventsByLieu(@PathVariable String lieu, @RequestBody Map<String, Integer> paginationParams) {
        int page = paginationParams.getOrDefault("page", 0);
        int size = paginationParams.getOrDefault("size", 10);
        Page<Events> eventsByLieu = eventsService.findEventsBylieu(lieu,page,size);
        return eventsByLieu;
    }
    @PreAuthorize("hasRole('ROLE_ORGANISATEUR')")
    @PutMapping("/cancel/{id}")
    public ResponseEntity<Events> cancelEvent(@PathVariable Long id) {
        Events canceledEvent = eventsService.cancelEvent(id);
        if (canceledEvent != null) {
            return ResponseEntity.ok(canceledEvent);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @ApiOperation(value = "add user to Event")
    @PreAuthorize("hasRole('ROLE_ORGANISATEUR') or hasRole('ROLE_PARTICIPANT') ")
    @PutMapping("/addUserToEvent/{idUser}/{idEvent}")
    public ResponseEntity<Events> addUserToEvent(@PathVariable Long idUser, @PathVariable Long idEvent) {
        if (idUser == null || idEvent == null) {
            return ResponseEntity.badRequest().build();
        }
        Events result = eventsService.addUserToEvent(idUser, idEvent);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }


    @ApiOperation(value = "add user to Event")
    @PreAuthorize("hasRole('ROLE_ORGANISATEUR')")
    @PutMapping("/deleteUserFromEvent/{idUser}/{idEvent}")
    public ResponseEntity<Events> deleteUserFromEvent(@PathVariable Long idUser, @PathVariable Long idEvent) {
        if (idUser == null || idEvent == null) {
            return ResponseEntity.badRequest().build();
        }
        Events result = eventsService.deleteUserFromEvent(idUser, idEvent);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }
    @ApiOperation(value = "Get users by Event")
    @PreAuthorize("hasRole('ROLE_ORGANISATEUR')")
    @PostMapping("/findUsersByEvent/{id}")
    public RestPage<User> findUsersByEventWithPagination(@PathVariable Long id, @RequestBody Map<String, Integer> paginationParams) {
        int page = paginationParams.getOrDefault("page", 0);
        int size = paginationParams.getOrDefault("size", 10);
        Pageable pageable = PageRequest.of(page, size);
        Set<User> usersByEvent = eventsService.findUsersByEvent(id);
        List<User> usersList = new ArrayList<>(usersByEvent);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), usersList.size());
        Page<User> usersPage = new PageImpl<>(usersList.subList(start, end), pageable, usersList.size());
        return new RestPage<>(usersPage);
    }

    @ApiOperation(value = "Get events for user with pagination")
    @PostMapping("/findEventsForUser/{userId}")
    public Page<Events> getEventsForUserWithPagination(@PathVariable Long userId, @RequestBody Map<String, Integer> paginationParams) {
        int page = paginationParams.getOrDefault("page", 0);
        int size = paginationParams.getOrDefault("size", 10);
        try {
            Page<Events> userEvents = eventsService.getEventsForUser(userId,page,size);
                 return userEvents;
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", e);
        }
    }


}
