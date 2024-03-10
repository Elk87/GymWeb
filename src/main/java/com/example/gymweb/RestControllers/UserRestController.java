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
    //this code es for user
    //first is login (user)
    @GetMapping("/login")
    public ResponseEntity<User> login(@RequestParam String email, @RequestParam String password){
        if(userService.checkLogin(email,password)){
            return new ResponseEntity<>(userService.getUser(email),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    //this code is change profile of user
    @PutMapping("/changeprofile/{id}/")
    public ResponseEntity<User> updateProfile(@PathVariable long id,@RequestBody User newUser){
        User oldUser= userService.getUser(id);
        if (oldUser!= null) {
            newUser.setId(id);
            userService.updateUser(id,newUser);
            return new ResponseEntity<>(newUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    //also, this is for register of user
    @PostMapping("/register")
    public ResponseEntity<User> newUser(@RequestBody User user){
        userService.addUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
    //this delete an user
    @DeleteMapping("/deleteuser/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable long id) {
        User user = userService.removeUser(id);
        if(user != null){
            return new ResponseEntity<>(user,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



}