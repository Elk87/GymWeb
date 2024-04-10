package com.example.gymweb.Repositories;

import com.example.gymweb.Entities.Lesson;
import com.example.gymweb.Entities.User;
import jakarta.persistence.EntityManager;


import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LessonsRepositoryImplem {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Lesson>findLessonBySport(String sport){
        TypedQuery<Lesson> query= entityManager.createQuery
                ("SELECT d FROM Lesson d WHERE d.sport=:sport",Lesson.class);
        return query.setParameter("sport",sport).getResultList();
    }
    public List<Lesson>findLessonByTeacher(User teacher, String sport){
        TypedQuery<Lesson> query= entityManager.createQuery
                ("SELECT d FROM Lesson d WHERE d.teacher=:teacher",Lesson.class);
        return query.setParameter("teacher",teacher).getResultList();
    }
    public List<Lesson>findLessonByUsers(List<User> user){
        TypedQuery<Lesson> query= entityManager.createQuery
                ("SELECT d FROM Lesson d WHERE d.users=:user",Lesson.class);
        return query.setParameter("user",user).getResultList();
    }
}
