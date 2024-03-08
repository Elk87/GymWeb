package com.example.gymweb.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
@Getter
@Setter
//@Entity
@NoArgsConstructor
public class Lesson {
   // @ManyToOne
    private User teacher;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private LocalTime startTime;
    private LocalTime finishTime;
    private String sport;

//constructor
 public Lesson(User teacher, long id, LocalTime startTime, LocalTime finishTime, String sport) {
  this.teacher = teacher;
  this.id = id;
  this.startTime = startTime;
  this.finishTime = finishTime;
  this.sport = sport;
 }

}
