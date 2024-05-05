package com.example.gymweb.RestControllers;
import com.example.gymweb.Entities.Lesson;
import com.example.gymweb.Entities.User;
import com.example.gymweb.Repositories.LessonsRepository;
import com.example.gymweb.Services.LessonsService;
import com.example.gymweb.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")

public class LessonsRest {
    @Autowired
    LessonsService lessonsService;
    @Autowired
    LessonsRepository lessonsRepository;
    @Autowired
    UserService userService;
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
        return new ResponseEntity<>(lesson, HttpStatus.OK);
    }
    //update an existing lesson giving his ID
    @PutMapping("/lesson/{id}")
    public ResponseEntity<Lesson> updateLesson(BindingResult bindingResult, @RequestParam String teacher, @ RequestParam LocalTime startTime, @RequestParam LocalTime finishTime, @RequestParam String sport, @PathVariable Long id){
        if (bindingResult.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<User> user = userService.findByName(teacher);
        if (user.isPresent()){
            User t = user.get();
            Lesson newLesson = new Lesson(t,startTime,finishTime,sport);
            lessonsService.updateLesson(id, newLesson);
            return new ResponseEntity<>(newLesson,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/lessonsByTeacherSport")
    public ResponseEntity<List<Lesson>> getLessonsByTeacherAndSport(
            @RequestParam String teacherName,
            @RequestParam String sport) {
        return new  ResponseEntity<>(lessonsRepository.findByTeacherNameAndSport(teacherName, sport),HttpStatus.OK);
    }

    @PostMapping("/uploadFile/{lessonId}")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, @PathVariable Long lessonId, RedirectAttributes redirectAttributes) {

        //original name of archive
        String originalFilename = file.getOriginalFilename();
        String cleanFilename = sanitizeFilename(originalFilename);
        String storageDirectory = "src/files";
        String filePath = storageDirectory + "/" + originalFilename;
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(filePath);
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/uploadStatus";
        }
        Lesson lesson = lessonsService.getLessonById(lessonId);
        if (lesson != null) {
            lesson.setFilePath(filePath);
            lessonsService.addLesson(lesson);
        }
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");
        return "redirect:/uploadStatus";

    }
    private String sanitizeFilename(String originalFilename) {
        return originalFilename.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
    }
        @GetMapping("/lessons/{id}/image")
    public ResponseEntity<Object> downloadImage(@PathVariable long id) throws SQLException {
        Lesson lesson = lessonsService.getLessonById(id);

        if (lesson.getFilePath() != null) {
            Path imagePath = Paths.get(lesson.getFilePath());
            try {
                Resource file = new UrlResource(imagePath.toUri());
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, "video/mp4")
                        .contentLength(file.contentLength())
                        .body(file);
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al leer la imagen", e);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


