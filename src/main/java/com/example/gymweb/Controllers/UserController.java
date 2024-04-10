package com.example.gymweb.Controllers;
import com.example.gymweb.Entities.Ranking;
import com.example.gymweb.Entities.User;
import com.example.gymweb.Entities.Lesson;
import com.example.gymweb.Repositories.UserRepository;
import com.example.gymweb.Managers.RankingManager;
import com.example.gymweb.Managers.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.Collection;


@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserManager userManager;
    @Autowired
    RankingManager rankingManager;
    /* @Autowired
    UploadFileService uploadFileService;*/
    //show user profile
    @GetMapping("/profile")
    public String viewProfile(Model model) {
        User user = userManager.getUser(1);
        model.addAttribute("user", user);
        //model.addAttribute("lessons", userService.getLessons(user.getId()));
        return "profile";
    }
    //check if the login is correct
    @PostMapping("/login/submit")
    public String login(Model model, @RequestParam String email, @RequestParam String password){
        boolean valid= userManager.checkLogin(email,password);
        boolean error= !valid;
        model.addAttribute("error",error); //Login invalid
       if(valid){
           return "redirect:/profile";
       }
       return "loginIncorrect";
    }

    //user book a class
    @PostMapping("/bookclass/{id}")
    public String bookLesson(@PathVariable long id) {
        User user = userManager.getUser(1);
        Lesson lesson = userManager.getLessonById(1,id);
        userManager.bookClass(user.getId(), lesson.getId());
        return "redirect:/profile";
    }
    //@PostMapping("/register")
    //create a new user
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String addUser(@RequestBody User newUser /* @RequestParam("image") MultipartFile image */) throws IOException {
        /*if(newUser.getId()==0L){//add image to new books
            String nombreImagen=uploadFileService.saveImage(image);
            newUser.setImage(nombreImagen);
        }*/
        userManager.addUser(newUser);
        return "redirect:/profile";
    }
    //show the lessons that the user has booked
    @GetMapping("/mylessons")
    public String showMyLessons(Model model){
        Collection<Lesson> myLessons= userManager.getLessons(1);
        model.addAttribute("myLessons", myLessons);
        return "mylessons";
    }
    //book a class
    @PostMapping("/bookClass/{id}")
    public String bookClass(@PathVariable long id){
        userManager.bookClass(1,id);
        return "redirect:/lessons";
    }
    //delete a lessons from users timetable
    @PostMapping ("/deleteClass/{id}")
    public String deleteClass(@PathVariable long id){
        userManager.deleteClass(1,id);
        return "redirect:/mylessons";
    }
    //show all user, only available for the admin
    @GetMapping("/admin/allUsers")
    public String showAllUsers(Model model){
       // Collection<User> users = userService.showAllUsers();
        Collection<User> users = userManager.getAllUsers();
        model.addAttribute("Users", users);
        return "adminUsers";
    }
    //show all the rankings that the user has done
    @GetMapping("/myrankings")
    public String showMyRankings(Model model){
        Collection<Ranking> rankings = rankingManager.getRanking();
        model.addAttribute("myRankings", rankings);
        return "myRankings";
    }
    //create a ranking associated to the existing user
    @PostMapping("/createRanking")
    public String createRanking(@RequestParam String comment){
        rankingManager.createRanking(comment);
        return "redirect:/ranking";
    }
    //update a ranking done by the user
    @PostMapping("/updateRanking/{id}")
    public String updateRanking(@RequestParam String comment, @PathVariable long id){
        rankingManager.updateRanking(id, comment);
        return "redirect:/myrankings";

    }
    //delete a ranking done by the user
    @PostMapping("/deleteRanking/{id}")
    public String deleteRanking( @PathVariable long id){
        rankingManager.deleteRanking(id);
        return "redirect:/myrankings";

    }
}
