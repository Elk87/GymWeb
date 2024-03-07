package com.example.gymweb.Controllers;
import com.example.gymweb.Entities.User;
import com.example.gymweb.Entities.Lesson;
import com.example.gymweb.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


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

    //user reserves classes
    @PostMapping("/bookclass")
    public String bookLesson(@RequestParam long lessonId) {
        User user = userService.getUser(1);
        Lesson lesson = userService.getLessonById(1,1);
        userService.bookClass(user.getId(), lesson);
        return "redirect:/profile";
    }
    @PostMapping("/register")
    public String addUser(User newUser){
        userService.addUser(newUser);
        return "registerSuccessful";
    }
}
