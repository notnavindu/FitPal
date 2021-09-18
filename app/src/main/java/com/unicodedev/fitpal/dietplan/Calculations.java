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

    protected double calculateCaloriesBeginnerMale(double weight, double height, int age){
        double bmr = 88.362 + (13.397 *  weight) + (4.799 * height) - (5.677 * age);
        double cal = bmr * 1.55;
        return cal-100;
    }

    protected double calculateCaloriesModerateMale(double weight, double height, int age){
        double bmr = 88.362 + (13.397 *  weight) + (4.799 * height) - (5.677 * age);
        double cal = bmr * 1.55;
        return cal-300;
    }

    protected double calculateCaloriesBeastMale(double weight, double height, int age){
        double bmr = 88.362 + (13.397 *  weight) + (4.799 * height) - (5.677 * age);
        double cal = bmr * 1.55;
        return cal-600;
    }

    protected double calculateCaloriesBeginnerFemale(double weight, double height, int age){
        double bmr = 447.593 + (9.247 *  weight) + (3.098 * height) - (4.330 * age);
        double cal = bmr * 1.55;
        return cal-100;
    }

    protected double calculateCaloriesModerateFemale(double weight, double height, int age){
        double bmr = 447.593 + (9.247 *  weight) + (3.098 * height) - (4.330 * age);
        double cal = bmr * 1.55;
        return cal-300;
    }

    protected double calculateCaloriesBeastFemale(double weight, double height, int age){
        double bmr = 447.593 + (9.247 *  weight) + (3.098 * height) - (4.330 * age);
        double cal = bmr * 1.55;
        return cal-600;
    }
}
