package com.example.gymweb.RestControllers;

import com.example.gymweb.Managers.UserManager;
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
    UserManager userManager;


    //code for login into an existing account
    @GetMapping("/login")
    public ResponseEntity<User> login(@RequestParam String email, @RequestParam String password){
        if(userManager.checkLogin(email,password)){
            return new ResponseEntity<>(userManager.getUser(email),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    //update users information
    @PutMapping("/changeprofile/{id}")
    public ResponseEntity<User> updateProfile(@PathVariable long id,@RequestBody User newUser){
        User oldUser= userManager.getUser(id);
        if (oldUser!= null) {
            newUser.setId(id);
            User user= userManager.updateUser(id,newUser);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // creating a user
    @PostMapping("/register")
    public ResponseEntity<User> newUser(@RequestBody User user){
        userManager.addUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
    //delete an user using his ID
    @DeleteMapping("/deleteuser/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable long id) {
        if(userManager.getUser(id).getId()==-1){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            userManager.removeUser(id);
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
        User user = userManager.getUser(id);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }




}