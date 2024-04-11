package com.example.gymweb.Services;
import com.example.gymweb.Secure.Secure;

import com.example.gymweb.Entities.Ranking;
import com.example.gymweb.Entities.User;
import com.example.gymweb.Repositories.RankingRepository;
import com.example.gymweb.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
//import java.util.concurrent.atomic.AtomicLong;

@Service
public class RankingService {
    @Autowired
    RankingRepository rankingRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
   /* private final Map<Long, Ranking> rankings = new HashMap<>(); //map where key-long and ranking-value
    private final AtomicLong Id = new AtomicLong();//this attribute is for to generate id
    //create ranking (comments)*/
    public RankingService(UserService userService,RankingRepository rankingRepository1){
        this.userService=userService;
        this.rankingRepository=rankingRepository1;

    }
    public Ranking createRanking(User user, String comment) {
        String cleanComment = Secure.deleteDangerous(comment);
        Ranking ranking = new Ranking();
        ranking.setUser(user);
        ranking.setComment(cleanComment);
        rankingRepository.save(ranking);
        return ranking;
    }
    //delete a ranking
    public void deleteRanking(long id) {
        rankingRepository.deleteById(id);
    }
    // look for the comment and take it
    public Collection<Ranking> getRanking() {
        return rankingRepository.findAll();
    }
    public Ranking getRankingById(long id){
        return rankingRepository.findRankingById(id);
    }
    public List<Ranking> getRankingByUser(User user){
        return rankingRepository.findRankingByUser(user);
    }

    public Ranking updateRanking(long id, String comment) {
        Optional<Ranking> optionalRanking = rankingRepository.findById(id);
        if (optionalRanking.isPresent()) {
            String cleanComment = Secure.deleteDangerous(comment);
            Ranking rankingToUpdate = optionalRanking.get();
            rankingToUpdate.setComment(cleanComment);
            rankingRepository.save(rankingToUpdate); // Update ranking in database
            return rankingToUpdate;
        } else {
            return null; // Ranking not found
        }
}}
