package com.pm.tin;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan
@EnableMongoRepositories(basePackages = "com.baeldung.repository")
public class ProductManagementApplication {
    public static void main(String... args) {
        SpringApplication.run(ProductManagementApplication.class, args);
    }
}
