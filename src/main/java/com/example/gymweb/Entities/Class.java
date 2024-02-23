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
public class Class {
   // @ManyToOne
    private User teacher;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private LocalTime startTime;
    private LocalTime finishTime;
    private String sport;
}
