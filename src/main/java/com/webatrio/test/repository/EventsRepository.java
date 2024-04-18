package com.webatrio.test.repository;

import com.webatrio.test.models.Events;
import com.webatrio.test.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventsRepository extends JpaRepository<Events, Long> {

    List<Events> findAllByLieuLike(String lieu);
    List<Events> findByListeUserContaining(User u);
}
