package com.furansujin.demosprinhexagonalcucumber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication()
@ComponentScan(basePackages = {"com.furansujin.demosprinhexagonalcucumber.infrastructure"})
@EnableScheduling
public class DemoSprinHexagonalCucumberApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoSprinHexagonalCucumberApplication.class, args);
    }

}
