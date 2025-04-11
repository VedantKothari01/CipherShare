package com.ciphershare.audit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CiphershareAuditServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CiphershareAuditServiceApplication.class, args);
	}

}
