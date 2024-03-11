package com.example.gymweb.Controllers;
import ch.qos.logback.core.model.Model;
import com.example.gymweb.Services.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

@Controller

public class RankingController {
    @Autowired
    RankingService rankingService;
    //this code is for to show comments
   /* @GetMapping("/rankings")
    public String showRankings(Model model) {
        model.addAttribute("rankings", rankingService.getRanking());
        return "rankings"; // Retorna la vista "rankings" con la lista de rankings
    }*/

}
