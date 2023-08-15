package com.ping.petservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ping.petservice.mapper")
public class PetserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetserviceApplication.class, args);
    }

}
