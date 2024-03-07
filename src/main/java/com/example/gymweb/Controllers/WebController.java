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
        return "Login";
    }




}