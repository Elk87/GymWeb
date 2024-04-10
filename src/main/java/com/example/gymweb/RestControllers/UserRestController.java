package com.example.gymweb.RestControllers;

import com.example.gymweb.Services.UserService;
import com.example.gymweb.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")

public class UserRestController {
    @Autowired
    UserService userService;


    //code for login into an existing account
    @GetMapping("/login")
    public ResponseEntity<User> login(@RequestParam String email, @RequestParam String password){
        if(userService.checkLogin(email,password)){
            return new ResponseEntity<>(userService.getUser(email),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    //update users information
    @PutMapping("/changeprofile/{id}")
    public ResponseEntity<User> updateProfile(@PathVariable long id,@RequestBody User newUser){
        User oldUser= userService.getUser(id);
        if (oldUser!= null) {
            newUser.setId(id);
            User user= userService.updateUser(id,newUser);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // creating a user
    @PostMapping("/register")
    public ResponseEntity<User> newUser(@RequestBody User user){
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