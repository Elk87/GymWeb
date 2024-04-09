package com.example.gymweb.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;
@Getter
@Setter
@Entity
@NoArgsConstructor
public class Lesson {
    @NotNull
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private User teacher;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime finishTime;

    @NotNull
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
