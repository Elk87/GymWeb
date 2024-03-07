package com.example.gymweb.Controllers;
import com.example.gymweb.Entities.User;
import com.example.gymweb.Entities.Lesson;
import com.example.gymweb.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;


@Controller
public class UserController {
    @Autowired
    UserService userService;
    //show profile of user and classes
    @GetMapping("/profile")
    public String viewProfile(Model model) {
        User user = userService.getUser(1);
        model.addAttribute("user", user);
        model.addAttribute("lessons", userService.getLessons(user.getId()));
        return "profile";
    }
    @PostMapping("/login")
    public String login(Model model, @RequestParam String email, @RequestParam String passwd){
        boolean valid= userService.checkLogin(email,passwd);
        boolean error= !(userService.checkLogin(email,passwd));
        model.addAttribute("ok",valid); //Login valid
        model.addAttribute("error",error); //Login invalid
        return "loginResult";
    }

    //user reserves classes
    @PostMapping("/bookclass")
    public String bookLesson(@RequestParam long lessonId) {
        User user = userService.getUser(1);
        Lesson lesson = userService.getLessonById(1,1);
        userService.bookClass(user.getId(), lesson);
        return "redirect:/profile";
    }
    //@PostMapping("/register")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String addUser(User newUser){
        userService.addUser(newUser);
        return "registerSuccessful";
    }
}
