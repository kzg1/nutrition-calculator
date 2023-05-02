This application made for calculating foods macronutrients.

Basic concept: If you're on a strict diet with pre-defined macronutrient values and want to prepare your meals for the whole day, you can set your protein, carbohydrate and fat sources and the software will calculate how much of each to prepare.

Since all foods generally contain all 3 macronutrients, we have to take into account the input values of each food for each macronutrient.

To solve this problem, I implemented the Cramer's rule (https://en.wikipedia.org/wiki/Cramer%27s_rule).
Using this formula we can easily calculate the values.

Currently you can choose up to 2 foods for each macronutrients, and also you can set how much do you want from each food, using percentages.

Requirements for the program:
<ul>
<li>Java 17+</li>
<li>PostgresSQL with a database named "nutrition"</li></li></li>
<li>Lombok</li></li>
<li>foods.csv which contains the input values for your foods. (already attached an example with a few foods. formula: name,protein,ch,fat)</li>
</ul>
<hr>
Example, and check in excel:

<img src="https://i.imgur.com/wO9LPeP.png">
