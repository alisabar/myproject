package com.example.alisa.myproject.game;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.example.alisa.myproject.GameOverActivity;
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
    private final Paint _textPaint;

    public Game(Context context, View view)
    {
        _textPaint = new Paint();
        _textPaint.setColor(Color.BLACK);
        _textPaint.setTextSize(50);

        _gameEnded=false;
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
    private void drawBackgroundImage(Canvas canvas) {
        canvas.drawBitmap(_background,0,0,null);
    }

    boolean _gameEnded;
    //API
    public boolean gameEnded(){
        return _gameEnded;
    }
    public void updateState()
    {
        if(_gameEnded){
            return;
        }
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

        //handle collisions
        RectF playerLocation= new RectF(_player.getLocation());

        for (GameObject gameObj: new ArrayList<>(_ganmeObjects)) {
            // if collides
            if(playerLocation.intersect(gameObj.getLocation())){
                collideWithObstecle();
            }
        }

        //is end game
        if (_player.getLifePoints()<=0){
            //end of game
            gameOver();
        }
    }

    private void gameOver() {
        _gameEnded=true;
        //go to game over screen
        _context.startActivity(new Intent(_context, GameOverActivity.class));
    }

    private void collideWithObstecle() {
        _player.decreaseLife(1);
    }


    public void draw(Canvas canvas)
    {
        if(_gameEnded){
            return;
        }
        drawBackgroundImage(canvas);

        drawLife(canvas);

        for (GameObject gameObj: _ganmeObjects) {
            gameObj.draw(canvas);
        }
        _player.draw(canvas);
    }

    private void drawLife(Canvas canvas) {
        try {
            String lifeString="Life:"+_player.getLifePoints();

            canvas.drawText(lifeString,10,20,_textPaint);
        } catch (Exception e) {
            Log.e(getClass().getName(),"drawLife",e);
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
