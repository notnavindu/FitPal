package com.unicodedev.fitpal.dietplan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.unicodedev.fitpal.R;

public class DietPlan extends AppCompatActivity {

    private FloatingActionButton addFoodBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dietplan_diet_plan);

        addFoodBtn = findViewById(R.id.add_food_btn);

        addFoodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddFood.class);
                startActivity(i);
            }
        });
    }
}