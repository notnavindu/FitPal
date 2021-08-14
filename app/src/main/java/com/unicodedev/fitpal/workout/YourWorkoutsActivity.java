package com.unicodedev.fitpal.workout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.unicodedev.fitpal.R;

public class YourWorkoutsActivity extends AppCompatActivity {

    private LinearLayout singleWorkout;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_your_workouts);

        singleWorkout = findViewById(R.id.singleWorkout);

        floatingActionButton = findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(YourWorkoutsActivity.this, AddWorkoutActivity.class);
                startActivity(intent);
            }
        });

        singleWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(YourWorkoutsActivity.this, UserExercisesActivity.class);
                startActivity(intent);
            }
        });
    }
}