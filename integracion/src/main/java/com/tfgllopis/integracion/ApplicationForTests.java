package com.tfgllopis.integracion;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;

@ComponentScan
@Configuration
@TestPropertySource("classpath:/resources/application.properties")
public class ApplicationForTests {
 
    public static void main(String[] args) {
        SpringApplication.run(ApplicationForTests.class, args);
    }
}