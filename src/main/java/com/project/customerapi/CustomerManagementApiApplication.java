package com.project.customerapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.project.customerapi")

public class CustomerManagementApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerManagementApiApplication.class, args);
	}

}
