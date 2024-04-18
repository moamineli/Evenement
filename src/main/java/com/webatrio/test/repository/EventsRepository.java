package com.webatrio.test.repository;

import com.webatrio.test.models.Events;
import com.webatrio.test.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventsRepository extends PagingAndSortingRepository<Events, Long> {

    List<Events> findAllByLieuLike(String lieu);
    Page<Events> findAllByAnnulerIsFalse(Pageable p);
    List<Events> findByListeUserContaining(User u);
}
