package com.urfu.telegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ChadNovelEngineApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChadNovelEngineApplication.class, args);
    }
}
