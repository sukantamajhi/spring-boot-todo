package com.sukanta.springboottodo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class SpringBootTodoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootTodoApplication.class, args);
    }

}
