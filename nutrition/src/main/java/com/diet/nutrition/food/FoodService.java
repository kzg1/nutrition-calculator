package com.diet.nutrition.food;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class FoodService {

	private final FoodRepository foodRepository;

	public FoodService(FoodRepository foodRepository) {
		this.foodRepository = foodRepository;
	}

	public List<Food> getFoods() {
		return this.foodRepository.findAll();
	}

	// Determinant calculation for 3x3 matrix
	public double determinantCalc(double[][] t) {
		double det = t[0][0] * (t[1][1] * t[2][2] - t[1][2] * t[2][1])
				- t[0][1] * (t[1][0] * t[2][2] - t[1][2] * t[2][0]) + t[0][2] * (t[1][0] * t[2][1] - t[1][1] * t[2][0]);
		return det;
	}

	// Switching a column of 3x3 matrix
	public double[][] columnSwitch(double[][] input, double[] change, int x) {
		double[][] toReturn = new double[3][3];

		for (int i = 0; i < 3; i++) {
			for (int y = 0; y < 3; y++) {
				toReturn[i][y] = input[i][y];
			}
		}

		for (int i = 0; i < 3; i++) {
			toReturn[i][x] = change[i];
		}
		return toReturn;
	}

	// Cramer's rule, to determine nutrition values
	public double[] cramersRule(double[][] eqs, double[] results) {
		double D = determinantCalc(eqs);
		double Dx = determinantCalc(columnSwitch(eqs, results, 0));
		double Dy = determinantCalc(columnSwitch(eqs, results, 1));
		double Dz = determinantCalc(columnSwitch(eqs, results, 2));

		return new double[] { Dx / D, Dy / D, Dz / D };
	}

	// returns an array first value: ch, second: prot, third: fat
	public double[] nutCalc(double ch, double fat, double prot, Food inputCh, Food inputProt, Food inputFat) {

		Food maxCh = inputCh;
		Food maxProt = inputProt;
		Food maxFat = inputFat;

		double[][] eqs = { { maxCh.getCh(), maxProt.getCh(), maxFat.getCh() },
				{ maxCh.getFat(), maxProt.getFat(), maxFat.getFat() },
				{ maxCh.getProt(), maxProt.getProt(), maxFat.getProt() } };

		double[] results = { ch, fat, prot };

		return cramersRule(eqs, results);

	}

	public Food findFoodByName(String name) {
		return this.foodRepository.findAll().stream().filter(food -> food.getName().equals(name)).findFirst().get();
	}

	public Food combineFoods(Food food1, Food food2, int percentage) {
		double ch = food1.getCh() * percentage / 100 + food2.getCh() * (100 - percentage) / 100;
		double prot = food1.getProt() * percentage / 100 + food2.getProt() * (100 - percentage) / 100;
		double fat = food1.getFat() * percentage / 100 + food2.getFat() * (100 - percentage) / 100;
		return new Food(food1.getName() + "," + food2.getName(), prot, ch, fat);
	}



}
