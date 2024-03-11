package com.example.gymweb.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    @GetMapping("/")
    public String showHome(){
        return "index";
    }
    @GetMapping("/login")
    public String showLogin(){
        return "login";
    }
    @GetMapping("/register")
    public String showRegister(){
        return "register";
    }
    @GetMapping("/training")
    public String showTrainnig(Model model){
        return "training";
    }
    @GetMapping("/admin/add")
    public String addLesson(){
        return "editLessons";
    }
    @GetMapping("/ranking")
    public String viewRanking() {
        return "ranking";//this show comments and opinions
    }


}
