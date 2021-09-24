package com.unicodedev.fitpal.workout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.unicodedev.fitpal.HomeActivity;
import com.unicodedev.fitpal.Profile;
import com.unicodedev.fitpal.R;
import com.unicodedev.fitpal.forum.ForumMain;
import com.unicodedev.fitpal.social.SocialHome;

public class WorkoutPlan extends AppCompatActivity {

    private ImageView upperBody, lowerBody, fullBody, abWorkout, homeWorkout, cardio, yourWorkouts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_main);

        //Bottom navigation bar
        BottomNavigationView navbar = findViewById(R.id.bottom_navigation);
        navbar.setSelectedItemId(R.id.other);

        navbar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.social:
                        startActivity(new Intent(getApplicationContext(), SocialHome.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.forum:
                        startActivity(new Intent(getApplicationContext(), ForumMain.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), Profile.class));
                        overridePendingTransition(0,0);
                        return true;

                    default: return true;
                }
            }
        });

        initViews();

        upperBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WorkoutPlan.this, ExerciseActivity.class);
                intent.putExtra("pageTitle", "Upper Body");
                startActivity(intent);
            }
        });
        lowerBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WorkoutPlan.this, ExerciseActivity.class);
                intent.putExtra("pageTitle", "Lower Body");
                startActivity(intent);
            }
        });
        fullBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WorkoutPlan.this, ExerciseActivity.class);
                intent.putExtra("pageTitle", "Full Body");
                startActivity(intent);
            }
        });
        abWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WorkoutPlan.this, ExerciseActivity.class);
                intent.putExtra("pageTitle", "Ab Workout");
                startActivity(intent);
            }
        });
        homeWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WorkoutPlan.this, ExerciseActivity.class);
                intent.putExtra("pageTitle", "Home Workout");
                startActivity(intent);
            }
        });
        cardio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WorkoutPlan.this, ExerciseActivity.class);
                intent.putExtra("pageTitle", "Cardio");
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