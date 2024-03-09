package com.example.gymweb.Controllers;

import com.example.gymweb.Entities.Lesson;
import com.example.gymweb.Services.LessonsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

//this code is for classes
@Controller
public class LessonsController {
    @Autowired
    LessonsService lessonsService;
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
    @GetMapping("/training")
    public String showTrainnig(Model model){
        return "training";
    }
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
    @GetMapping("/admin/add")
    public String add(){
        return "editLessons";
    }
    //admin can add classes
    @GetMapping("/admin/add/Lesson")
    public String addLesson(Model model, Lesson lesson){
        lessonsService.addLesson(lesson);
        Collection<Lesson> lessons = lessonsService.getLessons();
        if(lessons.isEmpty()){
            model.addAttribute("noLessons", "There are no lessons available");
        }else{
            model.addAttribute("Lessons", lessons);
        }
        return "admin";
    }
    //admin can delete some classes
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

    @PostMapping("/updateLesson/{id}")
    public String updateLesson(Model model, Lesson lesson, @PathVariable long id){
        lessonsService.updateBook(id,lesson);
        Collection<Lesson> lessons = lessonsService.getLessons();
        if(lessons.isEmpty()){
            model.addAttribute("noLessons", "There are no lessons available");
        }else{
            model.addAttribute("Lessons", lessons);
        }
        return "admin";
    }
}

