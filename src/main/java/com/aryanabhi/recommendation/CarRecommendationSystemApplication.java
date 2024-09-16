package com.aryanabhi.recommendation;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Log4j2
@SpringBootApplication
public class CarRecommendationSystemApplication {

	public static void main(String[] args) {
		log.info("Application starting up......");
		SpringApplication.run(CarRecommendationSystemApplication.class, args);
	}

}
