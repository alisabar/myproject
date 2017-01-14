package com.example.alisa.myproject;

import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.Console;
import java.io.PrintStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyMusicRunnable mp=new MyMusicRunnable(this);
        new Thread(mp).start();
    }
}
