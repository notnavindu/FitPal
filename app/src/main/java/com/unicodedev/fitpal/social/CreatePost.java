package com.unicodedev.fitpal.social;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.unicodedev.fitpal.R;

import androidx.appcompat.app.AppCompatActivity;

public class CreatePost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_new_post);
    }

    public void onSubmit(View view) {
        // Do Something
    }
}