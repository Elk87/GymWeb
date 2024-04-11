package com.example.gymweb.Entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Lesson {
    @NotNull
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "teacher_id")
    private User teacher;
    @Nullable
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private List<User> users;
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
 public Lesson(User teacher, LocalTime startTime, LocalTime finishTime, String sport) {
  this.teacher = teacher;
  this.startTime = startTime;
  this.finishTime = finishTime;
  this.sport = sport;
  this.users=new ArrayList<>();
 }
    public Lesson( LocalTime startTime, LocalTime finishTime, String sport) {
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.sport = sport;
        this.users=new ArrayList<>();
    }
    public Lesson(User teacher, LocalTime startTime, LocalTime finishTime, String sport,List<User> users) {
        this.teacher = teacher;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.sport = sport;
        this.users=users;
    }


}
