package com.example.gymweb.Repositories;

import com.example.gymweb.Entities.Lesson;
import com.example.gymweb.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonsRepository extends JpaRepository<Lesson,Long> {
    List<Lesson>findLessonBySport(String sport);
    //List<Lesson>findLessonByTeacherAndSport(User teacher, String sport);
}
