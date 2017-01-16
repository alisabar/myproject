package com.example.alisa.myproject.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.example.alisa.myproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alisa on 1/7/2017.
 */

public class Game {

    private final View _view;
    private final Context _context;
    private final List<GameObject> _ganmeObjects;
    private final BirdCreator _birdCreator;
    private final Player _player;
    private final Bitmap _background;

    public Game(Context context, View view)
    {
        _view =view;
        _context=context;
        _ganmeObjects = new ArrayList<GameObject>();
        _birdCreator = new BirdCreator(_context,_view,this,1000*3);
        //
        _player=new Player(
                _context
                ,_view
                ,this
                ,new Point(getScreenSize().x/2-100,getScreenSize().y-1000)
        );
        _background = BitmapFactory.decodeResource(_view.getResources(), R.drawable.sky3);

    }
    private void createBackgroundImage(Canvas canvas) {
        canvas.drawBitmap(_background,0,0,null);
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
        _player.updateState();
    }

    public void draw(Canvas canvas)
    {
        createBackgroundImage(canvas);

        for (GameObject gameObj: _ganmeObjects) {
            gameObj.draw(canvas);
        }
        _player.draw(canvas);
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
