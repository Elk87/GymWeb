package com.example.gymweb.Repositories;

import com.example.gymweb.Entities.Lesson;
import com.example.gymweb.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
@Repository
public interface LessonsRepository extends JpaRepository<Lesson,Long> {
    //List<Lesson>findLessonBySport(String sport);
    List<Lesson>findLessonByTeacher(User teacher);
    List<Lesson> findLessonByUsers(List<User> users);

   /* @Query("SELECT l FROM Lesson l WHERE l.teacher.name = :teacherName AND l.sport = :sport")

    List<Lesson> findByTeacherNameAndSport(String teacherName, String sport);*/
   @Query("SELECT l FROM Lesson l " +
           "WHERE (:teacherName IS NULL OR l.teacher.name = :teacherName) " +
           "AND (l.sport = :sport OR :sport IS NULL)")
   List<Lesson> findByTeacherNameAndSport(@Param("teacherName") String teacherName, @Param("sport") String sport);

   /* void test(){//ponerlo de forma opcional
        query.setParameter("teacherName", teacherName)
    }*/
}
