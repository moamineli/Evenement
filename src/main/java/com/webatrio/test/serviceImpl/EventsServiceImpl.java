package com.webatrio.test.serviceImpl;

import com.webatrio.test.models.Events;
import com.webatrio.test.models.User;
import com.webatrio.test.repository.EventsRepository;
import com.webatrio.test.service.EventsService;
import com.webatrio.test.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EventsServiceImpl implements EventsService  {
    private final EventsRepository repository;
    private final UserService userDetailsService;

    public EventsServiceImpl(EventsRepository repository, UserService userDetailsService) {
        this.repository = repository;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Events addEvent(Events events) {
        events.setAnnuler(false);
     Events result=Optional.of(events).map(repository::save).orElse(null);
     return result;
    }

    @Override
    public Events editEvent(Events events) {
        events.setAnnuler(true);
        Events result=Optional.of(events).map(repository::save).orElse(null);
        return result;
    }

    @Override
    public Events getEventById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Events cancelEvent(Long eventId) {
        Events event = repository.findById(eventId).orElse(null);
        if (event != null) {
            event.setAnnuler(true);
            return repository.save(event);
        }
        return null;
    }
    @Override
    public Events addUserToEvent(Long userId, Long eventId) {
        Events event = repository.findById(eventId).orElse(null);
        User user = userDetailsService.findUserById(userId);
       List<User> u = event.getListeUser().stream().filter(user1 -> user1.equals(user)).collect(Collectors.toList());
      if (u.isEmpty()){
        event.setCapacite(event.getCapacite()-1);
        event.getListeUser().add(user);
      }
      else  {
          throw new IllegalArgumentException("User with id " + userId + " is already added to event with id " + eventId);
      }
        return repository.save(event);

    }
    @Override
    public Events deleteUserFromEvent(Long userId, Long eventId) {
        Events event = repository.findById(eventId).orElse(null);
        User user = userDetailsService.findUserById(userId);
        List<User> u = event.getListeUser().stream().filter(user1 -> user1.equals(user)).collect(Collectors.toList());
        if (!u.isEmpty()){
        event.setCapacite(event.getCapacite()+1);
        event.getListeUser().remove(user);
        } else  {
            throw new IllegalArgumentException("User with id " + userId + " is not added to event with id " + eventId);
        }
        return repository.save(event);

    }
    @Override
    public Page<Events> findEvents(Integer page, Integer size) {
        Pageable p = PageRequest.of(page,size);
        return repository.findAllByAnnulerIsFalse(p);
    }

    @Override
    public List<Events> findEventsBylieu(String lieu) {
        return repository.findAllByLieuLike("%" + lieu + "%").stream().filter(events -> events.getAnnuler().equals(false)).collect(Collectors.toList());
    }
    @Override
    public Set<User> findUsersByEvent(Long id) {
        return repository.findById(id).orElse(null).getListeUser();
    }
    @Override
    public List<Events> getEventsForUser(Long userId) {
        User user = userDetailsService.findUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found with id: " + userId);
        }
        return repository.findByListeUserContaining(user);
    }

}
