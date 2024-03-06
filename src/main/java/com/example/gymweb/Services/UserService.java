package com.example.gymweb.Services;

import com.example.gymweb.Entities.Lesson;
import com.example.gymweb.Entities.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
@Service
public class UserService {
    private final AtomicLong Id= new AtomicLong();
    private Map<Long, User> idUsers= new HashMap<>();
    private Map<String, Long> idEmail= new HashMap<>();

    public UserService(){
        addUser(new User("Admin", "password", "XXXX", "admin@email.com",0,20));
    }

    public void addUser(User user){
        long id =Id.incrementAndGet();
        idUsers.put(id,user);
        user.setId(id);
        idEmail.put(user.getEmail(),id);
    }
    private String getPassword(long id){
        return idUsers.get(id).getPassword();
    }
    private User getUser(long id){
        return idUsers.get(id);
    }
    private User getUser(String email){
        return idUsers.get(idEmail.get(email));
    }
    public boolean checkLogin(String email, String  passwd ){
        long id=0;
        if(idEmail.containsKey(email)){
            id = idEmail.get(email);
        }
        if(id!=0){
            return idUsers.get(id).getEmail().equals(email) && getPassword(id).equals(passwd );
        }
        return false;
    }
    public void addToTimeTable(long id, Lesson lesson){
        idUsers.get(id).addLessons(lesson);
    }
    public void deleteFromTimeTable(long id, Lesson lesson){
        idUsers.get(id).deleteLessons(lesson);
    }
    public List<Lesson> getLessons(long id){
        return idUsers.get(id).getLessons();
    }
    public Lesson getLessonById(long idUser, long idLesson){
        List<Lesson> lessonList = idUsers.get(idUser).getLessons();
        Lesson lesson = null;
        for(Lesson l : lessonList){
            if(l!=null && l.getId()==idLesson){
                lesson = l;
            }
        }
        return lesson;
    }
}
