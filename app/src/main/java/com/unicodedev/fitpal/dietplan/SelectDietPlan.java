package com.unicodedev.fitpal.dietplan;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.unicodedev.fitpal.HomeActivity;
import com.unicodedev.fitpal.Profile;
import com.unicodedev.fitpal.R;
import com.unicodedev.fitpal.forum.ForumMain;
import com.unicodedev.fitpal.social.SocialHome;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SelectDietPlan extends AppCompatActivity {
    EditText current_weight_input;
    EditText target_weight_input;
    EditText height1_input;
    EditText age1_input;
    EditText gender1_input;
    RadioButton radio_beginner;
    RadioButton radio_moderate;
    RadioButton radio_beast;
    Button addFoodBtn;
    String userId;
    FirebaseFirestore db;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dietplan_select_plan);

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

                    default: return true;
                }
            }
        });

        addFoodBtn = findViewById(R.id.start_diet_btn);
        addFoodBtn.setOnClickListener(view -> {
                calculateCalories();
                Intent i = new Intent(SelectDietPlan.this, DietPlan.class);
                startActivity(i);
        });


        current_weight_input = findViewById(R.id.current_weight_input);
        target_weight_input = findViewById(R.id.target_weight_input);
        height1_input = findViewById(R.id.height1_input);
        age1_input = findViewById(R.id.age1_input);
        gender1_input = findViewById(R.id.gender1_input);
        radio_beginner = findViewById(R.id.radio_beginner);
        radio_moderate = findViewById(R.id.radio_moderate);
        radio_beast = findViewById(R.id.radio_beast);



        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        userId = fAuth.getCurrentUser().getUid();

        current_weight_input.addTextChangedListener(fieldWatcher);
        target_weight_input.addTextChangedListener(fieldWatcher);
        height1_input.addTextChangedListener(fieldWatcher);
        age1_input.addTextChangedListener(fieldWatcher);
        gender1_input.addTextChangedListener(fieldWatcher);

    }

    public void calculateCalories() {
        Calculations calc = new Calculations();
        String cweight = current_weight_input.getText().toString();
        String tweight = target_weight_input.getText().toString();
        String height = height1_input.getText().toString();
        String age = age1_input.getText().toString();
        String gender = gender1_input.getText().toString();
        Map<String, Object> diet = new HashMap<>();
        db = FirebaseFirestore.getInstance();

        if (TextUtils.isEmpty(cweight) || TextUtils.isEmpty(tweight) || TextUtils.isEmpty(gender) || TextUtils.isEmpty(height) || TextUtils.isEmpty(age)) {
            Toast.makeText(SelectDietPlan.this, "Please fill the fields", Toast.LENGTH_SHORT).show();
        } else {
            String intensity;
            double calories;
            double finalCalories;
            double cweightTemp = Double.parseDouble(cweight);
            double tweightTemp = Double.parseDouble(tweight);
            double heightTemp = Double.parseDouble(height);
            int ageTemp = Integer.parseInt(age);

            if (radio_beginner.isChecked()) {
                if (gender.equals("male") || gender.equals("Male")) {
                    intensity = radio_beginner.getText().toString();
                    calories = calc.calculateCaloriesBeginnerMale(cweightTemp, heightTemp, ageTemp);
                    finalCalories = Math.round(calories * 100.00) / 100.00;
                    diet.put("age", ageTemp);
                    diet.put("currentWeight", cweightTemp);
                    diet.put("targetWeight", tweightTemp);
                    diet.put("height", heightTemp);
                    diet.put("intensity", intensity);
                    diet.put("calories", finalCalories);
                    diet.put("userId", userId);

                } else if (gender.equals("female") || gender.equals("Female")) {
                    intensity = radio_beginner.getText().toString();
                    calories = calc.calculateCaloriesBeginnerFemale(cweightTemp, heightTemp, ageTemp);
                    finalCalories = Math.round(calories * 100.00) / 100.00;
                    diet.put("age", ageTemp);
                    diet.put("currentWeight", cweightTemp);
                    diet.put("targetWeight", tweightTemp);
                    diet.put("height", heightTemp);
                    diet.put("intensity", intensity);
                    diet.put("calories", finalCalories);
                    diet.put("userId", userId);

                } else {
                    Toast.makeText(SelectDietPlan.this, "Enter a valid gender", Toast.LENGTH_SHORT).show();
                }
            } else if (radio_moderate.isChecked()) {
                if (gender.equals("male") || gender.equals("Male")) {
                    intensity = radio_moderate.getText().toString();
                    calories = calc.calculateCaloriesModerateMale(cweightTemp, heightTemp, ageTemp);
                    finalCalories = Math.round(calories * 100.00) / 100.00;
                    diet.put("age", ageTemp);
                    diet.put("currentWeight", cweightTemp);
                    diet.put("targetWeight", tweightTemp);
                    diet.put("height", heightTemp);
                    diet.put("intensity", intensity);
                    diet.put("calories", finalCalories);
                    diet.put("userId", userId);
                } else if (gender.equals("female") || gender.equals("Female")) {
                    intensity = radio_moderate.getText().toString();
                    calories = calc.calculateCaloriesModerateFemale(cweightTemp, heightTemp, ageTemp);
                    finalCalories = Math.round(calories * 100.00) / 100.00;
                    diet.put("age", ageTemp);
                    diet.put("currentWeight", cweightTemp);
                    diet.put("targetWeight", tweightTemp);
                    diet.put("height", heightTemp);
                    diet.put("intensity", intensity);
                    diet.put("calories", finalCalories);
                    diet.put("userId", userId);
                } else {
                    Toast.makeText(SelectDietPlan.this, "Enter a valid gender", Toast.LENGTH_SHORT).show();
                }
            } else if (radio_beast.isChecked()) {
                if (gender.equals("male") || gender.equals("Male")) {
                    intensity = radio_beast.getText().toString();
                    calories = calc.calculateCaloriesBeastMale(cweightTemp, heightTemp, ageTemp);
                    finalCalories = Math.round(calories * 100.00) / 100.00;
                    diet.put("age", ageTemp);
                    diet.put("currentWeight", cweightTemp);
                    diet.put("targetWeight", tweightTemp);
                    diet.put("height", heightTemp);
                    diet.put("intensity", intensity);
                    diet.put("calories", finalCalories);
                    diet.put("userId", userId);
                } else if (gender.equals("female") || gender.equals("Female")) {
                    intensity = radio_beast.getText().toString();
                    calories = calc.calculateCaloriesBeastFemale(cweightTemp, heightTemp, ageTemp);
                    finalCalories = Math.round(calories * 100.00) / 100.00;
                    diet.put("age", ageTemp);
                    diet.put("currentWeight", cweightTemp);
                    diet.put("targetWeight", tweightTemp);
                    diet.put("height", heightTemp);
                    diet.put("intensity", intensity);
                    diet.put("calories", finalCalories);
                    diet.put("userId", userId);
                } else {
                    Toast.makeText(SelectDietPlan.this, "Enter a valid gender", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(SelectDietPlan.this, "Please select an intensity", Toast.LENGTH_SHORT).show();
            }
        }


        db.collection("Diet-plan").add(diet).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            private static final String TAG = "DietPlan";

            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "Document snapshot" + documentReference.getId() );
            }
        }).addOnFailureListener(new OnFailureListener() {
            private static final String TAG = "DietPlan";

            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error", e);
            }
        });

    }


    private final TextWatcher fieldWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String genderInput = gender1_input.getText().toString().trim();
            String heightInput = height1_input.getText().toString().trim();
            String cweightInput = current_weight_input.getText().toString().trim();
            String tweightInput = target_weight_input.getText().toString().trim();
            String ageInput = age1_input.getText().toString().trim();

            addFoodBtn.setEnabled(!genderInput.isEmpty() && !heightInput.isEmpty() && !cweightInput.isEmpty() && !tweightInput.isEmpty() && !ageInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}
