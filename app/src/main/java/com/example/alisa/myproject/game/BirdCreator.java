package com.example.alisa.myproject.game;

import android.content.Context;
import android.graphics.Point;
import android.view.View;

import java.util.Random;

/**
 * Created by Alisa on 1/7/2017.
 */


public class BirdCreator
{
    private final Context _context;
    private final View _view;
    private final Game _game;
    private final long _createIntervalMilli;

    public BirdCreator(Context context,View view,Game game, long createIntervalMilli){
        _context = context;
        _view = view;
        _game = game;
        _createIntervalMilli=createIntervalMilli;
    }

    long _lastCreated;

    public void createBird()
    {
        if (isTimeToCreate()){
            create();
        }
    }

    private void create() {
        GameObject newObject = new BirdObstecale(_context,_view,_game,getNewLocation());
        _game.addGameObject(newObject);
        lastGameObject=newObject;
        _lastCreated =System.currentTimeMillis();

    }

    private Point getNewLocation() {
        return getNextFreeLocation(_game.getScreenSize().x);
    }

    private boolean isTimeToCreate() {
        return System.currentTimeMillis() - _lastCreated > _createIntervalMilli;
    }

    static Random random =new Random(System.currentTimeMillis());

    private Point getNextFreeLocation(int screenWidth)
    {

        float lastX  = lastGameObject!=null? lastGameObject.getLocation().centerX() : -100;
        int nextX=0;
        int minXDiff=30;
        do {
            nextX = random.nextInt(screenWidth);
        }while(Math.abs(lastX-nextX)<minXDiff);
        return new Point(nextX,0);
    }

    static GameObject lastGameObject;
}

