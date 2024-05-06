package com.example.gymweb.Controllers;

import com.example.gymweb.Entities.Lesson;
import com.example.gymweb.Entities.User;
import com.example.gymweb.Secure.Secure;
import com.example.gymweb.Services.LessonsService;
import com.example.gymweb.Services.UploadFileService;
import com.example.gymweb.Services.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
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
import java.security.Principal;
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

    @ModelAttribute
    public void addAttributes(Model model, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        if (principal != null) {
            model.addAttribute("logged", true);
            model.addAttribute("userName", userService.findNameByEmail(principal.getName()));
            model.addAttribute("admin", request.isUserInRole("ADMIN"));

        } else {
            model.addAttribute("logged", false);
        }
    }

    //Show all existing lessons
    @GetMapping("/lesson")
    public String showLessons(Model model) {
        Collection<Lesson> lessons = lessonsService.getLessons();
        if (lessons.isEmpty()) {
            model.addAttribute("noLessons", "There are no lessons available");
        } else {
            model.addAttribute("Lessons", lessons);
        }
        return "lessons";
    }

    //Show all lessons to the admin page
    @GetMapping("/admin")
    public String showLessonsAdmin(Model model) {
        Collection<Lesson> lessons = lessonsService.getLessons();
        if (lessons.isEmpty()) {
            model.addAttribute("noLessons", "There are no lessons available");
        } else {
            model.addAttribute("Lessons", lessons);
        }
        return "admin";
    }

    //Show an existing lesson using its ID
    @GetMapping("/lessons/{id}")
    public String showLessons(Model model, @PathVariable long id) {
        Lesson lesson = lessonsService.getLessonById(id);
        if (lesson == null) {
            model.addAttribute("noLessons", "There are no lessons available");
        } else {
            model.addAttribute("Lessons", lesson);
        }
        return "lessons";
    }

    //Admin can add lessons
    @PostMapping("/admin/add/Lesson")
    public String addLesson(Model model,
                            @RequestParam String teacher,
                            @RequestParam LocalTime startTime,
                            @RequestParam LocalTime finishTime,
                            @RequestParam String sport) {
        Optional<User> u = userService.findByName(Secure.deleteDangerous(teacher));
        if (u.isPresent()) {
            Lesson lesson = new Lesson();
            lesson.setTeacher(userService.findByName(Secure.deleteDangerous(teacher)).get());
            lesson.setStartTime(startTime);
            lesson.setFinishTime(finishTime);
            lesson.setSport(Secure.deleteDangerous(sport));
            lessonsService.addLesson(lesson);
        }
        Collection<Lesson> lessons = lessonsService.getLessons();
        if (lessons.isEmpty()) {
            model.addAttribute("noLessons", "There are no lessons available");
        } else {
            model.addAttribute("Lessons", lessons);
        }

        return "redirect:/admin";
    }

    //Admin can delete some classes by their ID
    @RequestMapping(value = "/admin/deleteLesson/{id}", method = RequestMethod.POST)
    public String deleteLesson(Model model, @PathVariable long id) {
        lessonsService.deleteLessonById(id);
        Collection<Lesson> lessons = lessonsService.getLessons();
        if (lessons.isEmpty()) {
            model.addAttribute("noLessons", "There are no lessons available");
        } else {
            model.addAttribute("Lessons", lessons);
        }
        return "redirect:/admin";
    }

    //Update an existing lesson
    @PostMapping("/admin/updateLesson/{id}")
    public String updateLesson(Model model, @RequestParam String teacher, @RequestParam LocalTime startTime, @RequestParam LocalTime finishTime, @RequestParam String sport, @PathVariable Long id) {
        Optional<User> user = userService.findByName(Secure.deleteDangerous(teacher));
        if (user.isPresent()) {
            User t = user.get();
            Lesson newLesson = new Lesson(t, startTime, finishTime, Secure.deleteDangerous(sport));
            lessonsService.updateLesson(id, newLesson);
        }

        Collection<Lesson> lessons = lessonsService.getLessons();
        if (lessons.isEmpty()) {
            model.addAttribute("noLessons", "There are no lessons available");
        } else {
            model.addAttribute("Lessons", lessons);
        }

        return "redirect:/admin";
    }
}
