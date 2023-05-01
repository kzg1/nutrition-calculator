package com.diet.nutrition.food;

//
//Source for testing: https://www.purplemath.com/modules/cramers.htm
//
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class FoodServiceTest {

	@Test
	void determinantCalcTest() {
		double[][] testMatrix = { { 2, 1, 1 }, { 1, -1, -1 }, { 1, 2, 1 } };
		var foodService = new FoodService(null);
		assertEquals(3, foodService.determinantCalc(testMatrix));

	}

	@Test
	void columnSwitchTest() {
		double[][] testMatrix = { { 2, 1, 1 }, { 1, -1, -1 }, { 1, 2, 1 } };
		double[] testColumn = { 3, 0, 0 };
		var foodService = new FoodService(null);
		double[][] modifiedMatrix = foodService.columnSwitch(testMatrix, testColumn, 0);
		double[] switchedColumn = { modifiedMatrix[0][0], modifiedMatrix[1][0], modifiedMatrix[2][0] };
		assertArrayEquals(testColumn, switchedColumn);

		// calculate and test Dx
		double[][] dxMatrix = foodService.columnSwitch(testMatrix, testColumn, 0);
		double Dx = foodService.determinantCalc(dxMatrix);
		assertEquals(3, Dx);
		// calculate and test Dy
		double[][] dyMatrix = foodService.columnSwitch(testMatrix, testColumn, 1);
		double Dy = foodService.determinantCalc(dyMatrix);
		assertEquals(-6, Dy);
		// calculate and test Dz
		double[][] dzMatrix = foodService.columnSwitch(testMatrix, testColumn, 2);
		double Dz = foodService.determinantCalc(dzMatrix);
		assertEquals(9, Dz);
	}

	@Test
	void cramersRuleTest() {
		double[][] testMatrix = { { 2, 1, 1 }, { 1, -1, -1 }, { 1, 2, 1 } };
		double[] testColumn = { 3, 0, 0 };
		var foodService = new FoodService(null);

		assertArrayEquals(new double[] { 1, -2, 3 }, foodService.cramersRule(testMatrix, testColumn));

	}

}
