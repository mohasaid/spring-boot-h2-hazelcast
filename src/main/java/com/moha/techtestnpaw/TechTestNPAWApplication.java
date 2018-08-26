package com.moha.techtestnpaw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TechTestNPAWApplication {

    public static void main(String[] args) {
        SpringApplication.run(TechTestNPAWApplication.class, args);
    }
}
