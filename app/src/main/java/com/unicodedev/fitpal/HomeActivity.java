package com.unicodedev.fitpal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ImageView socialButton = (ImageView) findViewById(R.id.home_social_button);
        ImageView dietPlanButton = (ImageView) findViewById(R.id.home_diet_button);
        ImageView workoutPlanButton = (ImageView) findViewById(R.id.home_workout_button);
        ImageView forumButton = (ImageView) findViewById(R.id.home_forum_button);

        socialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Social.class);
                startActivity(i);
            }
        });

        dietPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), DietPlan.class);
                startActivity(i);
            }
        });

        workoutPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), WorkoutPlan.class);
                startActivity(i);
            }
        });

        forumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Forum.class);
                startActivity(i);
            }
        });
    }
}