package com.food.predictor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.food"})
public class FoodPredictorApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodPredictorApplication.class, args);
	}

}
