package com.example.alisa.myproject.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.RectF;
import android.view.View;

import com.example.alisa.myproject.R;

import static android.R.attr.bitmap;

/**
 * Created by Alisa on 12/28/2016.
 */

public class Player extends GameObject{


    public Player(Context context, View view, Game game, Point location) {
        super(context, view, game, location);
    }

    public void moveLeft()
    {
        RectF location=this.getLocation();
        location.offset(-10,0);
        if(location.centerX()<0)
        {
            location.offset(10,0);
        }
    }

    public void move_right()
    {
        RectF location=this.getLocation();
        location.offset(10,0);
        if(location.centerX()>_game.getScreenSize().x)
        {
            location.offset(-10,0);
        }
    }

    @Override
    protected int getNumberOfFramesInSprite() {
        return 1;
    }

    @Override
    protected int getSpriteResourceId() {
        return R.drawable.airplane;
    }

    @Override
    public void updateState() {

    }

    @Override
    protected Bitmap createSmallBitmap(Bitmap spritesBitmap) {
        double ratio = spritesBitmap.getHeight()/(float)spritesBitmap.getWidth();
        int smallWidth=200;
        int smallHeight = (int)(smallWidth*ratio);
        return Bitmap.createScaledBitmap(spritesBitmap, smallWidth, smallHeight, false);

    }
}
