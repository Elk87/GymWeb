package com.example.gymweb.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
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
    @JsonProperty("password")
    @JsonIgnore
    private String password;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    private String DNI;
    @NotNull
    @ElementCollection(fetch = FetchType.EAGER)
    @JsonIgnore
    private List<String> roles;
    @NotNull
    private String email;
    @NotNull
    private String phoneNumber;
    @NotNull
    private int age;
    @Lob
    @JsonIgnore
    private Blob imageFile;
    @Nullable
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, orphanRemoval = true)
    @JsonIgnore
    private List<Ranking> comments;

    @Nullable
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinTable(
            name = "user_lessons",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "lesson_id")
    )
    @JsonIgnore
    private List<Lesson> lessons;
    //constructor
    public User(String name, String password, String DNI, String email, String phoneNumber, int age, String role) {
        this.name = name;
        this.password=password;
        this.DNI=DNI;
        this.email=email;
        this.age=age;
        this.phoneNumber=phoneNumber;
        try (InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream("static/img/perfilgenerico.jpg")) {
            byte[] imageData = inputStream.readAllBytes();
            SerialBlob imageBlob = new SerialBlob(imageData);
            this.setImageFile(imageBlob);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        this.comments=new ArrayList<>();
        this.lessons=new ArrayList<>();
        this.roles=List.of(role);
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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
    public void addRole(String role ){
        this.roles.add(role);
    }

}
