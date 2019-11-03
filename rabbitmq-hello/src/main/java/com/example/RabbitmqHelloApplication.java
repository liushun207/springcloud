package com.example;

import com.example.config.RabbitConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
public class RabbitmqHelloApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqHelloApplication.class, args);
    }

}
