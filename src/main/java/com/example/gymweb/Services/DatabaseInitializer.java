package com.example.gymweb.Services;

import com.example.gymweb.Entities.Lesson;
import com.example.gymweb.Entities.Ranking;
import com.example.gymweb.Entities.User;
import com.example.gymweb.Entities.UserRole;
import com.example.gymweb.Repositories.LessonsRepository;
import com.example.gymweb.Repositories.RankingRepository;
import com.example.gymweb.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
@Transactional
@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final LessonsRepository lessonRepository;
    private final RankingRepository rankingRepository;


    public DatabaseInitializer(UserRepository userRepository, LessonsRepository lessonRepository, RankingRepository rankingRepository) {
        this.userRepository = userRepository;
        this.lessonRepository = lessonRepository;
        this.rankingRepository = rankingRepository;
       ;
    }

    @Override
    public void run(String... args) throws Exception {
        // Crear usuarios
        User admin = new User("Admin","adminPass", "12345678A", "admin@example.com", 123456789, 30,"/img/fotoPerfil.jpg");
        admin.setRole(UserRole.ADMIN);
        User user1 = new User("John", "userPass", "12345678B", "john@example.com", 987654321, 25);
        User user2 = new User("Jane","userPass", "12345678C", "jane@example.com", 987654322, 28);

        // Guardar usuarios
        Ranking ranking1 = new Ranking("Good lesson!");
        Ranking ranking2 = new Ranking("Awesome class!");

        admin.addRanking(ranking1);
        admin.addRanking(ranking2);
        userRepository.save(admin);


        // Crear lecciones
        Lesson lesson1 = new Lesson(user1, LocalTime.of(10, 0), LocalTime.of(11, 0), "Yoga");
        Lesson lesson2 = new Lesson(user2, LocalTime.of(15, 0), LocalTime.of(16, 0), "Zumba");

        // Guardar lecciones


        // Asignar lecciones a usuarios
        user1.addLessons(lesson1);
        user2.addLessons(lesson2);
        lessonRepository.save(lesson1);
        lessonRepository.save(lesson2);


    }
}


