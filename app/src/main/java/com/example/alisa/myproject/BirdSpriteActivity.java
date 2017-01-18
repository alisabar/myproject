package com.example.alisa.myproject;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.alisa.myproject.R;

public class BirdSpriteActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bird_sprite);
        ((GameView)findViewById(R.id.gameView)).onCreate();

    }


    @Override
    protected void onPause() {
        super.onPause();
        ((GameView)findViewById(R.id.gameView)).onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((GameView)findViewById(R.id.gameView)).onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((GameView)findViewById(R.id.gameView)).onDestroy();
    }
}
