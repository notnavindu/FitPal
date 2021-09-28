package com.unicodedev.fitpal.dietplan;

import static org.junit.Assert.*;

import org.junit.Test;

public class CalculationsTest {

    @Test
    public void calculateFatPercentageMale() {
        double inputH, inputW, inputH1, inputH2, bmi, output, expected, delta;
        int inputA;
        inputH = 150;
        inputW = 102;
        inputA = 20;
        delta = .1;
        inputH1 = inputH/100;
        inputH2 = Math.pow(inputH1, 2);
        bmi = inputW/inputH2;
        expected = (1.20 * bmi) + (0.23 * inputA ) - 16.2;



        Calculations calc = new Calculations();
        output = calc.calculateFatPercentageMale(inputW,inputH, inputA);

        assertEquals(expected, output, delta);

    }

    @Test
    public void calculateFatPercentageFemale() {
        double inputH, inputW, inputH1, inputH2, bmi, output, expected, delta;
        int inputA;
        inputH = 150;
        inputW = 102;
        inputA = 20;
        delta = .1;
        inputH1 = inputH/100;
        inputH2 = Math.pow(inputH1, 2);
        bmi = inputW/inputH2;
        expected = (1.20 * bmi) + (0.23 * inputA ) - 5.4;



        Calculations calc = new Calculations();
        output = calc.calculateFatPercentageFemale(inputW,inputH, inputA);

        assertEquals(expected, output, delta);
    }

    @Test
    public void calculateCaloriesBeginnerMale() {
        double inputH, inputW, bmr, cal, output, expected, delta;
        int inputA;
        inputH = 150;
        inputW = 102;
        inputA = 20;
        delta = .1;
        bmr = 83.362 + (13.397 * inputW) + (4.799 * inputH) - (5.677 * inputA);
        cal = bmr * 1.5;
        expected = cal - 100;

        Calculations calc = new Calculations();
        output = calc.calculateCaloriesBeginnerMale(inputW,inputH, inputA);

        assertEquals(expected, output, delta);
    }

    @Test
    public void calculateCaloriesModerateMale() {
        double inputH, inputW, bmr, cal, output, expected, delta;
        int inputA;
        inputH = 150;
        inputW = 102;
        inputA = 20;
        delta = .1;
        bmr = 83.362 + (13.397 * inputW) + (4.799 * inputH) - (5.677 * inputA);
        cal = bmr * 1.5;
        expected = cal - 300;

        Calculations calc = new Calculations();
        output = calc.calculateCaloriesModerateMale(inputW,inputH, inputA);

        assertEquals(expected, output, delta);
    }

    @Test
    public void calculateCaloriesBeastMale() {
        double inputH, inputW, bmr, cal, output, expected, delta;
        int inputA;
        inputH = 150;
        inputW = 102;
        inputA = 20;
        delta = .1;
        bmr = 83.362 + (13.397 * inputW) + (4.799 * inputH) - (5.677 * inputA);
        cal = bmr * 1.5;
        expected = cal - 100;

        Calculations calc = new Calculations();
        output = calc.calculateCaloriesBeastMale(inputW,inputH, inputA);

        assertEquals(expected, output, delta);
    }

    @Test
    public void calculateCaloriesBeginnerFemale() {
        double inputH, inputW, bmr, cal, output, expected, delta;
        int inputA;
        inputH = 150;
        inputW = 102;
        inputA = 20;
        delta = .1;
        bmr =  447.593 + (9.247 * inputW) + (3.098 * inputH) - (4.330 * inputA);
        cal = bmr * 1.5;
        expected = cal - 100;

        Calculations calc = new Calculations();
        output = calc.calculateCaloriesModerateFemale(inputW,inputH, inputA);

        assertEquals(expected, output, delta);
    }
    @Test
    public void calculateCaloriesModerateFemale() {
        double inputH, inputW, bmr, cal, output, expected, delta;
        int inputA;
        inputH = 150;
        inputW = 102;
        inputA = 20;
        delta = .1;
        bmr =  447.593 + (9.247 * inputW) + (3.098 * inputH) - (4.330 * inputA);
        cal = bmr * 1.5;
        expected = cal - 300;

        Calculations calc = new Calculations();
        output = calc.calculateCaloriesModerateFemale(inputW,inputH, inputA);

        assertEquals(expected, output, delta);
    }

    @Test
    public void calculateCaloriesBeastFemale() {
        double inputH, inputW, bmr, cal, output, expected, delta;
        int inputA;
        inputH = 150;
        inputW = 102;
        inputA = 20;
        delta = .1;
        bmr =  447.593 + (9.247 * inputW) + (3.098 * inputH) - (4.330 * inputA);
        cal = bmr * 1.5;
        expected = cal - 600;

        Calculations calc = new Calculations();
        output = calc.calculateCaloriesBeastFemale(inputW,inputH, inputA);

        assertEquals(expected, output, delta);
    }
}