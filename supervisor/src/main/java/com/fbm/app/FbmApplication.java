package com.fbm.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.fbm.mgmt.supervisor.controllers" } )
public class FbmApplication {

    public static void main(String[] args) {
        SpringApplication.run(FbmApplication.class, args);
    }
}
