package com.example.gymweb.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class WebController {
    @GetMapping("/")
    public String showHome(){
        return "index";
    }
    @GetMapping("/login")
    public String showLogin(Model model, HttpServletRequest request){
        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        model.addAttribute("token", token.getToken());
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
    @GetMapping("/loginIncorrect")
    public String badLogin(){
        return "loginIncorrect";
    }

}
