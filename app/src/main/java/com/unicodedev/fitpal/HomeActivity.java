package com.unicodedev.fitpal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import com.unicodedev.fitpal.dietplan.DietPlanHome;
import com.unicodedev.fitpal.forum.ForumMain;
import com.unicodedev.fitpal.social.SocialHome;
import com.unicodedev.fitpal.workout.WorkoutPlan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    TextView hello_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //Bottom navigation bar
        BottomNavigationView navbar = findViewById(R.id.bottom_navigation);
        navbar.setSelectedItemId(R.id.home);

        navbar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.social:
                        startActivity(new Intent(getApplicationContext(),SocialHome.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        return true;
                    case R.id.forum:
                        startActivity(new Intent(getApplicationContext(),ForumMain.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(),Profile.class));
                        overridePendingTransition(0,0);
                        return true;

                    default: return true;
                }
            }
        });




        if(user != null){
            hello_text = findViewById(R.id.helloText);

            ImageView socialButton = (ImageView) findViewById(R.id.home_social_button);
            ImageView dietPlanButton = (ImageView) findViewById(R.id.home_diet_button);
            ImageView workoutPlanButton = (ImageView) findViewById(R.id.home_workout_button);
            ImageView forumButton = (ImageView) findViewById(R.id.home_forum_button);


            DocumentReference docRef = db.collection("Users").document(user.getUid());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            String nickname = document.getString("nickname");
                           hello_text.setText("Hello " + nickname + "!");

                        } else {
                            Log.d("Firestore Log", "No such document");
                        }
                    } else {
                        Log.d("Firestore Log", "get failed with ", task.getException());
                    }
                }
            });



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
