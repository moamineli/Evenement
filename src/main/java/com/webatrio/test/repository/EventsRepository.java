package com.webatrio.test.repository;

import com.webatrio.test.models.Events;
import com.webatrio.test.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventsRepository extends PagingAndSortingRepository<Events, Long> {

    Page<Events> findAllByLieuLikeAndAnnulerIsFalseAndDateDebutGreaterThan(String lieu,Pageable p, LocalDateTime date);
    Page<Events> findAllByAnnulerIsFalseAndDateDebutGreaterThan(Pageable p, LocalDateTime date);
    Page<Events> findByListeUserContainingAndDateDebutGreaterThan(User u,Pageable p,LocalDateTime date);
}
