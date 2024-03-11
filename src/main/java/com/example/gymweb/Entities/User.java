package com.example.gymweb.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
//@Entity
public class User {
    private String name;
    private String password;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id=-1;
    private String DNI;
    private String email;
    private int phoneNumber;
    private int age;
    private String image;
   // private String Role;
    //@OneToMany
    private List<Ranking> comments;
    //@ManyToMany
    private List<Lesson> lessons;
    //constructor
    public User(String name, String password, String DNI, String email, int phoneNumber, int age) {
        this.name = name;
        this.password=password;
        this.DNI=DNI;
        this.email=email;
        this.age=age;
        this.phoneNumber=phoneNumber;
       // this.Role="Admin";
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
        // this.Role="Admin";
        this.lessons=new ArrayList<>();
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
    }
    public void deleteRanking(Ranking ranking){
        this.comments.add(ranking);
    }
    public List<Ranking> showRanking(){
        return comments;
    }



}
