package com.example.ordernode;

import com.example.ordernode.rest.RestApiController;
import com.example.ordernode.service.CustomerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class OrderNodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderNodeApplication.class, args);
    }

}
