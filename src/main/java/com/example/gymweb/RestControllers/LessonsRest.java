package com.example.gymweb.RestControllers;
import com.example.gymweb.Entities.Lesson;
import com.example.gymweb.Services.LessonsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api")

public class LessonsRest {
    @Autowired
    LessonsService lessonsService;
    //to get lesson by id
    @GetMapping("/lesson/{id}")
    public ResponseEntity<Lesson> getLessonById(@PathVariable long id) {
        Lesson lesson = lessonsService.getLessonById(id);
        if (lesson != null) {
            return new ResponseEntity<>(lesson, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    //to get all lessons
    @GetMapping("/lesson")
    public ResponseEntity<Collection<Lesson>> getAllLessons() {
        Collection<Lesson> lessons = lessonsService.getLessons();
        return new ResponseEntity<>(lessons, HttpStatus.OK);
    }
    //to create a lesson
    @PostMapping("/lesson")
    public ResponseEntity<Lesson> createLesson(@RequestBody Lesson lesson) {
        lessonsService.addLesson(lesson);
        return new ResponseEntity<>(lesson, HttpStatus.CREATED);
    }
    //this code is for deleting a lesson
    @DeleteMapping("/lesson/{id}")
    public ResponseEntity<?> deleteLesson(@PathVariable long id) {
        Lesson lesson = lessonsService.getLessonById(id);
        if (lesson == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        lessonsService.deleteLessonById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //update an existing lesson giving his ID
    @PutMapping("/lesson/{id}")
    public ResponseEntity<Lesson> updateLesson(@RequestBody Lesson lesson,@PathVariable long id, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        lessonsService.updateBook(id,lesson);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
