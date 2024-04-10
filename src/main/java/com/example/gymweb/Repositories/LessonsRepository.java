package com.example.gymweb.Repositories;

import com.example.gymweb.Entities.Lesson;
import com.example.gymweb.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface LessonsRepository extends JpaRepository<Lesson,Long> {
    List<Lesson>findLessonBySport(String sport);
    List<Lesson>findLessonByTeacher(User teacher);
    List<Lesson> findLessonByUsers(List<User> users);

}
