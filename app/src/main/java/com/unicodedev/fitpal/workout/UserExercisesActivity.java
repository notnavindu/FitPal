package com.unicodedev.fitpal.workout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.unicodedev.fitpal.R;

public class UserExercisesActivity extends AppCompatActivity {

    private LinearLayout singleExercise;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_user_exercises);

        singleExercise = findViewById(R.id.singleExercise);
        floatingActionButton = findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserExercisesActivity.this, AddWorkoutActivity.class);
                startActivity(intent);
            }
        });

        singleExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserExercisesActivity.this, UpdateExerciseActivity.class);
                startActivity(intent);
            }
        });
    }
}