/*package com.example.gymweb.Services;
                                                              //  Comentado para que no se añada constantemente
import com.example.gymweb.Entities.Lesson;
import com.example.gymweb.Entities.Ranking;
import com.example.gymweb.Entities.User;

import com.example.gymweb.Repositories.LessonsRepository;
import com.example.gymweb.Repositories.RankingRepository;
import com.example.gymweb.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.time.LocalTime;
import java.util.ArrayList;

@Transactional
@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final LessonsRepository lessonRepository;
    private final RankingRepository rankingRepository;
    private final PasswordEncoder passwordEncoder;


    public DatabaseInitializer(UserRepository userRepository, LessonsRepository lessonRepository, RankingRepository rankingRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.lessonRepository = lessonRepository;
        this.rankingRepository = rankingRepository;
       ;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        User admin = new User("Admin", passwordEncoder.encode("adminPass"), "12345678A", "admin@example.com", "123456789", 30,"ADMIN");
        User user1 = new User("John", passwordEncoder.encode("userPass"), "12345678B", "john@example.com", "987654321", 25,"USER");
        User user2 = new User("Jane",passwordEncoder.encode("otherPass"), "12345678C", "jane@example.com", "987654322", 28,"USER");

        Ranking ranking1 = new Ranking("Good lesson!");
        Ranking ranking2 = new Ranking("Awesome class!");

        admin.addRanking(ranking1);
        admin.addRanking(ranking2);
        userRepository.save(admin);

        Lesson lesson1 = new Lesson(user1, LocalTime.of(10, 0), LocalTime.of(11, 0), "Yoga");
        Lesson lesson2 = new Lesson(user2, LocalTime.of(15, 0), LocalTime.of(16, 0), "Zumba");

        user1.addLessons(lesson1);
        user2.addLessons(lesson2);
        lessonRepository.save(lesson1);
        lessonRepository.save(lesson2);

    }
}*/


