package javaClasses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Главный класс приложения.
 */
@SpringBootApplication
@ComponentScan(basePackages = {"javaClasses.controller",
        "javaClasses.service", "javaClasses.entity",
        "javaClasses.config", "javaClasses.repository"})
@EnableJpaRepositories(value = "javaClasses.repository")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
