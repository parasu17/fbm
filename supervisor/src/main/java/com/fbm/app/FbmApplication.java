package com.fbm.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { 
		"com.fbm.mgmt.supervisor.controllers", 
		"com.fbm.mgmt.supervisor.service.impl", 
		"com.fbm.mgmt.supervisor.dao.impl",
		"com.fbm.security"
		} )
public class FbmApplication {

    public static void main(String[] args) {
        SpringApplication.run(FbmApplication.class, args);
    }
}
