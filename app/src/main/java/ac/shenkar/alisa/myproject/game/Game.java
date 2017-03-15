package ac.shenkar.alisa.myproject.game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import ac.shenkar.alisa.myproject.GameOverActivity;
import ac.shenkar.alisa.myproject.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static android.view.KeyEvent.ACTION_DOWN;

/**
 * Created by Alisa on 1/7/2017.
 */

public class Game {

    private final View _view;
    private final Context _context;
    private final List<GameObject> _ganmeObjects;
    private final GameObjectCreator _birdCreator;
    private final Player _player;
    private final LifeBonusCreator _lifeObjCreator;
    private Bitmap _background;
    private final Paint _textPaint;

    private  Calendar c = Calendar.getInstance();
    private final int start_secs= (int) TimeUnit.MILLISECONDS.toSeconds(c.getTimeInMillis());
    private final int start_min=(int) TimeUnit.MILLISECONDS.toMinutes(c.getTimeInMillis());
    private long _timeToEndOfLevelMilli;
    private int _levelNumber;


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
        _lifeObjCreator= new LifeBonusCreator(_context,_view,this,1000*7);
        //
        _player=new Player(
                _context
                ,_view
                ,this
                ,new Point(getScreenSize().x/2-100,getScreenSize().y-1000)
        );
        _background = BitmapFactory.decodeResource(_view.getResources(), R.drawable.sky3);
        _background = Bitmap.createScaledBitmap(_background,getScreenSize().x,getScreenSize().y,true);
        _timeToEndOfLevelMilli=System.currentTimeMillis()+ 1000*60*2 /*2 minutes*/;
        _levelNumber = 0;
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
        Log.d(getClass().getName(),"updateState enter");

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

        _birdCreator.createObject();
        _lifeObjCreator.createObject();
        _player.updateState();

        //handle collisions
        RectF playerLocation= new RectF(_player.getLocation());

        for (GameObject gameObj: new ArrayList<>(_ganmeObjects)) {
            // if collides
            if(playerLocation.intersect(gameObj.getLocation())){
                gameObj.collideWithPlayer();
            }
        }

        //is end game
        if (_player.getLifePoints()<=0){
            //end of game
            gameOver();
        }
        else if(isCompleteLevel()){
            startNextLevel();
        }

        Log.d(getClass().getName(),"updateState exit");

    }

    private boolean isCompleteLevel() {
        return System.currentTimeMillis() > _timeToEndOfLevelMilli;
    }

    private void startNextLevel() {
        _gameEnded=true;

        GameManager.instance().nextLevel(_context,_view);
    }

    private void gameOver() {
        _gameEnded=true;
        saveHighScore();

        //we do not want to go back t bird sprite activity
        ((Activity)_context).finish();

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

        drawTimeToComplete(canvas);

        for (GameObject gameObj: _ganmeObjects) {
            gameObj.draw(canvas);
        }
        _player.draw(canvas);
    }

    private void drawTimeToComplete(Canvas canvas) {
        try {
            String timeString=(_timeToEndOfLevelMilli-System.currentTimeMillis())/1000+"";

            canvas.drawText(timeString,10,80,_textPaint);
        } catch (Exception e) {
            Log.e(getClass().getName(),"drawTimeToComplete",e);
        }
    }

    private void drawLife(Canvas canvas) {
        try {
            String lifeString="Life:"+_player.getLifePoints();

            canvas.drawText(lifeString,10,40,_textPaint);
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


    //happens when user slide
    /*
    public void onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        _player.onFling(e1, e2, velocityX, velocityY);
    }
*/
    public void saveHighScore(){
        try {
            c = Calendar.getInstance();
            int m = ((int) TimeUnit.MILLISECONDS.toMinutes(c.getTimeInMillis())) - start_min;
            int s = ((int) TimeUnit.MILLISECONDS.toSeconds(c.getTimeInMillis())) - start_secs;
            if (s < 0) {
                m -= 1;
                s = 60 + s;
            }
            Log.d("second", String.valueOf(s));

            Log.d("min", String.valueOf(m));
            SharedPreferences sharedprep = _context.getSharedPreferences("HighScore", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedprep.edit();

            int high_m = sharedprep.getInt("minutes", 0);
            int high_s = sharedprep.getInt("seconds", 0);

            if ((m > high_m) || ((m == high_m) && (s > high_s))) {
                high_m = m;
                high_s = s;
            }
            Log.d("h_second", String.valueOf(high_s));

            Log.d("h_min", String.valueOf(high_m));
            editor.putInt("minutes", high_m);
            editor.putInt("seconds", high_s);
            editor.commit();
        }catch(Exception ex){
            Log.e(getClass().getName(),"save high score",ex);
        }
    }

    public int get_levelNumber() {
        return _levelNumber;
    }


    public Player getPlayer() {
        return _player;
    }

   // public void onLongPress(MotionEvent e) {
   //     _player.onLongPress(e);
   // }

  //  public void onTouch(MotionEvent event) {
    //    _player.onTouch(event);
   // }
  public boolean onTouchEvent(MotionEvent event){
      return _player.onTouchEvent(event);
    //  return true;
  }
    //API

}
