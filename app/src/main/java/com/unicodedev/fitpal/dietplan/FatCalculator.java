package com.unicodedev.fitpal.dietplan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.unicodedev.fitpal.R;

public class FatCalculator extends AppCompatActivity {
    EditText gender_input;
    EditText age_input;
    EditText height_input;
    EditText weight_input;
    TextView fat_percentage_output;
    Button update_btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dietplan_fat_calculator);

        gender_input = findViewById(R.id.gender_input);
        age_input = findViewById(R.id.age_input);
        height_input = findViewById(R.id.height_input);
        weight_input = findViewById(R.id.weight_input);
        fat_percentage_output = findViewById(R.id.fat_percentage_output);
        update_btn = findViewById(R.id.update_btn);
    }

    @Override
    protected void onResume() {
        super.onResume();
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateFatPercentage();
            }
        });
    }

    public void calculateFatPercentage() {
        Calculations calc = new Calculations();
        String gender = gender_input.getText().toString();
        String age = age_input.getText().toString();
        String height = height_input.getText().toString();
        String weight = weight_input.getText().toString();

        if(TextUtils.isEmpty(gender) || TextUtils.isEmpty(age) || TextUtils.isEmpty(height) || TextUtils.isEmpty(weight)){
            Toast.makeText(this, "Please fill the field's", Toast.LENGTH_SHORT).show();
        }else{
            Double fatPercentage;
            Double finalfatPercentage;
            Double weightTemp = Double.parseDouble(weight);
            Double heightTemp = Double.parseDouble(height);
            Integer ageTemp = Integer.parseInt(age);
            if(gender.equals("male") || gender.equals("Male")){
                fatPercentage = calc.calculateFatPercentageMale(weightTemp, heightTemp, ageTemp);
                finalfatPercentage = Math.round(fatPercentage * 100.00) / 100.00;
            }else if(gender.equals("female")|| gender.equals("Female")){
                fatPercentage = calc.calculateFatPercentageFemale(weightTemp, heightTemp, ageTemp);
                finalfatPercentage = Math.round(fatPercentage * 100.00) / 100.00;
            }else{
                Toast.makeText(this, "Enter a correct gender", Toast.LENGTH_SHORT).show();
                finalfatPercentage = 0.0;
            }
            fat_percentage_output.setText(new Double(finalfatPercentage).toString());
        }
    }
}
