package com.ciphershare.sharing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class CiphershareSharingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CiphershareSharingServiceApplication.class, args);
	}
}
