package com.example.gymweb.Controllers;

import com.example.gymweb.Entities.Lesson;
import com.example.gymweb.Entities.User;
import com.example.gymweb.Services.LessonsService;
import com.example.gymweb.Services.UploadFileService;
import com.example.gymweb.Services.UserService;
import jakarta.annotation.Resource;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Optional;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@Controller
public class LessonsController {
    @Autowired
    LessonsService lessonsService;
    @Autowired
    UserService userService;
     @Autowired
     UploadFileService uploadFileService;
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
        Optional<User> u=userService.findByName(teacher);
        if (u.isPresent()){
            Lesson lesson = new Lesson();
            lesson.setTeacher(userService.findByName(teacher).get());
            lesson.setStartTime(startTime);
            lesson.setFinishTime(finishTime);
            lesson.setSport(sport);
            lessonsService.addLesson(lesson);
        }
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
    public String updateLesson(Model model,@RequestParam String teacher, @ RequestParam LocalTime startTime, @RequestParam LocalTime finishTime,@RequestParam String sport, @PathVariable Long id){
        Optional<User> user = userService.findByName(teacher);
        if (user.isPresent()){
            User t = user.get();
            Lesson newLesson = new Lesson(t,startTime,finishTime,sport);
            lessonsService.updateLesson(id, newLesson);
        }

        Collection<Lesson> lessons = lessonsService.getLessons();
        if(lessons.isEmpty()){
            model.addAttribute("noLessons", "There are no lessons available");
        } else {
            model.addAttribute("Lessons", lessons);
        }

        return "admin";
    }




}

