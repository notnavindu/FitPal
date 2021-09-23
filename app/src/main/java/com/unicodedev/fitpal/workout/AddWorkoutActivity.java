package com.unicodedev.fitpal.workout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.unicodedev.fitpal.R;
import com.unicodedev.models.Workout;

import java.util.HashMap;
import java.util.Map;

public class AddWorkoutActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    EditText nameInput, repetitionInput, setsInput;
    Button addWorkoutBtn;

    boolean isValidated;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_add_workout);

        nameInput = findViewById(R.id.nameInput);
        repetitionInput = findViewById(R.id.repetitionInput);
        setsInput = findViewById(R.id.setsInput);
        addWorkoutBtn = findViewById(R.id.add_workout_btn);

        userId = auth.getCurrentUser().getUid();
//        Toast.makeText(AddWorkoutActivity.this, "" + userId, Toast.LENGTH_SHORT).show();

        addWorkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isValidated = validateFields();
                if (isValidated) {
                    String exerciseName = nameInput.getText().toString();
                    String repetition = repetitionInput.getText().toString();
                    String sets = setsInput.getText().toString();

                    Workout workout = new Workout(exerciseName, repetition, sets, userId);

                    db.collection("user-workouts")
                            .add(workout)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
//                                    Toast.makeText(AddWorkoutActivity.this, "data added", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(AddWorkoutActivity.this, YourWorkoutsActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AddWorkoutActivity.this, "error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });


                }
            }
        });
    }

    private boolean validateFields() {
        if (nameInput.length() == 0) {
            nameInput.setError("Exercise name is required");
            return false;
        }
        if (repetitionInput.length() == 0) {
            repetitionInput.setError("repetition count is required");
            return false;
        }
        if (setsInput.length() == 0) {
            setsInput.setError("sets count is required");
            return false;
        }
        return true;
    }
}