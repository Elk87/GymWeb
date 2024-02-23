package com.example.gymweb.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class User {
    private String name;
    private String password;
    @Id
    private String DNI;
    private String email;
    private int phoneNumber;
    private int age;
    private String Role;

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
