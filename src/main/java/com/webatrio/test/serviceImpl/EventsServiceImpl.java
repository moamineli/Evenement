package com.webatrio.test.serviceImpl;

import com.webatrio.test.models.Events;
import com.webatrio.test.models.User;
import com.webatrio.test.repository.EventsRepository;
import com.webatrio.test.repository.UserRepository;
import com.webatrio.test.service.EventsService;
import com.webatrio.test.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
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

        if (events.getDateFin().isAfter(events.getDateDebut()) && events.getDateDebut().isAfter(LocalDateTime.now())) {
            Events result = Optional.of(events).map(repository::save).orElse(null);
            return result;
        }
        else {

            throw new IllegalArgumentException("false date");

        }

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
          if(findUsersByEvent(eventId).size() < event.getCapacite()){
              event.getListeUser().add(user);
          }
          else {
              throw new IllegalArgumentException("Out of capacity");

          }
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
         event.getListeUser().remove(user);
        } else  {
            throw new IllegalArgumentException("User with id " + userId + " is not added to event with id " + eventId);
        }
        return repository.save(event);

    }
    @Override
    public Page<Events> findEvents(Integer page, Integer size) {
        Pageable p = PageRequest.of(page,size);
        return repository.findAllByAnnulerIsFalseAndDateDebutGreaterThan(p, LocalDateTime.now());
    }

    @Override
    public Page<Events> findEventsBylieu(String lieu, Integer page, Integer size) {
        Pageable p = PageRequest.of(page,size);
        return repository.findAllByLieuLikeAndAnnulerIsFalseAndDateDebutGreaterThan("%" + lieu + "%",p,LocalDateTime.now());
    }
    @Override
    public List<User> findUsersByEvent(Long id) {
        return repository.findById(id)
                .map(Events::getListeUser)
                .orElse(Collections.emptyList());

        //  Solution 2
//       return repository.listUserbyEvents(id)
//                .stream()
//                .map(userId -> userRepository.findById(userId))
//                .filter(Optional::isPresent)
//                .map(Optional::get)
//                .collect(Collectors.toList());
    }
    @Override
    public Page<Events> getEventsForUser(Long userId, Integer page, Integer size) {
        User user = userDetailsService.findUserById(userId);
        Pageable p =  PageRequest.of(page,size);
        if (user == null) {
            throw new IllegalArgumentException("User not found with id: " + userId);
        }
        return repository.findByListeUserContainingAndDateDebutGreaterThan(user,p,LocalDateTime.now());
    }

}
