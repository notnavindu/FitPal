package com.unicodedev.fitpal.workout;

import static com.unicodedev.fitpal.R.id;
import static com.unicodedev.fitpal.R.layout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.unicodedev.adapters.DefaultExerciseAdapter;
import com.unicodedev.fitpal.R;
import com.unicodedev.models.Exercise;

import java.util.ArrayList;
import java.util.Objects;

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

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
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