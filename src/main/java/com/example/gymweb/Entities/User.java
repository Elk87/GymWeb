package com.example.gymweb.Entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class User {
    @NotNull
    private String name;
    @NotNull
    private String password;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id=-1;
    @NotNull
    private String DNI;
    @NotNull
    private String email;
    @NotNull
    private int phoneNumber;
    @NotNull
    private int age;
    @Nullable
    private String image;
    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.REGISTERED_USER;
    @Nullable
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ranking> comments;
    @Nullable
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinTable(
            name = "user_lessons",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "lesson_id")
    )
    private List<Lesson> lessons;
    //constructor
    public User(String name, String password, String DNI, String email, int phoneNumber, int age) {
        this.name = name;
        this.password=password;
        this.DNI=DNI;
        this.email=email;
        this.age=age;
        this.phoneNumber=phoneNumber;
        this.comments=new ArrayList<>();
        this.lessons=new ArrayList<>();
    }
    public User(String name, String password, String DNI, String email, int phoneNumber, int age, String image) {
        this.name = name;
        this.password=password;
        this.DNI=DNI;
        this.email=email;
        this.age=age;
        this.phoneNumber=phoneNumber;
        this.image=image;
        this.comments=new ArrayList<>();
        this.lessons=new ArrayList<>();
    }

    @Nullable
    public String getImage() {
        return image;
    }

    public void setImage(@Nullable String image) {
        this.image = image;
    }

    public User(String name) {
        this.name = name;
    }

    //this method is for the user to book classes
    public void addLessons(Lesson lesson){
        this.lessons.add(lesson);
    }

    //this method is for the user to delete classes
    public void deleteLessons(Lesson lesson){
        this.lessons.remove(lesson);
    }

    //this method is for the user to show the lessons
    public List<Lesson> showLessons(){
        return lessons;
    }
    public void addRanking(Ranking ranking){
        this.comments.add(ranking);
        ranking.setUser(this);
    }
    public void deleteRanking(Ranking ranking){
        this.comments.remove(ranking);
    }
    public List<Ranking> showRanking(){
        return comments;
    }



}
