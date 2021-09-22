package com.unicodedev.fitpal.dietplan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
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


        fAuth = FirebaseAuth.getInstance();
        String userId = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        db = FirebaseFirestore.getInstance();
        db.collection("Diet-plan")
                .whereEqualTo("userId", userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        boolean isEmpty = task.getResult().isEmpty();
                        if(!isEmpty){
                            startBtn.setOnClickListener(view -> {
                             Intent i = new Intent(getApplicationContext(), DietPlan.class);
                               startActivity(i);
                                });
                        }else{
                            startBtn.setOnClickListener(view -> {
                                Intent i = new Intent(getApplicationContext(), SelectDietPlan.class);
                                startActivity(i);
                            });
                        }
                    }
                });


        Button fatCalcBtn = findViewById(R.id.fat_calculate_btn);
        startBtn = findViewById(R.id.start_btn);

        fatCalcBtn.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), FatCalculator.class);
            startActivity(i);
        });
    }

}
