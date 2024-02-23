package com.example.gymweb.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private long id;
    private String DNI;
    private String email;
    private int phoneNumber;
    private int age;
    private String Role;
    //@ManyToMany
    private List<Class> classes;

    public User(String name, String password, String DNI, String email, int phoneNumber, int age, String Role) {
        this.name = name;
        this.password=password;
        this.DNI=DNI;
        this.email=email;
        this.age=age;
        this.phoneNumber=phoneNumber;
        this.Role="Admin";
    }


}
