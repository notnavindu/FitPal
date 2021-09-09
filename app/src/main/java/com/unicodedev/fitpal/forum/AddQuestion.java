package com.unicodedev.fitpal.forum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.unicodedev.fitpal.HomeActivity;
import com.unicodedev.fitpal.R;

public class AddQuestion extends AppCompatActivity {
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