package com.unicodedev.fitpal.dietplan;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.unicodedev.fitpal.R;

import java.util.Objects;

public class AddFood extends AppCompatActivity {
    EditText currentWeight;
    Button addBtn;
    String userId;
    String docId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dietplan_addfoods);

        addBtn = findViewById(R.id.update_btn);
        addBtn.setOnClickListener(view -> {
            updateWeight();
            Intent i = new Intent(AddFood.this, DietPlan.class);
            startActivity(i);
            Toast.makeText(getApplicationContext(), "Weight updated successfully", Toast.LENGTH_SHORT).show();
        });

        currentWeight = findViewById(R.id.food_items_input);

        currentWeight.addTextChangedListener(fieldWatcher);

        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        userId = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Diet-plan").whereEqualTo("userId", userId).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            docId = (document.getId());
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public  void updateWeight() {
        String cweight = currentWeight.getText().toString();
        double cweightTemp = Double.parseDouble(cweight);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Diet-plan").document(docId).update("currentWeight", cweightTemp).addOnSuccessListener(unused -> {
        });
    }


    private final TextWatcher fieldWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String weightInput = currentWeight.getText().toString().trim();


            addBtn.setEnabled(!weightInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

}