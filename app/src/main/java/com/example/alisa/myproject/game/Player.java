package com.example.alisa.myproject.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.RectF;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.alisa.myproject.R;

import static android.R.attr.bitmap;

/**
 * Created by Alisa on 12/28/2016.
 */

public class Player extends GameObject{
    private static final long MAX_TIME_TO_BE_INVINCIBLE_MILLI = 1000 * 2;
    private View.OnTouchListener touchListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
Log.d("touch","On touch");
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (event.getX() > getLocation().centerX()) {
                    move_right();
                    return true;
                } else if (event.getX() < getLocation().centerX()) {
                    moveLeft();
                    return true;
                }
            }
            return false;
        }

    };
    private int lifePoints;
    private boolean _isInvincible;
    private long _isInvincibleStartedMilli;

    public int getLifePoints(){
        return lifePoints;
    }
    public Player(Context context, View view, Game game, Point location) {
        super(context, view, game, location);
        lifePoints=3;
        _isInvincible=false;
        _isInvincibleStartedMilli=0;
        view.setOnTouchListener(touchListener);
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
        //if player is invincible (cannot be hit
        if(_isInvincible){
            //check the time until invincible is over
            if(System.currentTimeMillis()-_isInvincibleStartedMilli > MAX_TIME_TO_BE_INVINCIBLE_MILLI){
                _isInvincible=false;
            }
        }
    }

    @Override
    protected Bitmap createSmallBitmap(Bitmap spritesBitmap) {
        double ratio = spritesBitmap.getHeight()/(float)spritesBitmap.getWidth();
        int smallWidth=200;
        int smallHeight = (int)(smallWidth*ratio);
        return Bitmap.createScaledBitmap(spritesBitmap, smallWidth, smallHeight, false);

    }

    public void decreaseLife(int numOfLifePoints) {
        if (_isInvincible){
            return;
        }

        _isInvincible=true;
        _isInvincibleStartedMilli= System.currentTimeMillis();
        lifePoints=lifePoints-numOfLifePoints;

        Log.i(getClass().getName(),"player hit,ouch! life points: "+getLifePoints());
    }
}
