package com.pm.tin;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
//import org.slf4j.impl.StaticLoggerBinder;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@ComponentScan
//@EnableMongoRepositories(basePackages = "com.baeldung.repository")
@Slf4j
public class ProductManagementApplication {
    public static void main(String... args) {
        SpringApplication.run(ProductManagementApplication.class, args);
    }
}
