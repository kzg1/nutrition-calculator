package com.diet.nutrition.food;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "foods")
public class Food {

	@Id
	@SequenceGenerator(name = "food_sequence", sequenceName = "food_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "food_sequence")
	private Long id;
	private String name;
	private double ch;
	private double prot;
	private double fat;
	private boolean selected = false;	

	public Food(String name,double prot, double ch, double fat) {
		this.name = name;
		this.ch = ch;
		this.prot = prot;
		this.fat = fat;
	}

}
