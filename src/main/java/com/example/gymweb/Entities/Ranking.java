package com.example.gymweb.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Ranking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    private String comment;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Ranking(long id, String comment, User user) {
        this.id = id;
        this.comment = comment;
        this.user = user;
    }
}

