package com.example.gymweb.Managers;

import com.example.gymweb.Entities.Ranking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class RankingManager {
    @Autowired
    UserManager userManager;
    private final Map<Long, Ranking> rankings = new HashMap<>(); //map where key-long and ranking-value
    private final AtomicLong Id = new AtomicLong();//this attribute is for to generate id
    //create ranking (comments)
    public RankingManager(UserManager userManager){
        this.userManager = userManager;
        createRanking("Clases variadas y profesores entregados");
        createRanking("Poco disponibilidad de horarios ");
    }
    public Ranking createRanking(String comment) {
        long id = Id.incrementAndGet();
        Ranking ranking = new Ranking();
        ranking.setId(id);
        ranking.setUser(userManager.getUser(1));
        ranking.setComment(comment);
        rankings.put(id, ranking);
        return ranking;
    }
    //delete a ranking
    public void deleteRanking(long id) {
        rankings.remove(id);
    }
    // look for the comment and take it
    public Collection<Ranking> getRanking() {
        return rankings.values();
    }
    public Ranking getRankingById(long id){
        return rankings.get(id);
    }

    public Ranking updateRanking(long id, String comment) {
        Ranking r;
        if (!rankings.containsKey(id)) {
            r = null;
        }else {
            r=rankings.get(id);
            r.setComment(comment);
            r.setUser(userManager.getUser(1));
        }
        return r;}


}
