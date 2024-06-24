package com.home.LedScreensServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LedScreensServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LedScreensServerApplication.class, args);
	}

}
