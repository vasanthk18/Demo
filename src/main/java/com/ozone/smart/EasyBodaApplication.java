package com.ozone.smart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EasyBodaApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasyBodaApplication.class, args);
	}

}
