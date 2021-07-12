package com.example.pactconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class PactConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PactConsumerApplication.class, args);
	}

}
