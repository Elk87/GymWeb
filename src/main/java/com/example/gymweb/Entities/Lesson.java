package com.example.gymweb.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.sql.Blob;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Lesson {
    @Nullable
    @ManyToOne(cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "teacher_id")
    @JsonManagedReference
    private User teacher;
    @Nullable
    @ManyToMany(cascade={CascadeType.ALL})
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
    private String filePath;

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
    public String getTeacherName(){
     return this.teacher.getName();
    }


}
