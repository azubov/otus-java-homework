package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
    // Страница клиентов
    http://localhost:8080/clients
*/

@SpringBootApplication
public class SpringDataJdbcApp {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataJdbcApp.class, args);
    }
}
