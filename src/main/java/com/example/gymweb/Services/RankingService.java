package com.example.gymweb.Services;

import com.example.gymweb.Entities.Ranking;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class RankingService {
    private final Map<Long, Ranking> rankings = new HashMap<>(); //map where key-long and ranking-value
    private final AtomicLong Id = new AtomicLong();//this attribute is for to generate id
    //create ranking (comments)
    public Ranking createRanking(Ranking ranking) {
        long id = Id.incrementAndGet();
        ranking.setId(id);
        rankings.put(id, ranking);
        return ranking;
    }
    //delete a ranking
    public void deleteRanking(Long id) {
        rankings.remove(id);
    }
    // look for the comment and take it
    public Collection<Ranking> getRanking() {
        return rankings.values();
    }




}
