package com.unicodedev.fitpal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FirstVisit extends AppCompatActivity {
    final Calendar myCalendar = Calendar.getInstance();

    EditText nameInput, birthdayInput, genderInput, heightInput, weightInput;
    Button submitBtn;

    boolean isChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_visit);

        nameInput = findViewById(R.id.nameInput);
        birthdayInput = findViewById(R.id.birthdayInput);
        genderInput = findViewById(R.id.genderInput);
        heightInput = findViewById(R.id.heightInput);
        weightInput = findViewById(R.id.weightInput);

        submitBtn = findViewById(R.id.submit_btn);

        // Create date picker and getting user date input
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabel();
            }
        };

        birthdayInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(FirstVisit.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // On submit event listener
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isChecked = validateFields();
                if(isChecked){
                    String name = nameInput.getText().toString();
                    String birthday = birthdayInput.getText().toString();
                    String gender = genderInput.getText().toString();
                    String height = heightInput.getText().toString();
                    String weight = weightInput.getText().toString();

                    Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(i);
                }

            }
        });
    }

    //Form field validations
    private boolean validateFields() {
        if (nameInput.length() == 0) {
            nameInput.setError("Name is required");
            return false;
        }

        if (birthdayInput.length() == 0) {
            birthdayInput.setError("Birthday is required");
            return false;
        }

        if (genderInput.length() == 0) {
            genderInput.setError("Gender is required");
            return false;
        }

        if (heightInput.length() == 0) {
            heightInput.setError("Height is required");
            return false;
        }

        if (weightInput.length() == 0) {
            weightInput.setError("Weight is required");
            return false;
        }

        return true;
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yy";
        SimpleDateFormat date_format = new SimpleDateFormat(myFormat, Locale.UK);

        birthdayInput.setText(date_format.format(myCalendar.getTime()));
    }



}