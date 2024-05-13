package com.example.gymweb.RestControllers;

import com.example.gymweb.Entities.Ranking;
import com.example.gymweb.Services.RankingService;
import com.example.gymweb.Services.UserService;
import com.example.gymweb.Entities.User;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.engine.jdbc.connections.internal.UserSuppliedConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.stream.events.Comment;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")

public class UserRestController {
    @Autowired
    UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RankingService rankingService;


    //update users information
    @PutMapping("/changeprofile")
    public ResponseEntity<User> updateProfile(HttpServletRequest request, @RequestBody User newUser, MultipartFile multipartFile) throws IOException {
        String email = request.getUserPrincipal().getName();
        User oldUser= userService.getUserByEmail(email);
        if (oldUser!= null) {
            newUser.setId(oldUser.getId());
            User user= userService.updateUser(oldUser.getId(), newUser, multipartFile);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // creating a user
    /*@PostMapping("/register")
    public ResponseEntity<User> newUser(@RequestParam String name,
                                        @RequestParam String DNI,
                                        @RequestParam String phoneNumber,
                                        @RequestParam int age,
                                        @RequestParam String email,
                                        @RequestParam String password) {
        User user = new User(name, passwordEncoder.encode(password),DNI,email,phoneNumber,age,"USER");
        userService.addUser(user);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }*/

    @PostMapping("/register")
    public ResponseEntity<User> newUser(@RequestBody Map<String, Object> user) {
        String name = (String) user.get("name");
        String DNI = (String) user.get("DNI");
        String phoneNumber = (String) user.get("phoneNumber");
        int age = (int) user.get("age");
        String email = (String) user.get("email");
        String password = (String) user.get("password");

        User newUser = new User(name, passwordEncoder.encode(password), DNI, email, phoneNumber, age, "USER");
        userService.addUser(newUser);

        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
    //delete an user using his ID
    @DeleteMapping("/deleteuser/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable long id) {
        User user = userService.getUser(id);
        if(user != null) {
            if(user.getRoles().contains("ADMIN")){
                return new ResponseEntity<>(user, HttpStatus.FORBIDDEN);
            }
            List<Ranking> rankings = rankingService.getRankingByUser(user);
            if (!rankings.isEmpty()) {
                for (Ranking ranking : rankings) {
                    rankingService.deleteRanking(ranking.getId());
                }
            }
            userService.deleteUserById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/deleteMyUser")
    public ResponseEntity<User> deleteMyUser(HttpServletRequest request) {
        String name = request.getUserPrincipal().getName();
        User user = userService.getUserByEmail(name);
        userService.deleteUserById(user.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //show users based on their ID
    @GetMapping("/users/{id}")
    public ResponseEntity<User> showUser(@PathVariable long id,HttpServletRequest request) {
        String email = request.getUserPrincipal().getName();
        if(userService.getUserByEmail(email).getRoles().contains("ADMIN")){
            User user = userService.getUser(id);
            if (user != null) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }else if (userService.getUserByEmail(email).getId() == id) {
            User user = userService.getUser(userService.getUserByEmail(email).getId());
            if (user != null) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
 @GetMapping("admin/allUsers")
    public ResponseEntity<List<User>> showAllUsers(){
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users,HttpStatus.OK);
 }



}