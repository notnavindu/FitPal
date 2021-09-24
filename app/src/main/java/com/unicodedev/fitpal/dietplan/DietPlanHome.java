package com.unicodedev.fitpal.dietplan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.unicodedev.fitpal.HomeActivity;
import com.unicodedev.fitpal.R;

import java.util.Objects;

public class DietPlanHome extends AppCompatActivity {
    FirebaseFirestore db;
    FirebaseAuth fAuth;
    private Button startBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dietplan_main);

        startBtn = findViewById(R.id.start_btn);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), DietPlan.class);
                startActivity(i);
            }
        });

        fAuth = FirebaseAuth.getInstance();
        String userId = fAuth.getCurrentUser().getUid();
        db = FirebaseFirestore.getInstance();
        db.collection("Diet-plan")
                .whereEqualTo("userId", userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        boolean isEmpty = task.getResult().isEmpty();
                        if (!isEmpty) {
                            startBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent i = new Intent(getApplicationContext(), DietPlan.class);
                                    startActivity(i);
                                }
                            });
                        } else {
                            startBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent i = new Intent(DietPlanHome.this, SelectDietPlan.class);
                                    startActivity(i);
                                }
                            });
                        }
                    }
                });


        Button fatCalcBtn = findViewById(R.id.fat_calculate_btn);


        fatCalcBtn.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), FatCalculator.class);
            startActivity(i);
        });
    }

}
