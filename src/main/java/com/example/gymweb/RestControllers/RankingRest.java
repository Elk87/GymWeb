package com.example.gymweb.RestControllers;

import com.example.gymweb.Entities.Ranking;
import com.example.gymweb.Services.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RankingRest {
    @Autowired
    RankingService rankingService;
    //this code is to create ranking
    @PostMapping("/")
    public ResponseEntity<Ranking> createRanking(@RequestBody Ranking ranking) {
        Ranking createdRanking = rankingService.createRanking(ranking);
        return new ResponseEntity<>(createdRanking, HttpStatus.CREATED);
    }
    //this delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRanking(@PathVariable Long id) {
        rankingService.deleteRanking(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Ranking> getRanking(@PathVariable Long id) {
        Ranking ranking = rankingService.getRanking(id);//falta ver esto
        if (ranking == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ranking);
    }
    //to get all rankings
    @GetMapping
    public ResponseEntity<Collection<Ranking>> getAllRankings() {
        Collection<Ranking> rankings = rankingService.getRanking();
        return ResponseEntity.ok(rankings);
    }
}


