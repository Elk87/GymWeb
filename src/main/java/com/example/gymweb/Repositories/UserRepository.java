package com.example.gymweb.Repositories;
import com.example.gymweb.Entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    List<User> findByPhoneNumberAndEmail(int phoneNumber, String email);
    User findByEmailAndPassword(String email, String password);
    User findByEmail(String email);
}
