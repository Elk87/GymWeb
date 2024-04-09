package com.example.gymweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication()
public class GymWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(GymWebApplication.class, args);
    }
}
