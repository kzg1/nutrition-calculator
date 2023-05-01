package com.diet.nutrition.food;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FoodConfig {

	@Bean
	CommandLineRunner commandLineRunner(FoodRepository repository) {

		return args -> {

			// Function to create object from each input line
			Function<String[], Food> toFood = new Function<String[], Food>() {

				@Override
				public Food apply(String[] t) {

					return new Food(t[0], Double.valueOf(t[1]), Double.valueOf(t[2]), Double.valueOf(t[3]));
				}
			};

			repository.saveAll(Files.lines(Paths.get("foods.csv")).map(line -> line.split(",")).map(toFood)
					.collect(Collectors.toList()));

		};
	}
}
