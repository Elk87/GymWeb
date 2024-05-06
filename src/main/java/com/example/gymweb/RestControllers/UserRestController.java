package com.example.gymweb.RestControllers;

import com.example.gymweb.Services.UserService;
import com.example.gymweb.Entities.User;
import jakarta.servlet.http.HttpServletRequest;
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

import java.io.IOException;

@RestController
@RequestMapping("/api")

public class UserRestController {
    @Autowired
    UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    //update users information
    @PutMapping("/changeprofile/{id}")
    public ResponseEntity<User> updateProfile(@PathVariable long id, @RequestBody User newUser, MultipartFile multipartFile) throws IOException {
        User oldUser= userService.getUser(id);
        if (oldUser!= null) {
            newUser.setId(id);
            User user= userService.updateUser(id,newUser, multipartFile);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // creating a user
    @PostMapping("/register")
    public ResponseEntity<User> newUser(@RequestParam String name,
                                        @RequestParam String DNI,
                                        @RequestParam String phoneNumber,
                                        @RequestParam int age,
                                        @RequestParam String email,
                                        @RequestParam String password) {
        User user = new User(name, passwordEncoder.encode(password),DNI,email,phoneNumber,age,"USER");
        userService.addUser(user);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    //delete an user using his ID
    @DeleteMapping("/deleteuser/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable long id) {
        if(userService.getUser(id).getId()==-1){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            userService.removeUser(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        //userService.removeUser(id);

        /*if(user != null){
            return new ResponseEntity<>(user,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }*/
    }
    @DeleteMapping("/deleteMyUser")
    public ResponseEntity<User> deleteMyUser(HttpServletRequest request) {
        String name = request.getUserPrincipal().getName();
        User user = userService.findByName(name).orElseThrow();
        userService.deleteUserById(user.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //show users based on their ID
    @GetMapping("/users/{id}")
    public ResponseEntity<User> showUser(@PathVariable long id) {
        User user = userService.getUser(id);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }




}