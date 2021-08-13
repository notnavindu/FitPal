package com.unicodedev.fitpal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ImageView socialButton = (ImageView) findViewById(R.id.home_social_button);
        socialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Social.class);
                startActivity(i);
            }
        });
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