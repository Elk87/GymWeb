package com.example.gymweb.Controllers;
import com.example.gymweb.Entities.Ranking;
import com.example.gymweb.Entities.User;
import com.example.gymweb.Entities.Lesson;
import com.example.gymweb.Repositories.UserRepository;
import com.example.gymweb.Services.RankingService;
import com.example.gymweb.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Collection;


@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    RankingService rankingService;

    //show user profile
    @GetMapping("/profile")
    public String viewProfile(Model model) throws SQLException {
        User user = userService.getUser(1);
        model.addAttribute("user", user);
        model.addAttribute("image", "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(userService.getUser(1)
                .getImageFile().getBytes(1, (int) userService.getUser(1).getImageFile().length())));
        //model.addAttribute("lessons", userService.getLessons(user.getId()));
        return "profile";
    }
    //check if the login is correct
    @PostMapping("/login/submit")
    public String login(Model model, @RequestParam String email, @RequestParam String password){
        boolean valid= userService.checkLogin(email,password);
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
        User user = userService.getUser(1);
        Lesson lesson = userService.getLessonById(1,id);
        userService.bookClass(user.getId(), lesson.getId());
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
        userService.addUser(newUser);
        return "redirect:/profile";
    }
    //show the lessons that the user has booked
    @GetMapping("/mylessons")
    public String showMyLessons(Model model){
        Collection<Lesson> myLessons= userService.getLessons(1);
        model.addAttribute("myLessons", myLessons);
        return "mylessons";
    }
    //book a class
    @PostMapping("/bookClass/{id}")
    public String bookClass(@PathVariable long id){
        userService.bookClass(1,id);
        return "redirect:/lessons";
    }
    //delete a lessons from users timetable
    @PostMapping ("/deleteClass/{id}")
    public String deleteClass(@PathVariable long id){
        userService.deleteClass(1,id);
        return "redirect:/mylessons";
    }
    @PostMapping("/updateProfile")
    public String UpdateUser( @RequestParam(value = "fileImage", required = false) MultipartFile fileImage,
                              @ModelAttribute User u) throws IOException {
        userService.updateUser(1,u,fileImage);
        return "redirect:/profile";
    }
    //show all user, only available for the admin
    @GetMapping("/admin/allUsers")
    public String showAllUsers(Model model) throws SQLException {
        Collection<User> users = userService.getAllUsers();
        for (User u : users){
            long id = u.getId();
            String imageKey = "image" + id;
            model.addAttribute(imageKey, "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(userService.getUser(id)
                    .getImageFile().getBytes(1, (int) userService.getUser(id).getImageFile().length())));
        }
        model.addAttribute("Users", users);
        return "adminUsers";
    }

    //show all the rankings that the user has done
    @GetMapping("/myrankings")
    public String showMyRankings(Model model){
        Collection<Ranking> rankings = rankingService.getRankingByUser(userService.getUser(1));
        model.addAttribute("myRankings", rankings);
        return "myRankings";
    }
    //create a ranking associated to the existing user
    @PostMapping("/createRanking")
    public String createRanking(@RequestParam String comment){
        rankingService.createRanking(userService.getUser(1),comment);
        return "redirect:/ranking";
    }
    //update a ranking done by the user
    @PostMapping("/updateRanking/{id}")
    public String updateRanking(@RequestParam String comment, @PathVariable long id){
        rankingService.updateRanking(id, comment);
        return "redirect:/myrankings";

    }
    //delete a ranking done by the user
    @PostMapping("/deleteRanking/{id}")
    public String deleteRanking( @PathVariable long id){
        rankingService.deleteRanking(id);
        return "redirect:/myrankings";

    }
}
