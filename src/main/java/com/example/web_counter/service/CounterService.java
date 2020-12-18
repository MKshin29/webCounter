package com.example.web_counter.service;

import com.example.web_counter.model.Site;
import com.example.web_counter.model.User;
import com.example.web_counter.model.Visit;

import java.util.Date;
import java.util.Map;

public interface CounterService {

    Site getSiteByUrl(String url);
    User getUserByUsername(String username);
    // создание события посещения сайта
    void saveVisit(long user_id, long site_id);
    // получение статистики посещений без user_id
    Map<String, Integer> getStatistics(Date from, Date to);
    // получение статистики посещений c user_id
    Map<String, Integer> getStatistics(Date from, Date to, int user_id);
}
