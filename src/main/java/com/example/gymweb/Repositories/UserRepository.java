package com.example.gymweb.Repositories;
import com.example.gymweb.Entities.Lesson;
import com.example.gymweb.Entities.Ranking;
import com.example.gymweb.Entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    List<User> findByPhoneNumberAndEmail(int phoneNumber, String email);
    User findByEmailAndPassword(String email, String password);
    User findByEmail(String email);
    User findUserById(long id);
    List<User> findUserByLessons(List<Lesson> lessons);
    Optional<User> findByName(String name);
    @Query("SELECT u.name FROM User u WHERE u.email = :email")
    String findNameByEmail(String email);
}
