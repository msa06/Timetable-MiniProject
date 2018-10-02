package com.example.msa.timetable.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.msa.timetable.R;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT=3000;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mAuth.addAuthStateListener(mAuthListner);
                if(mAuth.getCurrentUser()!=null){
                    startActivity(new Intent(SplashActivity.this, DayViewActivity.class));
                    finish();
                }
                else{
                    startActivity(new Intent(SplashActivity.this, ChoiceActivity.class));
                    finish();
                }

            }
        },SPLASH_TIME_OUT);

        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };

    }
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.addAuthStateListener(mAuthListner);
    }
}
