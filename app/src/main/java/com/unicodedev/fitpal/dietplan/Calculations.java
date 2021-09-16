package com.unicodedev.fitpal.dietplan;

public class Calculations {

    protected double calculateFatPercentageMale(double weight, double height, int age){
        double height1 = height/100;
        double height2 = Math.pow(height1, 2);
        double bmi = weight/height2;
        return (1.20 * bmi) + (0.23 * age ) - 16.2;
    }

    protected double calculateFatPercentageFemale(double weight, double height, int age){
        double height1 = height/100;
        double height2 = Math.pow(height1, 2);
        double bmi = weight/height2;
        return (1.20 * bmi) + (0.23 * age ) - 5.4;
    }
}
