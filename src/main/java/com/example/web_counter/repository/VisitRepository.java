package com.example.web_counter.repository;

import com.example.web_counter.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {
    List<Visit> findAllByDateLessThanEqualAndDateGreaterThanEqual(Date to, Date from);
}
