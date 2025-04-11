package com.ciphershare.audit;

import javax.sql.DataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@SpringBootApplication
@EnableDiscoveryClient
public class CiphershareAuditServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(CiphershareAuditServiceApplication.class, args);
	}

}
