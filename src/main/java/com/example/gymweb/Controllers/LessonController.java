package com.example.gymweb.Controllers;
import com.example.gymweb.Entities.User;
import com.example.gymweb.Entities.Lesson;
import com.example.gymweb.Services.LessonService;
import com.example.gymweb.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LessonController {
    @Autowired
    UserService userService;
    LessonService lessonService;

    @GetMapping
    public String showTimetable(Model model){
        User user=userService.getUser(1);
        Iterable<Lesson>avaliableLesson=lessonService.getAvailableLessons();
        model.addAttribute("user",user);
        model.addAttribute("lessons",avaliableLesson);
        return "timetable";
    }


}
