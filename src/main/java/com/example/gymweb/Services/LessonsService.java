package com.example.gymweb.Services;

import com.example.gymweb.Entities.Lesson;
import com.example.gymweb.Entities.Ranking;
import com.example.gymweb.Entities.User;
import com.example.gymweb.Repositories.LessonsRepository;
import com.example.gymweb.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class LessonsService {
    @Autowired
    private LessonsRepository lessonsRepository;
    @Autowired
    private UserRepository userRepository;

    //private Map<Long, Lesson> idLesson = new HashMap<>() ;
    private AtomicLong Id =new AtomicLong();//ver esto porque la bbdd genera id automatico
    //examples of classes and teacher
   public LessonsService(LessonsRepository lessonRepository) {
       lessonsRepository=lessonRepository;
    }
    /*public Lesson addLesson(Lesson lesson){
        long id = Id.incrementAndGet();
        lesson.setId(id);
        idLesson.put(id,lesson);
        return lesson;
    }*/
    public Lesson addLesson(Lesson lesson) {
        return lessonsRepository.save(lesson);
    }
    /*public Collection<Lesson> getLessons(){
        return idLesson.values();
    }*/
    public Collection<Lesson> getLessons() {
        return lessonsRepository.findAll();
    }
    /*public Lesson getLessonById(long id){
        return idLesson.get(id);
    }*/
    public Lesson getLessonById(long id) {
        return lessonsRepository.findById(id).orElse(null);
    }
    /*public Lesson deleteLessonById(long id){
        return idLesson.remove(id);
    }*/
    public Lesson deleteLessonById(long id) {
        Optional<Lesson> lesson = lessonsRepository.findById(id);
        if (lesson.isPresent()) {
            for (User user : lesson.get().getUsers()) {
                user.getLessons().remove(lesson.get());
            }
            userRepository.saveAll(lesson.get().getUsers());
            lessonsRepository.deleteById(id);
            return lesson.get();
        }
       return null;
    }

    /*public void updateLesson(long id, Lesson lesson) {
        Lesson l;
        if (!idLesson.containsKey(id)) {
            l = null;
        }else {
                l = idLesson.get(id);
                l.setSport(lesson.getSport());
                l.setTeacher(lesson.getTeacher());
                l.setStartTime(lesson.getStartTime());
                l.setFinishTime(lesson.getFinishTime());
        }
    }*/
    public void updateLesson(long id, Lesson lesson) {
        Optional<Lesson> lesson1 = lessonsRepository.findById(id);
        if (lesson1.isPresent()){
            Lesson l = lesson1.get();
            l.setTeacher(lesson.getTeacher());
            l.setSport(lesson.getSport());
            l.setStartTime(lesson.getStartTime());
            l.setFinishTime(lesson.getFinishTime());
            lessonsRepository.save(l);
        }
    }
    /*public List<Lesson> findLessonsBySport(String sport) {
        return lessonsRepository.findLessonBySport(sport);
    }*/



}
