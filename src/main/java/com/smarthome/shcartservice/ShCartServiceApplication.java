package com.smarthome.shcartservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableEurekaClient
@EnableJpaRepositories("com.smarthome.shcartservice.repo")
public class ShCartServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShCartServiceApplication.class, args);
	}

}
