package com.diet.nutrition.food;

import java.text.DecimalFormat;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FoodController {

	private FoodService foodService;
	private static final DecimalFormat df = new DecimalFormat("0.00");

	public FoodController(FoodService foodService) {
		this.foodService = foodService;
	}

	@GetMapping("/foods")
	public ModelAndView getFoods() {
		ModelAndView mav = new ModelAndView("foods");
		List<Food> list = this.foodService.getFoods();
		mav.addObject("foods", list);
		return mav;
	}

	@GetMapping({ "/", "/info" })
	public ModelAndView Info() {
		ModelAndView mav = new ModelAndView("info");
		return mav;
	}

	@GetMapping("/selector")
	public ModelAndView nutritionCalcSelector() {
		ModelAndView mav = new ModelAndView("selector");
		mav.addObject("foods_prot", this.foodService.getFoods().stream().filter(food -> food.getProt() > 10));
		mav.addObject("foods_prot2", this.foodService.getFoods().stream().filter(food -> food.getProt() > 10));
		mav.addObject("foods_ch", this.foodService.getFoods().stream().filter(food -> food.getCh() > 10));
		mav.addObject("foods_ch2", this.foodService.getFoods().stream().filter(food -> food.getCh() > 10));
		mav.addObject("foods_fat", this.foodService.getFoods().stream().filter(food -> food.getFat() > 10));
		mav.addObject("foods_fat2", this.foodService.getFoods().stream().filter(food -> food.getFat() > 10));
		return mav;
	}

	@PostMapping("/selector")
	public ModelAndView nutritionCalculated(String ch1, String prot1, String fat1, int inputch, int inputprot,
			int inputfat, String ch2, String prot2, String fat2, int ch1perc, int prot1perc, int fat1perc) {
		ModelAndView mav = new ModelAndView("result");
		// variables to check how many inputs are there
		int chinput = 1;
		int protinput = 1;
		int fatinput = 1;

		Food inputchObj = this.foodService.findFoodByName(ch1);
		if (ch1perc > 0 && ch1perc < 100) {
			inputchObj = this.foodService.combineFoods(this.foodService.findFoodByName(ch1),
					this.foodService.findFoodByName(ch2), ch1perc);
			chinput++;
		}

		Food inputprotObj = this.foodService.findFoodByName(prot1);

		if (prot1perc > 0 && prot1perc < 100) {
			inputprotObj = this.foodService.combineFoods(this.foodService.findFoodByName(prot1),
					this.foodService.findFoodByName(prot2), prot1perc);
			protinput++;
		}
		Food inputfatObj = this.foodService.findFoodByName(fat1);

		if (fat1perc > 0 && fat1perc < 100) {
			inputfatObj = this.foodService.combineFoods(this.foodService.findFoodByName(fat1),
					this.foodService.findFoodByName(fat2), fat1perc);
			fatinput++;
		}

		// returns an array first value: ch, second: prot, third: fat

		double[] result = this.foodService.nutCalc(inputch, inputfat, inputprot, inputchObj, inputprotObj, inputfatObj);

		if (result[0] < 0 || result[1] < 0 || result[2] < 0) {
			ModelAndView mav2 = new ModelAndView("error");
			return mav2;
		}
		if (chinput == 1) {
			mav.addObject("resultCh", inputchObj.getName() + ": " + df.format(result[0] * 100) + " grams");
		} else if (chinput == 2) {
			mav.addObject("resultCh",
					inputchObj.getName().split(",")[0] + ": " + df.format(result[0] * ch1perc) + " grams");
			mav.addObject("resultCh2",
					inputchObj.getName().split(",")[1] + ": " + df.format(result[0] * (100 - ch1perc)) + " grams");
		}
		if (protinput == 1) {
			mav.addObject("resultProt", inputprotObj.getName() + ": " + df.format(result[1] * 100) + " grams");
		} else if (protinput == 2) {
			mav.addObject("resultProt",
					inputprotObj.getName().split(",")[0] + ": " + df.format(result[1] * prot1perc) + " grams");
			mav.addObject("resultProt2",
					inputprotObj.getName().split(",")[1] + ": " + df.format(result[1] * (100 - prot1perc)) + " grams");
		}
		if (fatinput == 1) {
			mav.addObject("resultFat", inputfatObj.getName() + ": " + df.format(result[2] * 100) + " grams");
		} else if (fatinput == 2) {
			mav.addObject("resultFat",
					inputfatObj.getName().split(",")[0] + ": " + df.format(result[2] * fat1perc) + " grams");
			mav.addObject("resultFat2",
					inputfatObj.getName().split(",")[1] + ": " + df.format(result[2] * (100 - fat1perc)) + " grams");
		}
		return mav;
	}

}
