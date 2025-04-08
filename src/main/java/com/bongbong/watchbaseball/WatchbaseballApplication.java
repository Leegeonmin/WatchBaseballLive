package com.bongbong.watchbaseball;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WatchbaseballApplication {

	public static void main(String[] args) {
		SpringApplication.run(WatchbaseballApplication.class, args);
	}

}
