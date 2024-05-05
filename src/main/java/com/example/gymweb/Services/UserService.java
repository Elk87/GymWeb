package com.example.gymweb.Services;

import com.example.gymweb.Entities.Lesson;
import com.example.gymweb.Entities.Ranking;
import com.example.gymweb.Entities.User;
import com.example.gymweb.Repositories.LessonsRepository;
import com.example.gymweb.Repositories.RankingRepository;
import com.example.gymweb.Repositories.UserRepository;
import com.example.gymweb.Secure.RepositoryUserDetailsService;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
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
    @Autowired
    RankingRepository rankingRepository;
    @Autowired
     PasswordEncoder passwordEncoder;
    @Autowired
    RepositoryUserDetailsService userDetailsService;

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
    public User getUserByEmail(String email){
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
    public boolean checkLogin(String email, String passwd) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return passwordEncoder.matches(passwd, userDetails.getPassword());
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
            System.err.println("user or class not found");
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
            System.err.println("user not found");
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
            System.err.println("user not found");
        }
    }*/
   public void bookClass(long userId, long lessonId) {
       User user = userRepository.findById(userId).orElse(null);
       Lesson lesson = lessonsRepository.findById(lessonId).orElse(null);
       if (user != null && lesson != null) {
           user.addLessons(lesson);
           userRepository.save(user);
       } else {
           System.err.println("user or class not found");
       }
   }
    public User findByID(long id){
       return userRepository.findUserById(id);
    }
    public User deleteUserById(long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            for (Lesson lesson : user.getLessons()) {
                lesson.getUsers().remove(user);
            }
            for (Ranking ranking : user.getComments()) {
                ranking.setUser(null);
            }
            lessonsRepository.saveAll(user.getLessons());
            rankingRepository.saveAll(user.getComments());
            userRepository.deleteById(id);
            return user;
        } else {
            return null;
        }
    }

    public User updateUser(long id, User newUser, MultipartFile fileImage) throws IOException {
        Optional<User> u = userRepository.findById(id);
        if (u.isPresent()) {
            User user = u.get();
            user.setName(newUser.getName());
            user.setEmail(newUser.getEmail());
            user.setPassword(newUser.getPassword());
            user.setPhoneNumber(newUser.getPhoneNumber());
            if(fileImage!=null && !fileImage.isEmpty()){
                user.setImageFile(BlobProxy.generateProxy(fileImage.getInputStream(), fileImage.getSize()));
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
    public String findNameByEmail(String email) {
        return userRepository.findNameByEmail(email);
    }

    public List<Lesson> findLessonsByUser(List<User> user){
        return lessonsRepository.findLessonByUsers(user);
    }

    public List<Lesson> findLessonsByTeacher(User user){
        return lessonsRepository.findLessonByTeacher(user);
    }

}
