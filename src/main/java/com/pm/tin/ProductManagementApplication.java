package com.pm.tin;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan
@EnableMongoRepositories(basePackages = "com.baeldung.repository")
public class ProductManagementApplication {
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}
