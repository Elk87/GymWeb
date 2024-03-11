package com.example.gymweb.Controllers;

import com.example.gymweb.Entities.Lesson;
import com.example.gymweb.Entities.User;
import com.example.gymweb.Services.LessonsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.Collection;

@Controller
public class LessonsController {
    @Autowired
    LessonsService lessonsService;
    //Show all existing lessons
    @GetMapping("/lessons")
    public String showLessons(Model model){
        Collection<Lesson> lessons = lessonsService.getLessons();
        if(lessons.isEmpty()){
            model.addAttribute("noLessons", "There are no lessons available");
        }else{
            model.addAttribute("Lessons", lessons);
        }
        return "lessons";
    }
    //Show all lessons to de admin page
    @GetMapping("/admin")
    public String showLessonsAdmin(Model model){
        Collection<Lesson> lessons = lessonsService.getLessons();
        if(lessons.isEmpty()){
            model.addAttribute("noLessons", "There are no lessons available");
        }else{
            model.addAttribute("Lessons", lessons);
        }
        return "admin";
    }
    //Show an exiting lesson using his ID
    @GetMapping("/lessons/{id}")
    public String showLessons(Model model, @PathVariable long id){
       Lesson lesson = lessonsService.getLessonById(id);
        if(lesson==null){
            model.addAttribute("noLessons", "There are no lessons available");
        }else{
            model.addAttribute("Lessons", lesson);
        }
        return "lessons";
    }
    //admin can add lessons
    @PostMapping("/admin/add/Lesson")
    public String addLesson(Model model,
                            @RequestParam String teacher,
                            @RequestParam LocalTime startTime,
                            @RequestParam LocalTime finishTime,
                            @RequestParam String sport) {

        Lesson lesson = new Lesson();
        lesson.setTeacher(new User(teacher));
        lesson.setStartTime(startTime);
        lesson.setFinishTime(finishTime);
        lesson.setSport(sport);
        lessonsService.addLesson(lesson);

        Collection<Lesson> lessons = lessonsService.getLessons();
        if (lessons.isEmpty()) {
            model.addAttribute("noLessons", "There are no lessons available");
        } else {
            model.addAttribute("Lessons", lessons);
        }

        return "admin";
    }

    //admin can delete some classes their ID
    @RequestMapping(value = "/admin/deleteLesson/{id}", method = RequestMethod.POST)
    public String deleteLesson(Model model, @PathVariable long id){
        lessonsService.deleteLessonById(id);
        Collection<Lesson> lessons = lessonsService.getLessons();
        if(lessons.isEmpty()){
            model.addAttribute("noLessons", "There are no lessons available");
        } else {
            model.addAttribute("Lessons", lessons);
        }
        return "admin";
    }
    //update an exiting lesson

    @PostMapping("/updateLesson/{id}")
    public String updateLesson(Model model, @ModelAttribute Lesson lesson, @PathVariable String id){
        long lessonId = Long.parseLong(id);
        lessonsService.updateLesson(lessonId, lesson);
        Collection<Lesson> lessons = lessonsService.getLessons();
        if(lessons.isEmpty()){
            model.addAttribute("noLessons", "There are no lessons available");
        } else {
            model.addAttribute("Lessons", lessons);
        }

        return "admin";
    }

}

