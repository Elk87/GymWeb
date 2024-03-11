package com.example.gymweb.Services;

import com.example.gymweb.Entities.Lesson;
import com.example.gymweb.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
@Service
public class UserService {
    @Autowired
    UploadFileService uploadFileService;
    @Autowired
    LessonsService lessonsService;
    private final AtomicLong Id= new AtomicLong();
    private Map<Long, User> idUsers= new HashMap<>();//map of id and user
    private Map<String, Long> idEmail= new HashMap<>(); //map of email and id

    //adding an user
    public UserService(){
        User user = new User("Admin", "password", "09864527F", "admin@email.com",666777888,20, "/img/fotoPerfil.jpg");
        addUser(user);
    }

    //add an user
    public void addUser(User user){
        long id =Id.incrementAndGet();
        idUsers.put(id,user);
        user.setId(id);
        idEmail.put(user.getEmail(),id);
    }
    public Collection<User> showAllUsers(){
        Collection<User> users = idUsers.values();
        return users;
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

    public User removeUser(long id){
        idEmail.remove(idUsers.get(id).getEmail()); //remove from the <email,ID> Map
        return idUsers.remove(id);

    }
    public void deleteClass(long idUser, long idLesson){
        idUsers.get(idUser).deleteLessons(lessonsService.getLessonById(idLesson));
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
    public void bookClass(long userId, long idLesson) {
        User user = idUsers.get(userId);
        if (user != null) {
            user.addLessons(lessonsService.getLessonById(idLesson));
        } else {
            System.err.println("Usuario no encontrado");
        }
    }

    public void updateUser(long id, User newUser) {
        idUsers.put(id,newUser);//overwrite old user
        idEmail.put(newUser.getEmail(),id); //overwrite old register
    }
}
