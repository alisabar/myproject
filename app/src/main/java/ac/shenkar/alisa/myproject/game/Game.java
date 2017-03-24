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
import ac.shenkar.alisa.myproject.common.Utils;
import ac.shenkar.alisa.myproject.sound.SoundManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Alisa on 1/7/2017.
 */

public class Game {

    private final View _view;
    private final Context _context;
    private final List<GameObject> _ganmeObjects;
    private final List<GameObject> _ganmeObjectsToAdd;


    private final GameObjectCreator _birdCreator;
    private final Player _player;
    private final LifeBonusCreator _lifeObjCreator;
    private final PlayPauseButton _playPauseButton;
    private final LifeBar _lifebar;
    private int _timeToEndOfLevelSec2;
    private Bitmap _background;
    private final Paint _textPaint;

    private  Calendar c = Calendar.getInstance();
    private final int start_secs= (int) TimeUnit.MILLISECONDS.toSeconds(c.getTimeInMillis());
    private final int start_min=(int) TimeUnit.MILLISECONDS.toMinutes(c.getTimeInMillis());

    private int _levelNumber;
    private boolean _paused;
    private long _lastUpdateState;

    public Game(Context context, View view)
    {
        _lifebar=new LifeBar(context,view,this,new Point(0,0));
        _textPaint = new Paint();
        _textPaint.setColor(Color.BLACK);
        _textPaint.setTextSize(50);

        _gameEnded=false;
        _view =view;
        _context=context;
        _ganmeObjects = new ArrayList<GameObject>();
        _ganmeObjectsToAdd= new ArrayList<GameObject>();
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
        _timeToEndOfLevelSec2=60*2 /*2 minutes*/;

        _levelNumber = 0;

        _playPauseButton =new PlayPauseButton(context,view,this,new Point(getScreenSize().x-100,0));

        initCreators();

        SoundManager.Instance(_context);
    }

    private void initCreators() {

        Utils.ThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                while(!_paused){
                    _birdCreator.createObject();
                    _lifeObjCreator.createObject();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Utils.logError(e,"creators thread crashed");
                    }
                }
            }
        });

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
        //Log.d(getClass().getName(),"updateState enter");
    try {
    if (_gameEnded || _paused) {
        return;
    }
    for (GameObject gameObj : _ganmeObjects) {
        gameObj.updateState();
    }

    for (GameObject gameObj : new ArrayList<>(_ganmeObjects)) {
        if (!gameObj.isAlive()) {
            _ganmeObjects.remove(gameObj);
        }
    }

        addItems();

    _player.updateState();

    //handle collisions
    RectF playerLocation = new RectF(_player.getLocation());

    for (GameObject gameObj : new ArrayList<>(_ganmeObjects)) {
        // if collides
        if (playerLocation.intersect(gameObj.getLocation())) {
            gameObj.collideWithPlayer();
        }
    }

    //is end _game
    if (_player.getLifePoints() <= 0) {
        //end of _game
        gameOver();
    } else if (isCompleteLevel()) {
        startNextLevel();
    }

    computeTimeToEndLevel();


    //Log.d(getClass().getName(),"updateState exit");
    }
    catch(Exception ex){
        Log.e("birdGame" + getClass().getName(),"update state "+ex.getMessage(),ex);
    }
    }

    private void addItems() {
        synchronized (_ganmeObjects) {
            _ganmeObjects.addAll(_ganmeObjectsToAdd);
            _ganmeObjectsToAdd.clear();
        }
    }

    private void computeTimeToEndLevel() {
        //current time
        long currentTime = System.currentTimeMillis();
        if(_lastUpdateState==0){
            _lastUpdateState=System.currentTimeMillis();
        }
        //last time computed
        long timeDiffSec=(currentTime-_lastUpdateState)/1000;

        if(timeDiffSec>0) {
            _timeToEndOfLevelSec2 -= timeDiffSec;
            _lastUpdateState = System.currentTimeMillis();
        }
    }

    private boolean isCompleteLevel() {
        return _timeToEndOfLevelSec2<=0;
        //return System.currentTimeMillis() > _timeToEndOfLevelMilli;
    }

    private void startNextLevel() {
        _gameEnded=true;
        saveHighScore();
        GameManager.instance().nextLevel(_context,_view);
    }

    private void gameOver() {
        _gameEnded=true;
        saveHighScore();

        //we do not want to go back t bird sprite activity
        ((Activity)_context).finish();

        //go to _game over screen
        _context.startActivity(new Intent(_context, GameOverActivity.class));
    }

 //   private void collideWithObstecle() {
 //       _player.decreaseLife(1);
 //   }


    public void draw(Canvas canvas)
    {
        if(_gameEnded){
            return;
        }
        drawBackgroundImage(canvas);

        drawLife(canvas);

        drawTimeToComplete(canvas);

        drawPlayPause(canvas);

        for (GameObject gameObj: _ganmeObjects) {
            gameObj.draw(canvas);
        }
        _player.draw(canvas);
    }

    private void drawPlayPause(Canvas canvas) {
        _playPauseButton.draw(canvas);

    }

    private void drawTimeToComplete(Canvas canvas) {
        try {
            String timeString=_timeToEndOfLevelSec2+"";//(_timeToEndOfLevelMilli-System.currentTimeMillis())/1000+"";

            canvas.drawText(timeString,10,80,_textPaint);
        } catch (Exception e) {
            Log.e(getClass().getName(),"drawTimeToComplete",e);
        }
    }

    private void drawLife(Canvas canvas) {
        _lifebar.draw(canvas);
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
        synchronized (_ganmeObjects) {
            _ganmeObjectsToAdd.add(gameObject);
        }
    }




    //happens when user slide
    /*
    public void onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        _player.onFling(e1, e2, velocityX, velocityY);
    }
*/
    public void saveHighScore(){
        try {

            SharedPreferences sharedprep = _context.getSharedPreferences("level info", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedprep.edit();

            editor.putInt("life remained", _player.getLifePoints());
           // editor.putInt("seconds", high_s);
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

    public boolean isPaused() {
        return _paused;
    }

    public void togglePause(){
        _paused=!_paused;
        _lastUpdateState=0;
        if(!_paused){
            SoundManager.Instance(_context).playSound(R.raw.pause);
            initCreators();
        }
    }

    public void setPaused(boolean _paused) {
        this._paused = _paused;
    }
    //API

}
