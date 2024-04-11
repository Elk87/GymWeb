package com.example.gymweb.Services;

import com.example.gymweb.Entities.Lesson;
import com.example.gymweb.Entities.User;
import com.example.gymweb.Repositories.LessonsRepository;
import com.example.gymweb.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.*;

@Service
public class UserService {
    @Autowired
    UploadFileService uploadFileService;
    @Autowired
    LessonsService lessonsService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    LessonsRepository lessonsRepository;

   /* private final AtomicLong Id= new AtomicLong();
    private Map<Long, User> idUsers= new HashMap<>();//map of id and user
    private Map<String, Long> idEmail= new HashMap<>(); //map of email and id*/

    //adding an user
    public UserService(LessonsService lessonsService1, UserRepository userRepository1, LessonsRepository lessonsRepository1){
        this.lessonsRepository=lessonsRepository1;
        this.lessonsService=lessonsService1;
        this.userRepository=userRepository1;
    }

    //add an user
    /*public void addUser(User user){
        long id =Id.incrementAndGet();
        idUsers.put(id,user);
        user.setId(id);
        idEmail.put(user.getEmail(),id);
    }*/
    //delete user
    /*public User removeUser(long id){
        idEmail.remove(idUsers.get(id).getEmail()); //remove from the <email,ID> Map
        return idUsers.remove(id);
    }*/


    //add an user
    public User addUser(User user){
        return userRepository.save(user);
    }
    //delete an user
    public void removeUser(Long id){
        userRepository.deleteById(id);
    }
    //take all users
    public List<User>getAllUsers(){
        return userRepository.findAll();
    }

    //list of user by email and phone
   public List<User> getByEmailAndPhone(int phoneNumber, String email){
        return userRepository.findByPhoneNumberAndEmail(phoneNumber,email);
   }

   /* public Collection<User> showAllUsers(){
        Collection<User> users = idUsers.values();
        return users;
    }*/

    /*private String getPassword(long id){
        return idUsers.get(id).getPassword();
    }*/
    public String getPassword(long id){
        Optional<User> op= userRepository.findById(id);
        if(op.isPresent()){
            User user=op.get();
            return user.getPassword();
        }
        else{
            return null;
        }
    }
    public User removeUser(long id){
        Optional<User> op= userRepository.findById(id);
        if(op.isPresent()){
            User user=op.get();
            userRepository.deleteById(id);
            return user;
        }else{
            return null;
        }
    }
   /* public User getUser(long id){
        return idUsers.get(id);
    }*/

    public User getUser(long id){
        return userRepository.findUserById(id);
    }
   /* public User getUser(String email){
        return idUsers.get(idEmail.get(email));
    }*/
    public User getUser(String email){
        return userRepository.findByEmail(email);
    }

    /*public boolean checkLogin(String email, String  passwd ){
        long id=0;
        if(idEmail.containsKey(email)){
            id = idEmail.get(email);
        }
        if(id!=0){
            return idUsers.get(id).getEmail().equals(email) && getPassword(id).equals(passwd );
        }
        return false;
    }*/
   public boolean checkLogin(String email, String passwd ){
       User user = userRepository.findByEmailAndPassword(email,passwd);
       return user != null && user.getEmail().equals(email) && user.getPassword().equals(passwd);
   }


    /*public void deleteClass(long idUser, long idLesson){
        idUsers.get(idUser).deleteLessons(lessonsService.getLessonById(idLesson));
    }*/
    public void deleteClass(long userId, long lessonId) {
        User user = userRepository.findById(userId).orElse(null);
        Lesson lesson = lessonsRepository.findById(lessonId).orElse(null);
        if (user != null && lesson != null) {
            user.deleteLessons(lesson);
            userRepository.save(user);
        } else {
            System.err.println("Usuario o clase no encontrados");
        }
    }
    /*public Collection<Lesson> getLessons(long id){
        return idUsers.get(id).getLessons();
    }*/
    public Collection<Lesson> getLessons(long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return user.getLessons();
        } else {
            System.err.println("Usuario no encontrado");
            return Collections.emptyList();
        }
    }
    /*public Lesson getLessonById(long idUser, long idLesson){
        List<Lesson> lessonList = idUsers.get(idUser).getLessons();
        Lesson lesson = null;
        for(Lesson l : lessonList){
            if(l!=null && l.getId()==idLesson){
                lesson = l;
            }
        }
        return lesson;
    }*/
    public Lesson getLessonById(long userId, long lessonId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            for (Lesson lesson : user.getLessons()) {
                if (lesson != null && lesson.getId() == lessonId) {
                    return lesson;
                }
            }
        }
        return null;
    }
   /* public void bookClass(long userId, long idLesson) {
        User user = idUsers.get(userId);
        if (user != null) {
            user.addLessons(lessonsService.getLessonById(idLesson));
        } else {
            System.err.println("Usuario no encontrado");
        }
    }*/
   public void bookClass(long userId, long lessonId) {
       User user = userRepository.findById(userId).orElse(null);
       Lesson lesson = lessonsRepository.findById(lessonId).orElse(null);
       if (user != null && lesson != null) {
           user.addLessons(lesson);
           userRepository.save(user);
       } else {
           System.err.println("Usuario o clase no encontrados");
       }
   }
    public User findByID(long id){
       return userRepository.findUserById(id);
    }
    public User updateUser(long id, User newUser, MultipartFile multipartFile) throws IOException {
        Optional<User> u = userRepository.findById(id);

        if (u.isPresent()) {
            User user = u.get();
            user.setName(newUser.getName());
            user.setEmail(newUser.getEmail());
            user.setPassword(newUser.getPassword());
            if (multipartFile != null && !multipartFile.isEmpty()) {
                user.setImage(multipartFile.getBytes());
            }

            userRepository.save(user);
            return user;
        } else {
            return null;
        }
    }
    public List<User> findUserByLesson(List<Lesson> lessons){
       return userRepository.findUserByLessons(lessons);
    }

    public Optional<User> findByName(String name  ){
       return userRepository.findByName(name);
   }
    public List<Lesson> findLessonsByUser(List<User> user){
        return lessonsRepository.findLessonByUsers(user);
    }

    public List<Lesson> findLessonsByTeacher(User user){
        return lessonsRepository.findLessonByTeacher(user);
    }

}
