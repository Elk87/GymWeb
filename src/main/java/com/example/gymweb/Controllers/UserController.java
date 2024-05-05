package com.example.gymweb.Controllers;
import com.example.gymweb.Entities.Ranking;
import com.example.gymweb.Entities.User;
import com.example.gymweb.Entities.Lesson;
import com.example.gymweb.Repositories.UserRepository;
import com.example.gymweb.Secure.jwt.LoginRequest;
import com.example.gymweb.Secure.jwt.Token;
import com.example.gymweb.Secure.jwt.UserLoginService;
import com.example.gymweb.Services.RankingService;
import com.example.gymweb.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.Principal;
import java.security.Security;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Collection;


@Controller
public class UserController {
   //no puede haber repositorios en controllers
    @Autowired
    UserService userService;
    @Autowired
    RankingService rankingService;
    @Autowired
    UserLoginService loginService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @ModelAttribute
    public void addAttributes(Model model, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        if (principal != null) {
            model.addAttribute("logged", true);
            model.addAttribute("admin", request.isUserInRole("ADMIN"));
            model.addAttribute("user", request.isUserInRole("USER"));
        } else {
            model.addAttribute("logged", false);
        }
        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        model.addAttribute("token", token.getToken());
    }
    //show user profile
    @GetMapping("/profile")
    public String viewProfile(Model model, HttpServletRequest request) throws SQLException {
        String email = request.getUserPrincipal().getName();
        User user = userService.getUserByEmail(email);
        model.addAttribute("user",user);
        model.addAttribute("admin", request.isUserInRole("ADMIN"));
        model.addAttribute("image", "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(user.getImageFile().getBytes(1, (int) user.getImageFile().length())));
        //model.addAttribute("lessons", userService.getLessons(user.getId()));
        return "profile";
    }


    //user book a class
    @PostMapping("/lessons/bookclass/{id}")
    public String bookLesson(@PathVariable long id,HttpServletRequest request) {
        String email = request.getUserPrincipal().getName();
        User user = userService.getUserByEmail(email);
        Lesson lesson = userService.getLessonById(user.getId(),id);
        userService.bookClass(user.getId(), lesson.getId());
        return "redirect:/profile";
    }
    //@PostMapping("/register")
    //create a new user
    /*@RequestMapping(value = "/register", method = RequestMethod.POST)
    public String addUser(@RequestBody User newUser /* @RequestParam("image") MultipartFile image ) throws IOException {
        if(newUser.getId()==0L){//add image to new books
            String nombreImagen=uploadFileService.saveImage(image);
            newUser.setImage(nombreImagen);
        }
        userService.addUser(newUser);
        return "redirect:/profile";
    }*/

    @PostMapping("/register")
    public String registerUser(@RequestParam String name, @RequestParam String DNI, @RequestParam String phoneNumber,@RequestParam int age, @RequestParam String email, @RequestParam String password) {
        if (userService.getUserByEmail(email) != null) {
            return "redirect:/register?error=email_exists";
        }
        User newUser = new User(name,passwordEncoder.encode(password),DNI,email,phoneNumber,age,"USER");

        userService.saveUser(newUser);

        return "redirect:/login?registered=true";
    }

    //show the lessons that the user has booked
    @GetMapping("/profile/mylessons")
    public String showMyLessons(Model model,HttpServletRequest request ){
        String email = request.getUserPrincipal().getName();
        User user = userService.getUserByEmail(email);
        Collection<Lesson> myLessons= userService.getLessons(user.getId());
        model.addAttribute("myLessons", myLessons);
        return "mylessons";
    }
    //book a class
    @PostMapping("/lessons/bookClass/{id}")
    public String bookClass(@PathVariable long id,HttpServletRequest request){
        String email = request.getUserPrincipal().getName();
        User user = userService.getUserByEmail(email);
        userService.bookClass(user.getId(),id);
        return "redirect:/lesson";
    }
    //delete a lessons from users timetable
    @PostMapping ("/lessons/deleteClass/{id}")
    public String deleteClass(@PathVariable long id,HttpServletRequest request){
        String email = request.getUserPrincipal().getName();
        User user = userService.getUserByEmail(email);
        userService.deleteClass(user.getId(), id);
        return "redirect:/profile/mylessons";
    }
    @PostMapping("/profile/updProfile")
    public String updateUser(@RequestParam(value = "fileImage", required = false) MultipartFile fileImage,
                             @RequestParam String name,
                             @RequestParam int age,
                             @RequestParam String phoneNumber,
                             HttpServletRequest request) throws IOException {
        String userEmail = request.getUserPrincipal().getName();
        User existingUser = userService.getUserByEmail(userEmail);
        existingUser.setName(name);
        existingUser.setAge(age);
        existingUser.setPhoneNumber(phoneNumber);

        if (fileImage != null && !fileImage.isEmpty()) {
            existingUser.setImageFile(BlobProxy.generateProxy(fileImage.getInputStream(), fileImage.getSize()));
        }

        userService.updateUser(existingUser.getId(), existingUser, fileImage);

        return "redirect:/";
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
    @GetMapping("/profile/myrankings")
    public String showMyRankings(Model model,HttpServletRequest request){
        String email = request.getUserPrincipal().getName();
        User user = userService.getUserByEmail(email);
        Collection<Ranking> rankings = rankingService.getRankingByUser(user);
        model.addAttribute("myRankings", rankings);
        return "myRankings";
    }
    //create a ranking associated to the existing user
    @PostMapping("/ranking/createRanking")
    public String createRanking(@RequestParam String comment,HttpServletRequest request){
        String email = request.getUserPrincipal().getName();
        User user = userService.getUserByEmail(email);
        rankingService.createRanking(user,comment);
        return "redirect:/ranking";
    }
    //update a ranking done by the user
    @PostMapping("/ranking/updateRanking/{id}")
    public String updateRanking(@RequestParam String comment, @PathVariable long id){
        rankingService.updateRanking(id, comment);
        return "redirect:/profile/myrankings";

    }
    //delete a ranking done by the user
    @PostMapping("/ranking/deleteRanking/{id}")
    public String deleteRanking( @PathVariable long id){
        rankingService.deleteRanking(id);
        return "redirect:/profile/myrankings";

    }
}
