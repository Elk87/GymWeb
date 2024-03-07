package com.example.gymweb.Services;

import com.example.gymweb.Entities.Lesson;
import com.example.gymweb.Entities.User;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
@Service
public class UserService {
    private final AtomicLong Id= new AtomicLong();
    private Map<Long, User> idUsers= new HashMap<>();
    private Map<String, Long> idEmail= new HashMap<>();

    //adding an user
    public UserService(){
        addUser(new User("Admin", "password", "09864527F", "admin@email.com",666777888,20));
    }

    //add an user
    public void addUser(User user){
        long id =Id.incrementAndGet();
        idUsers.put(id,user);
        user.setId(id);
        idEmail.put(user.getEmail(),id);
    }

    private String getPassword(long id){
        return idUsers.get(id).getPassword();
    }
    public User getUser(long id){
        return idUsers.get(id);
    }
    public User getUser(String email){
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
    public Collection<Lesson> getLessons(long id){
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

    public void bookClass(long userId, Lesson lesson) {
        User user = idUsers.get(userId);
        if (user != null) {
            user.addLessons(lesson);
        } else {
            System.err.println("Usuario no encontrado");
        }
    }

}
