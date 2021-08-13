package com.unicodedev.fitpal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void onSubmit(View view) {
        Intent i = new Intent(this, AddQuestion.class);
        startActivity(i);
    }

    public void onSubmit2(View view) {
        Intent i = new Intent(this, FirstVisit.class);
        startActivity(i);
    }
}