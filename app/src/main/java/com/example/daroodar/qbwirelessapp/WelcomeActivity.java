package com.example.daroodar.qbwirelessapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent myIntent = new Intent(WelcomeActivity.this, MainActivity.class);
                WelcomeActivity.this.startActivity(myIntent);
                finish();
            }

        }, 2500L);
    }
}
