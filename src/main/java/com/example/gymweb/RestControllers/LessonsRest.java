package com.example.gymweb.RestControllers;
import com.example.gymweb.Entities.Lesson;
import com.example.gymweb.Services.LessonsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;

@RestController
@RequestMapping("/api")

public class LessonsRest {
    @Autowired
    LessonsService lessonsService;
    //to get lesson by id
    @GetMapping("/{id}")
    public ResponseEntity<Lesson> getLesson(@PathVariable long id) {
        Lesson lesson = lessonsService.getLessonById(id);
        if (lesson != null) {
            return new ResponseEntity<>(lesson, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    //to get all lessons
    @GetMapping("/")
    public ResponseEntity<Collection<Lesson>> getAllLessons() {
        Collection<Lesson> lessons = lessonsService.getLessons();
        return new ResponseEntity<>(lessons, HttpStatus.OK);
    }
    //to create a lesson
    @PostMapping("/")
    public ResponseEntity<Lesson> createLesson(@RequestBody Lesson lesson) {
        Lesson createdLesson = lessonsService.addLesson(lesson);
        return new ResponseEntity<>(createdLesson, HttpStatus.CREATED);
    }
    //this code is for to delete a lesson
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLesson(@PathVariable long id) {
        lessonsService.deleteLessonById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
