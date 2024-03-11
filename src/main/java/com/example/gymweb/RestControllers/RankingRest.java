package com.example.gymweb.RestControllers;

import com.example.gymweb.Entities.Lesson;
import com.example.gymweb.Entities.Ranking;
import com.example.gymweb.Services.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;


@RestController
@RequestMapping("/api")
public class RankingRest {
    @Autowired
    RankingService rankingService;
    //create a ranking using a comment, this comment is associated to the existing user
    @PostMapping("/ranking")
    public ResponseEntity<Ranking> createRanking(@RequestParam String comment) {
        Ranking ranking=rankingService.createRanking(comment);
        return new ResponseEntity<>(ranking, HttpStatus.CREATED);
    }
    //delete a ranking using his ID
    @DeleteMapping("/ranking/{id}")
    public ResponseEntity<?> deleteRanking(@PathVariable long id) {
        Ranking ranking = rankingService.getRankingById(id);
        if(ranking==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        rankingService.deleteRanking(id);
        return new ResponseEntity<>(ranking, HttpStatus.OK);
    }
    //show a ranking using his ID
    @GetMapping("/ranking/{id}")
    public ResponseEntity<Ranking> getRankingById(@PathVariable long id) {
       Ranking ranking= rankingService.getRankingById(id);
        if (ranking == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ranking, HttpStatus.OK);
    }
    //to get all rankings
    @GetMapping("/ranking")
    public ResponseEntity<Collection<Ranking>> getAllRankings() {
        Collection<Ranking> rankings = rankingService.getRanking();
        return new ResponseEntity<>(rankings, HttpStatus.OK);

    }
    //update an existing ranking using his ID
    @PutMapping("/ranking/{id}")
    public ResponseEntity<Ranking> updateRanking(@RequestParam String comment, @PathVariable long id, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Ranking ranking=rankingService.updateRanking(id,comment);
        return new ResponseEntity<>(ranking,HttpStatus.OK);
    }

}

