package com.unicodedev.fitpal.workout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import com.unicodedev.fitpal.R;

public class WorkoutPlan extends AppCompatActivity {

    private ImageView upperBody, lowerBody, fullBody, abWorkout, homeWorkout, cardio, yourWorkouts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_main);

        initViews();

        upperBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WorkoutPlan.this, ExerciseActivity.class);
                startActivity(intent);
            }
        });

        yourWorkouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WorkoutPlan.this, YourWorkoutsActivity.class);
                startActivity(intent);
            }
        });

//        TODO: add other click actions
    }

    private void initViews() {
        upperBody = findViewById(R.id.upperBody);
        lowerBody = findViewById(R.id.lowerBody);
        fullBody = findViewById(R.id.fullbody);
        abWorkout = findViewById(R.id.abworkout);
        homeWorkout = findViewById(R.id.homeWorkout);
        cardio = findViewById(R.id.cardio);
        yourWorkouts = findViewById(R.id.yourWorkouts);
    }


}