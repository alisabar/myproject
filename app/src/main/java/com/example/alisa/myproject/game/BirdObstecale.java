package com.example.alisa.myproject.game;

import android.content.Context;
import android.graphics.Point;
import android.view.View;

import com.example.alisa.myproject.R;

import java.util.Random;

/**
 * Created by Alisa on 1/7/2017.
 */

public class BirdObstecale extends GameObject {
    int directionY=10;

    public BirdObstecale(Context context, View view,Game game, Point location) {
        super(context, view,game, location);
    }

    @Override
    protected int getNumberOfFramesInSprite() {
        return 3;
    }

    @Override
    protected int getSpriteResourceId() {
        return R.drawable.newbird_sprite;
    }

    @Override
    public void updateState() {
        getLocation().offset(0,directionY);

        naiveFrameNam=(naiveFrameNam+1)%frames.length;


        Point screenSize = _game.getScreenSize();
        if(getLocation().centerY() >  screenSize.y){
            setAlive(false);
        }
    }



}