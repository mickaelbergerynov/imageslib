package com.ynov.webfullstack.imagelib;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.ynov.webfullstack.imagelib.repositories.ImageRepository;

@Configuration
public class LoadDatabase {
    
    @Bean
    CommandLineRunner initDatabase(ImageRepository imageRepo) {
        return args -> { };
    } 
}
