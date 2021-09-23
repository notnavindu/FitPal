package com.unicodedev.fitpal.workout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.unicodedev.adapters.YourWorkoutsAdapter;
import com.unicodedev.fitpal.R;
import com.unicodedev.models.Workout;

import java.util.ArrayList;

public class YourWorkoutsActivity extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    ArrayList<Workout> workoutArrayList;
    YourWorkoutsAdapter adapter;
    FirebaseFirestore db;
    FirebaseAuth auth;

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_your_workouts);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        floatingActionButton = findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(YourWorkoutsActivity.this, AddWorkoutActivity.class);
                startActivity(intent);
            }
        });

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        userId = auth.getCurrentUser().getUid();

        recyclerView = findViewById(R.id.your_workouts_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        workoutArrayList = new ArrayList<>();
        adapter = new YourWorkoutsAdapter(YourWorkoutsActivity.this, workoutArrayList);
        recyclerView.setAdapter(adapter);

        getUserWorkouts(userId);
    }

    private void getUserWorkouts(String userId) {
        db.collection("user-workouts")
                .whereEqualTo("userId", userId)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {

                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Toast.makeText(YourWorkoutsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                String id = dc.getDocument().getId();
                                Workout workout = dc.getDocument().toObject(Workout.class);
                                workout.setDocId(id);
                                workoutArrayList.add(workout);
//                                Toast.makeText(YourWorkoutsActivity.this, "" + dc.getDocument().getData(), Toast.LENGTH_SHORT).show();
                            }

                            adapter.notifyDataSetChanged();
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                        }
                    }
                });
    }
}