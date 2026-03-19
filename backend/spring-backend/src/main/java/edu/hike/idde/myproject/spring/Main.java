package edu.hike.idde.myproject.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import edu.hike.idde.myproject.spring.service.HikeService;

@Slf4j
@SpringBootApplication
@ComponentScan
public class Main {
    @Autowired
    private HikeService service;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            var cakes = service.readAll();
            for (var cake : cakes) {
                log.info(cake.toString());
            }
        };
    }
}
