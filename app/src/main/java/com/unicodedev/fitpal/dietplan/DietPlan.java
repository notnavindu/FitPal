package com.unicodedev.fitpal.dietplan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.unicodedev.fitpal.R;

import java.util.Collection;
import java.util.Objects;

public class DietPlan extends AppCompatActivity {
    TextView goal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dietplan_diet_plan);

        FloatingActionButton addFoodBtn = findViewById(R.id.add_food_btn);


//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        DocumentReference documentReference = db.collection("Diet-plan").document();
//        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
//                goal.setText(documentSnapshot.getString("calories"));
//            }
//        });

        addFoodBtn.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), AddFood.class);
            startActivity(i);
        });
    }
}