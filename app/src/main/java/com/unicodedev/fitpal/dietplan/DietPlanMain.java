package com.unicodedev.fitpal.dietplan;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.unicodedev.fitpal.R;

public class DietPlanMain extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dietplan_main);

        View fatCalcBtn = findViewById(R.id.fat_calculate_btn);
        fatCalcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent i = new Intent(getApplicationContext(), FatCalculator.class);
               startActivity(i);
            }
        });

        View dietPlanBtn = findViewById(R.id.start_btn);
        dietPlanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SelectDietPlan.class);
                startActivity(i);
            }
        });
    }

}
