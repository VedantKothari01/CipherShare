package com.ciphershare.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients

public class CiphershareUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CiphershareUserServiceApplication.class, args);
	}

}
