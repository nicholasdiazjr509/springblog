package com.codeup.springblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@EnableJpaRepositories("com.codeup.springblog.repositories")
@SpringBootApplication
public class SpringblogApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringblogApplication.class, args);
    }
}
