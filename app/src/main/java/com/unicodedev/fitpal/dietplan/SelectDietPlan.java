package com.unicodedev.fitpal.dietplan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.unicodedev.fitpal.R;

import java.util.ArrayList;

public class SelectDietPlan extends AppCompatActivity {
    EditText current_weight_input;
    EditText target_weight_input;
    TextView diet_plan_days;

    private ArrayList<IntensityItem> mIntensityList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dietplan_select_plan);

        initList();

        Spinner spinnerIntensityItems = findViewById(R.id.intensity_input);

        IntensityAdapter mAdapter = new IntensityAdapter(this, mIntensityList);
        spinnerIntensityItems.setAdapter(mAdapter);

        spinnerIntensityItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                IntensityItem clickedItem = (IntensityItem) parent.getItemAtPosition(position);
                String clickedIntensityItem = clickedItem.getIntensityItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Button addFoodBtn = findViewById(R.id.start_diet_btn);
        addFoodBtn.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), DietPlan.class);
            startActivity(i);
        });
    }

    private void initList() {
        mIntensityList = new ArrayList<>();
        mIntensityList.add(new IntensityItem(""));
        mIntensityList.add(new IntensityItem("Beginner"));
        mIntensityList.add(new IntensityItem("Moderate"));
        mIntensityList.add(new IntensityItem("Beast"));
    }
}