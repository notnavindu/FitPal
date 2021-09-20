package com.unicodedev.fitpal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.unicodedev.fitpal.dietplan.DietPlanHome;
import com.unicodedev.fitpal.forum.ForumMain;
import com.unicodedev.fitpal.social.SocialHome;
import com.unicodedev.fitpal.workout.WorkoutPlan;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    TextView hello_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            String name= user.getDisplayName();
            hello_text = findViewById(R.id.helloText);
            hello_text.setText("Hello \n" + name);

            ImageView socialButton = (ImageView) findViewById(R.id.home_social_button);
            ImageView dietPlanButton = (ImageView) findViewById(R.id.home_diet_button);
            ImageView workoutPlanButton = (ImageView) findViewById(R.id.home_workout_button);
            ImageView forumButton = (ImageView) findViewById(R.id.home_forum_button);

            socialButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), SocialHome.class);
                    startActivity(i);
                }
            });

            dietPlanButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), DietPlanHome.class);
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
                    Intent i = new Intent(getApplicationContext(), ForumMain.class);
                    startActivity(i);
                }
            });
        }



    }
}
