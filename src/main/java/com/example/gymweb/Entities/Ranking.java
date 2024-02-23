package com.example.gymweb.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@Getter
@Setter
//@Entity
public class Ranking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String comment;
    //@ManyToOne
    private User user;
}
