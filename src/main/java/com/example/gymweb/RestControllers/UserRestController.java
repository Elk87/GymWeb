package com.example.gymweb.RestControllers;

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

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")

public class UserRestController {
    @Autowired
    UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;


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
    public ResponseEntity<String> newUser(@RequestBody User newUser) {
        if (userService.findNameByEmail(newUser.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("El email ya está registrado");
        }
        User user = new User();
        user.setName(newUser.getName());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        user.setDNI(newUser.getDNI());
        user.setEmail(newUser.getEmail());
        user.setPhoneNumber(newUser.getPhoneNumber());
        user.setAge(newUser.getAge());
        // Asignar el rol USER al nuevo usuario
        user.setRoles(List.of("USER"));

        // Guardar el nuevo usuario en la base de datos
        userService.addUser(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Usuario registrado exitosamente");
    }
    //delete an user using his ID
    @DeleteMapping("/deleteuser/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable long id) {
       User user= userService.getUser(id);
        if(user!=null){
            userService.deleteUserById(id);
            return new ResponseEntity<>(user,HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
        User user = userService.getUserByEmail(name);
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
 @GetMapping("admin/allUsers")
    public ResponseEntity<List<User>> showAllUsers(){
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users,HttpStatus.OK);
 }



}