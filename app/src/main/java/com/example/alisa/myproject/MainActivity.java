package com.example.alisa.myproject;

import android.content.Intent;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.alisa.myproject.game.GameManager;

import com.google.firebase.analytics.FirebaseAnalytics;


import java.io.Console;
import java.io.PrintStream;

public class MainActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
    }

    public void PlayButton(View view){
        GameManager.instance().newGame(this);
    }

    public void SettingButton(View view) {
        startActivity(new Intent(this,settings.class));

    }

    public void openAboutActivity(View view) {
        startActivity(new Intent(this,About.class));
    }
}
