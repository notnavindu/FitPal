package com.unicodedev.fitpal.forum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.unicodedev.fitpal.R;

public class ForumMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_main);

        TextView myQuestionBtn= (TextView) findViewById(R.id.my_question_button);
        myQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ForumMyQuestions.class);
                startActivity(i);
            }
        });

        FloatingActionButton addQuestionBtn = (FloatingActionButton) findViewById(R.id.addQuestionBtn);
        addQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ForumAddQuestion.class);
                startActivity(i);
            }
        });

        TextView cardTitleBtn= (TextView) findViewById(R.id.card_title);
        cardTitleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ForumQuestion.class);
                startActivity(i);
            }
        });
    }

    public void onSubmit(View view) {
        // Do Something
    }
}