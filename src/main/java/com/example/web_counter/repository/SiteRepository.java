package com.example.web_counter.repository;

import com.example.web_counter.model.Site;
import com.example.web_counter.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiteRepository extends JpaRepository<Site, Long> {
    Site getByUrl(String url);
}
