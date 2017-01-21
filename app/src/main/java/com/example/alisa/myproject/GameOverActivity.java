package com.example.alisa.myproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class GameOverActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        TextView score=(TextView) findViewById(R.id.Score);
        SharedPreferences sharedprep=getSharedPreferences("HighScore", Context.MODE_PRIVATE);

        int high_m= sharedprep.getInt("minutes",0);
        int high_s= sharedprep.getInt("seconds",0);

        score.setText(String.valueOf(high_m)+ "."+String.valueOf(high_s));
    }

    public void goToMainMenu(View view) {

        finish();
        startActivity(new Intent(this,MainActivity.class));


    }
}
