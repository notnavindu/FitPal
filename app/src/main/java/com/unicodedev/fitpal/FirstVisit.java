package com.unicodedev.fitpal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class FirstVisit extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    final Calendar myCalendar = Calendar.getInstance();

    ProgressDialog progressDialog;

    EditText nameInput, birthdayInput, genderInput, heightInput, weightInput;
    Button submitBtn;

    boolean isChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_visit);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
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
                        progressDialog = new ProgressDialog(FirstVisit.this);
                        progressDialog.setCancelable(false);
                        progressDialog.setMessage("Creating Profile...");
                        progressDialog.show();

                        String name = nameInput.getText().toString();
                        String birthday = birthdayInput.getText().toString();
                        String gender = genderInput.getText().toString();
                        String height = heightInput.getText().toString();
                        String weight = weightInput.getText().toString();

                        // Add to database
                        String userId = user.getUid();

                        Map<String, Object> data = new HashMap<>();

                        data.put("nickname", name);
                        data.put("birthday", birthday);
                        data.put("gender", gender);
                        data.put("height", height);
                        data.put("weight", weight);

                        db.collection("Users").document(userId)
                                .set(data)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("FirebaseLog", "DocumentSnapshot successfully written!");
                                        if(progressDialog.isShowing()){
                                            progressDialog.dismiss();
                                        }
                                        Intent i = new Intent(getApplicationContext(), ProfilePic.class);
                                        startActivity(i);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        if(progressDialog.isShowing()){
                                            progressDialog.dismiss();
                                        }
                                        Log.w("FirebaseLog", "Error writing document", e);
                                    }
                                });


                    }

                }
            });
        }


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