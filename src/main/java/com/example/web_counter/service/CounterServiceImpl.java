package com.example.web_counter.service;

import com.example.web_counter.model.Site;
import com.example.web_counter.model.User;
import com.example.web_counter.model.Visit;
import com.example.web_counter.repository.SiteRepository;
import com.example.web_counter.repository.UserRepository;
import com.example.web_counter.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CounterServiceImpl implements CounterService {

    private final SiteRepository siteRepository;
    private final UserRepository userRepository;
    private final VisitRepository visitRepository;

    @Autowired
    public CounterServiceImpl(SiteRepository siteRepository, UserRepository userRepository, VisitRepository visitRepository) {
        this.siteRepository = siteRepository;
        this.userRepository = userRepository;
        this.visitRepository = visitRepository;
    }

    @Override
    public Site getSiteByUrl(String url) {
        return siteRepository.getByUrl(url);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    @Override
    public void saveVisit(long user_id, long site_id) {

        Visit visit = new Visit();
        visit.setUser_id(user_id);
        visit.setSite_id(site_id);
        visit.setDate(new Date(System.currentTimeMillis()));

        visitRepository.save(visit);
    }

    @Override
    public Map<String, Integer> getStatistics(Date from, Date to) {

        List<Visit> visits = this.visitRepository.findAllByDateLessThanEqualAndDateGreaterThanEqual(to, from);

        //а. Общее количество посещений за указанный период
        int all_visits = visits.size();

        //словарь где ключ = айди клиента, а значение, количество его упоминаний в БД за период.
        HashMap<Long, Integer> visits_by_users = new HashMap<>();

        for (Visit visit: visits){
            if(visits_by_users.containsKey(visit.getUser_id())){
                int visits_by_this_user = visits_by_users.get(visit.getUser_id());
                visits_by_this_user++;
                visits_by_users.put(visit.getUser_id(), visits_by_this_user);
            }
            else{
                visits_by_users.put(visit.getUser_id(), 0);
            }
        }
        //б. Количество уникальных пользователей за указанный период
        int uniq_users = visits_by_users.keySet().size();

        //с. Количество постоянных пользователей
        int constant_users = 0;
        for (Map.Entry<Long, Integer> entry: visits_by_users.entrySet()){
            if (entry.getValue() >= 10) {
                constant_users++;
            }
        }

        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("all_visits", all_visits);
        resultMap.put("uniq_users", uniq_users);
        resultMap.put("constant_users", constant_users);

        return resultMap;
    }

    @Override
    public Map<String, Integer> getStatistics(Date from, Date to, int user_id) {
        Map<String, Integer> resultMap = getStatistics(from, to);
        resultMap.put("uniq_users", 1);
        return resultMap;
    }
}
