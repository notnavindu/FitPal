package com.unicodedev.fitpal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Animation anim, anim_out;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.image_view);
        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {


            }

            @Override
            public void onAnimationEnd(Animation animation) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        anim_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
                        anim_out.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {


                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                Intent i = new Intent(MainActivity.this, Signin.class);
                                startActivity(i);
                                overridePendingTransition(0, 0);
                                finish();


                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });


                        imageView.startAnimation(anim_out);
                    }
                }, 600);



            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        imageView.startAnimation(anim);
    }
}