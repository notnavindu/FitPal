package com.unicodedev.fitpal.dietplan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.unicodedev.fitpal.R;

public class DietPlanHome extends AppCompatActivity {

    private Button startBtn, fatCalcBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dietplan_main);

        fatCalcBtn = findViewById(R.id.fat_calculate_btn);
        startBtn = findViewById(R.id.start_btn);

        fatCalcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), FatCalculator.class);
                startActivity(i);
            }
        });

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SelectDietPlan.class);
                startActivity(i);
            }
        });
    }

}
