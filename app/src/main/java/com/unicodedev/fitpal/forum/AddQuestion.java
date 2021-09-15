package com.unicodedev.fitpal.forum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.unicodedev.fitpal.HomeActivity;
import com.unicodedev.fitpal.R;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddQuestion extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    EditText questionInput, descriptionInput;
    Button submitBtn;

    boolean isChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_add_question);

        questionInput = findViewById(R.id.questionInput);
        descriptionInput = findViewById((R.id.descriptionInput));

        submitBtn = findViewById(R.id.submit_btn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isChecked = validateFields();
                if(isChecked){
                    String question = questionInput.getText().toString();
                    String description = descriptionInput.getText().toString();

                    String authorId = "N5F3ReaLEixRpe9wdSS7";
                    Date date =java.util.Calendar.getInstance().getTime();


                    Map<String, Object> data = new HashMap<>();
                    data.put("authorID", authorId);
                    data.put("question",question);
                    data.put("description", description);
                    data.put("publishedOn", date);



                    db.collection("Forum").document()
                            .set(data)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("FirebaseLog", "DocumentSnapshot successfully written!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("FirebaseLog", "Error writing document", e);
                                }
                            });


                    Intent i = new Intent(getApplicationContext(), MyQuestions.class);
                    startActivity(i);
                }
            }
        });
    }

    private boolean validateFields(){
        if (questionInput.length() == 0) {
            questionInput.setError("Question is required");
            return false;
        }
        if (descriptionInput.length() == 0) {
            descriptionInput.setError("Description is required");
            return false;
        }
        return true;
    }


}