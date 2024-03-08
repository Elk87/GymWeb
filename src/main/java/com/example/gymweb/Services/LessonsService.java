package com.example.gymweb.Services;

import com.example.gymweb.Entities.Lesson;
import com.example.gymweb.Entities.User;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class LessonsService {
    private Map<Long, Lesson> idLesson = new HashMap<>() ;
    private AtomicLong Id =new AtomicLong();
    //examples of classes and teacher
    public LessonsService() {
        User teacher1 = new User("Enrique", "password1", "XXXX1", "profesor1@email.com", 0, 30);
        User teacher2 = new User("Erika", "password2", "XXXX2", "profesor2@email.com", 0, 35);
        User teacher3 = new User("Joan", "password3", "XXXX3", "profesor3@email.com", 0, 40);
        addLesson(new Lesson(teacher1, Id.incrementAndGet(), LocalTime.of(9, 0), LocalTime.of(10, 30), "Yoga"));
        addLesson(new Lesson(teacher2, Id.incrementAndGet(), LocalTime.of(11, 0), LocalTime.of(12, 30), "Spinning"));
        addLesson(new Lesson(teacher3, Id.incrementAndGet(), LocalTime.of(14, 0), LocalTime.of(15, 30), "CrossFit"));
        addLesson(new Lesson(teacher1, Id.incrementAndGet(), LocalTime.of(16, 0), LocalTime.of(17, 30), "Pilates"));
        addLesson(new Lesson(teacher2, Id.incrementAndGet(), LocalTime.of(18, 0), LocalTime.of(19, 30), "Zumba"));
    }
    public void addLesson(Lesson lesson){
        long id = Id.incrementAndGet();
        lesson.setId(id);
        idLesson.put(id,lesson);
    }
    public Collection<Lesson> getLessons(){
        return idLesson.values();
    }
    public Lesson getLessonById(long id){
        return idLesson.get(id);
    }
    public Lesson deleteLessonById(long id){
        return idLesson.remove(id);
    }
    public Lesson updateBook(long id, Lesson lesson) {
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
        return l;}



}
