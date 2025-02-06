package com.pets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PetPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetPlatformApplication.class, args);
    }

}