package com.unicodedev.fitpal.dietplan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.unicodedev.fitpal.R;

public class SelectDietPlan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dietplan_select_plan);

        View addFoodBtn = findViewById(R.id.start_diet_btn);
        addFoodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Plan.class);
                startActivity(i);
            }
        });
    }
}