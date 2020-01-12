package com.example.acmod;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.acmod.utils.SharedPref;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(SharedPref.getBoolean(getApplicationContext(),"sp_loggedin") == true)
                {
                    Intent intent = new Intent(SplashActivity.this,HomeActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        },600);
    }
}
