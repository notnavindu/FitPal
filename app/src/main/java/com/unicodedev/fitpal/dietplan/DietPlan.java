package com.unicodedev.fitpal.dietplan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.unicodedev.fitpal.R;

import java.util.Objects;

public class DietPlan extends AppCompatActivity {
    TextView goal;
    TextView current_weight_display;
    String userId;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dietplan_diet_plan);

        FloatingActionButton addFoodBtn = findViewById(R.id.add_food_btn);
        goal = findViewById(R.id.goal);
        current_weight_display = findViewById(R.id.current_weight_display);

        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        userId = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        db.collection("Diet-plan")
                .whereEqualTo("userId", userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                           goal.setText("Calorie goal :" + Objects.requireNonNull(document.getData().get("calories")).toString());
                           current_weight_display.setText("Current weight is :" + Objects.requireNonNull(document.getData().get("currentWeight")).toString());
                        }
                    }
                });
        addFoodBtn.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), AddFood.class);
            startActivity(i);
        });
    }
}