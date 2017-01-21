package com.example.alisa.myproject;

import android.content.Intent;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.Console;
import java.io.PrintStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

public void PlayButton(View view){
    Intent i = new Intent(this, BirdSpriteActivity.class);
    startActivity(i);

}

    public void SettingButton(View view) {
  //      Intent i = new Intent(this,SettingsActivity.class);
     //   startActivity(i);

    }
}
