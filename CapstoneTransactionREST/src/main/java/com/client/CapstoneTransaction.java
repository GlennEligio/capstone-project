package com.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com")
@EntityScan("com.model")
@EnableJpaRepositories("com.dao")
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.delegate")
public class CapstoneTransaction {
	public static void main(String[] args) {
		SpringApplication.run(CapstoneTransaction.class, args);
	}
}
