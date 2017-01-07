package com.example.alisa.myproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;

import com.example.alisa.myproject.game.Obstacle;
import com.example.alisa.myproject.game.Player;

import java.util.ArrayList;
import java.util.Random;

public class SkyBackground extends AppCompatActivity {

    Bitmap obst_image;
    Player player;
    Point size;
    Random random_generator;
    ArrayList<Obstacle> obstacles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sky_background);
        Display d=getWindowManager().getDefaultDisplay();
        size=new Point();
        d.getSize(size);
        random_generator=new Random();
        player=new Player(size.x,BitmapFactory.decodeResource(getResources(),R.drawable.plane));
    }

    public void addObstacle()
    {
        int x,y;
        x=random_generator.nextInt(size.x);
        y=random_generator.nextInt(size.y-(size.y-10))+(size.y-10);
        obstacles.add(new Obstacle(x,y));
    }

    ;public void removeObstacles() {
        for (int i = 0; i < obstacles.size(); i++) {
            if ((obstacles.get(i).getPosition()).y == 0) {
                obstacles.remove(i);
                addObstacle();
            }
        }
    }

}
