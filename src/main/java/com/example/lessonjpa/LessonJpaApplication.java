package com.example.lessonjpa;

import com.example.lessonjpa.controllers.Controller;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LessonJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(LessonJpaApplication.class, args);
    }

    @Bean
    CommandLineRunner run(Controller controller) {
        return args -> controller.mainLoop();
    }
}
