package com.example.gymweb.Controllers;
import com.example.gymweb.Entities.User;
import com.example.gymweb.Entities.Lesson;
//import com.example.gymweb.Services.UploadFileService;
import com.example.gymweb.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Controller
public class UserController {
    @Autowired
    UserService userService;
   /* @Autowired
    UploadFileService uploadFileService;*/
    //show profile of user and classes
    @GetMapping("/profile")
    public String viewProfile(Model model) {
        User user = userService.getUser(1);
        model.addAttribute("user", user);
        model.addAttribute("lessons", userService.getLessons(user.getId()));
        return "profile";
    }
    @PostMapping("/login/submit")
    public String login(Model model, @RequestParam String email, @RequestParam String password){
        boolean valid= userService.checkLogin(email,password);
        boolean error= !valid;
        model.addAttribute("error",error); //Login invalid
       if(valid){
           return "redirect:/profile";
       }
       return "redirect:/login";
    }

    //user reserves classes
    @PostMapping("/bookclass/{id}")
    public String bookLesson(@PathVariable long lessonId) {
        User user = userService.getUser(1);
        Lesson lesson = userService.getLessonById(1,lessonId);
        userService.bookClass(user.getId(), lesson);
        return "redirect:/profile";
    }
    //@PostMapping("/register")

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String addUser(@RequestBody User newUser /* @RequestParam("image") MultipartFile image */) throws IOException {
        /*if(newUser.getId()==0L){//add image to new books
            String nombreImagen=uploadFileService.saveImage(image);
            newUser.setImage(nombreImagen);
        }*/
        userService.addUser(newUser);
        return "redirect: profile";
    }
    @GetMapping("/ranking")
    public String viewRanking() {
        return "ranking";//this show comments and opinions, but not avaliable for now
    }
}
