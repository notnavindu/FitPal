package com.unicodedev.fitpal.workout;

import static com.unicodedev.fitpal.R.id;
import static com.unicodedev.fitpal.R.layout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.unicodedev.adapters.DefaultExerciseAdapter;
import com.unicodedev.fitpal.HomeActivity;
import com.unicodedev.fitpal.Profile;
import com.unicodedev.fitpal.R;
import com.unicodedev.fitpal.forum.ForumMain;
import com.unicodedev.fitpal.social.SocialHome;
import com.unicodedev.models.Exercise;

import java.util.ArrayList;

public class ExerciseActivity extends AppCompatActivity {

    TextView titleText;
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    ArrayList<Exercise> exerciseArrayList;
    DefaultExerciseAdapter adapter;
    FirebaseFirestore db;
    private String pageTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_workout_exercises);

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
                }
                return false;
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        titleText = findViewById(id.title_exercise);

        Intent intent = getIntent();
        String pageTitle = intent.getStringExtra("pageTitle");

        titleText.setText(pageTitle);

        db = FirebaseFirestore.getInstance();

        recyclerView = findViewById(R.id.exercise_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        exerciseArrayList = new ArrayList<Exercise>();
        adapter = new DefaultExerciseAdapter(ExerciseActivity.this, exerciseArrayList);
        recyclerView.setAdapter(adapter);

        getDefaultExercises(getExerciseType(pageTitle));

    }

    private void getDefaultExercises(String exerciseType) {


        db.collection(exerciseType).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(ExerciseActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }


                for (DocumentChange dc : value.getDocumentChanges()) {
                    if (dc.getType() == DocumentChange.Type.ADDED) {
                        exerciseArrayList.add(dc.getDocument().toObject(Exercise.class));
                    }

                    adapter.notifyDataSetChanged();
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
            }
        });

    }

    private String getExerciseType(String pageTitle) {
        switch (pageTitle) {
            case "Upper Body":
                return "upper-body";
            case "Lower Body":
                return "lower-body";
            case "Full Body":
                return "full-body";
            case "Ab Workout":
                return "ab-workout";
            case "Home Workout":
                return "home-workout";
            default:
                return "cardio";
        }
    }
}