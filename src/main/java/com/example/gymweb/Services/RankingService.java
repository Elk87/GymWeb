package com.example.gymweb.Services;

import com.example.gymweb.Entities.Lesson;
import com.example.gymweb.Entities.Ranking;
import com.example.gymweb.Entities.User;
import com.example.gymweb.Repositories.RankingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
//import java.util.concurrent.atomic.AtomicLong;

@Service
public class RankingService {
    @Autowired
    UserService userService;
    @Autowired
    RankingRepository rankingRepository;

    //private final Map<Long, Ranking> rankings = new HashMap<>(); //map where key-long and ranking-value
    //private final AtomicLong Id = new AtomicLong();//this attribute is for to generate id
    //create ranking (comments)
    public RankingService(UserService userService){
        this.userService=userService;
        createRanking("Clases variadas y profesores entregados");
        createRanking("Poco disponibilidad de horarios ");
    }
    /*public Ranking createRanking(String comment) {
        long id = Id.incrementAndGet();
        Ranking ranking = new Ranking();
        ranking.setId(id);
        ranking.setUser(userService.getUser(1));
        ranking.setComment(comment);
        rankings.put(id, ranking);
        return ranking;
    }*/
    public Ranking createRanking(String comment) {
        Ranking ranking = new Ranking();
        ranking.setComment(comment);
        ranking.setUser(userService.getUser(1)); // Assuming you fetch the user by ID
        ranking = rankingRepository.save(ranking); // Save ranking to database
        return ranking;
    }
    //delete a ranking
   /* public void deleteRanking(long id) {
        rankings.remove(id);
    }*/
    public void deleteRanking(long id) {
        rankingRepository.deleteRanking(id);
    }
    // look for the comment and take it
    /*public Collection<Ranking> getRanking() {
        return rankings.values();
    }*/
    public Collection<Ranking> getRanking() {
        return rankingRepository.findAll();
    }
    /*public Ranking getRankingById(long id){
        return rankings.get(id);
    }*/
    public Ranking getRankingById(long id) {
        Optional<Ranking> optionalRanking = rankingRepository.findById(id);
        return optionalRanking.orElse(null); // Return null if not found
    }

    /*public Ranking updateRanking(long id, String comment) {
        Ranking r;
        if (!rankings.containsKey(id)) {
            r = null;
        }else {
            r=rankings.get(id);
            r.setComment(comment);
            r.setUser(userService.getUser(1));
        }
        return r;}*/
    public Ranking updateRanking(long id, String comment) {
        Optional<Ranking> optionalRanking = rankingRepository.findById(id);
        if (optionalRanking.isPresent()) {
            Ranking rankingToUpdate = optionalRanking.get();
            rankingToUpdate.setComment(comment);
            rankingRepository.save(rankingToUpdate); // Update ranking in database
            return rankingToUpdate;
        } else {
            return null; // Ranking not found
        }
    }


}
