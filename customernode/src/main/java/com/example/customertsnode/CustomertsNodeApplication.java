package com.example.customertsnode;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication

public class CustomertsNodeApplication {
    private static final Logger logger =  LogManager.getLogger(CustomertsNodeApplication.class.getName());
    public static void main(String[] args) {
        logger.info("CustomerNode was started");
        SpringApplication.run(CustomertsNodeApplication.class, args);
    }

}


