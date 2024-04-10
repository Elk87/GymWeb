package com.example.gymweb.Services;

import com.example.gymweb.Entities.Lesson;
import com.example.gymweb.Entities.Ranking;
import com.example.gymweb.Entities.User;
import com.example.gymweb.Entities.UserRole;
import com.example.gymweb.Repositories.LessonsRepository;
import com.example.gymweb.Repositories.RankingRepository;
import com.example.gymweb.Repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalTime;
@Transactional
@Component
public class DatabaseInitializer {
    @Autowired
    RankingService rankingService;
    @Autowired
    UserService userService;
    @Autowired
    LessonsService lessonsService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    LessonsRepository lessonsRepository;
    @Autowired
    RankingRepository rankingRepository;
    @PostConstruct
    public void init() throws IOException {
        // Creación de usuarios
        User teacher1 = new User("Enrique", "password1", "XXXX1", "profesor1@email.com", 0, 30);
        User teacher2 = new User("Erika", "password2", "XXXX2", "profesor2@email.com", 0, 35);
        User teacher3 = new User("Joan", "password3", "XXXX3", "profesor3@email.com", 0, 40);
        User p1 = new User("A", "password11", "XXXX11", "p1@email.com", 0, 30);
        User p2 = new User("B", "password22", "XXXX22", "p2@email.com", 0, 35);
        User p3 = new User("C", "password33", "XXXX33", "p3@email.com", 0, 40);
        User user = new User("Admin", "password", "09864527F", "admin@email.com",666777888,20, "/img/fotoPerfil.jpg");
        user.setRole(UserRole.ADMIN);

        // Guardar usuarios primero
        userRepository.save(user);
        userRepository.save(teacher1);
        userRepository.save(teacher2);
        userRepository.save(teacher3);
        userRepository.save(p1);
        userRepository.save(p2);
        userRepository.save(p3);

        // Creación de lecciones
        Lesson l1 = new Lesson(teacher1, LocalTime.of(9, 0), LocalTime.of(10, 30), "Yoga");
        Lesson l2 = new Lesson(teacher2, LocalTime.of(11, 0), LocalTime.of(12, 30), "Spinning");
        Lesson l3 = new Lesson(teacher3, LocalTime.of(14, 0), LocalTime.of(15, 30), "CrossFit");
        Lesson l4 = new Lesson(teacher1, LocalTime.of(16, 0), LocalTime.of(17, 30), "Pilates");
        Lesson l5 = new Lesson(teacher2, LocalTime.of(18, 0), LocalTime.of(19, 30), "Zumba");

        // Guardar lecciones después de guardar los usuarios
        lessonsRepository.save(l1);
        lessonsRepository.save(l2);
        lessonsRepository.save(l3);
        lessonsRepository.save(l4);
        lessonsRepository.save(l5);

        // Creación de rankings
        Ranking r1 = new Ranking("Clases variadas y profesores entregados");
        Ranking r2 = new Ranking("Poco disponibilidad de horarios ");

        rankingRepository.save(r1);
        rankingRepository.save(r2);

        // Añadir lecciones y rankings a los usuarios
        user.addLessons(l1);
        user.addLessons(l2);
        user.addLessons(l5);
        user.addLessons(l3);
        user.addLessons(l4);

        p1.addLessons(l1);
        p1.addLessons(l2);
        p1.addLessons(l5);

        p2.addLessons(l3);

        p3.addLessons(l4);
        p3.addLessons(l2);
    }
}

