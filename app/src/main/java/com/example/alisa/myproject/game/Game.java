package com.example.alisa.myproject.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Alisa on 1/7/2017.
 */

public class Game {

    private final View _view;
    private final Context _context;
    private final List<GameObject> _ganmeObjects;
    private final BirdCreator _birdCreator;

    public Game(Context context, View view)
    {
        _view =view;
        _context=context;
        _ganmeObjects = new ArrayList<GameObject>();
        _birdCreator = new BirdCreator(_context,_view,this,1000*3);
    }
    private void createBackgroundImage(Canvas canvas) {
    }
    //API

    public void updateState()
    {
        for (GameObject gameObj: _ganmeObjects) {
            gameObj.updateState();
        }

        for (GameObject gameObj: new ArrayList<>(_ganmeObjects)) {
            if(!gameObj.isAlive()){
                _ganmeObjects.remove(gameObj);
            }
        }

        _birdCreator.createBird();
    }

    public void draw(Canvas canvas)
    {
        createBackgroundImage(canvas);

        for (GameObject gameObj: _ganmeObjects) {
            gameObj.draw(canvas);
        }

    }

    private Point _screenSize;
    public Point getScreenSize()
    {
        if(_screenSize!=null){
            return _screenSize;
        }
        WindowManager wm = (WindowManager) _context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return _screenSize = size;
    }

    public  void addGameObject(GameObject gameObject) {
        _ganmeObjects.add(gameObject);
    }

    //API

}
