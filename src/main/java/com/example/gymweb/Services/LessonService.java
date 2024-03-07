package com.example.gymweb.Services;

import com.example.gymweb.Entities.Lesson;

import java.util.ArrayList;
import java.util.List;

public class LessonService {
    private List<Lesson>allLessons=new ArrayList<>();

    //show the lessons are available
    public Iterable<Lesson> getAvailableLessons(){
        return allLessons;
    }
}
