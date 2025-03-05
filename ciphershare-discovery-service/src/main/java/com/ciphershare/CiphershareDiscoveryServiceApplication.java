package com.ciphershare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@EnableEurekaServer
@SpringBootApplication
public class CiphershareDiscoveryServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(CiphershareDiscoveryServiceApplication.class, args);
	}
}
