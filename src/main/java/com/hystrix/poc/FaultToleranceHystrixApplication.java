package com.hystrix.poc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FaultToleranceHystrixApplication {

	public static void main(String[] args) {
		SpringApplication.run(FaultToleranceHystrixApplication.class, args);
	}

}
