package com.ciphershare.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.ciphershare.file.client")
public class FileServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(FileServiceApplication.class, args);
	}
}
