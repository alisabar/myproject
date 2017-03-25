package ac.shenkar.alisa.myproject.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.EventLog;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import ac.shenkar.alisa.myproject.R;

/**
 * Created by Alisa on 12/28/2016.
 */

public class Player extends GameObject{
    private static final long MAX_TIME_TO_BE_INVINCIBLE_MILLI = 1000 * 2;
    public static final int MAX_LIFE_POINTS = 2;
    private boolean isPressedMoveRight=false;
    private boolean isPressedMoveLeft=false;
    private int lifePoints;
    private boolean _isInvincible;
    private long _isInvincibleStartedMilli;
    private long _moveUntil;

    public int getLifePoints(){
        return lifePoints;
    }
    public Player(Context context, View view, Game game, Point location) {
        super(context, view, game, location);
        lifePoints=3;
        _isInvincible=false;
        _isInvincibleStartedMilli=0;

    }

    public void moveLeft()
    {
        RectF location=this.getLocation();
        if(location.left==0)
        {
            return;
        }

        location.offset(-10,0);
        if(location.centerX()<0)
        {
            location.offset(10,0);
        }
        if(location.left==0)
        {

        }
        if(location.right==_game.getScreenSize().x)
        {

        }
    }

    public void move_right()
    {
        RectF location=this.getLocation();
        if(location.right==_game.getScreenSize().x)
        {
            return;
        }

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

        //update movement
     // updateMovement();
    }

    @Override
    void collideWithPlayer() {

    }

    private void updateMovement() {
        if(System.currentTimeMillis()<_moveUntil) {
            RectF location = this.getLocation();
            RectF locationBeforeMove = location;

            location.offset(_moveVectorX, 0);

            if (location.centerX() > _game.getScreenSize().x || location.centerX() < 0) {
                location = locationBeforeMove;
            }
            if(isPressedMoveLeft){
                moveLeft();
            }
            if(isPressedMoveRight)
            {
                move_right();
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
/*

    public void onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        int moveDuration=0;
        Log.i(getClass().getName(),"onFling "+velocityX+" , "+velocityY);
        if (Math.abs(velocityX) < 20){
            moveDuration=1000;
            _moveVectorX =5;
        }else if (Math.abs(velocityX) < 50){
            moveDuration=2000;
            _moveVectorX =6;
        }else {
            moveDuration=2000;
            _moveVectorX =10;
        }

        _moveUntil = System.currentTimeMillis() + moveDuration;
        _moveVectorX = (velocityX>0?1:-1) * _moveVectorX;
    }

*/
    int _moveVectorX =0;

    public void increaseLife(int i) {
        if (_isInvincible || lifePoints>MAX_LIFE_POINTS){
            return;
        }

        _isInvincible=true;
        _isInvincibleStartedMilli= System.currentTimeMillis();
        lifePoints=lifePoints+i;

        Log.i(getClass().getName(),"player fell in love! life points: "+getLifePoints());
    }
/*


    public void onLongPress(MotionEvent event) {

    }

    public void onTouch(MotionEvent event) {
     if( event.getAction() == MotionEvent.ACTION_UP) {
         isPressedMoveRight=false;
         isPressedMoveLeft=false;
     }
    }
   */
    /*
    final GestureDetector gestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
        public void onLongPress(MotionEvent e) {
            Log.e("", "Longpress detected");
            if (e.getX() > getLocation().centerX()) {
                isPressedMoveRight=true;
                move_right();


            } else if (e.getX() < getLocation().centerX()) {
                isPressedMoveLeft=true;
                moveLeft();
            }
        }
    });
*/
    public boolean onTouchEvent(MotionEvent event) {

        if(_game.isPaused()){
            return true;
        }

        if (event.getX() > getLocation().centerX()) {
            isPressedMoveRight=true;
            move_right();


        } else if (event.getX() < getLocation().centerX()) {
            isPressedMoveLeft=true;
            moveLeft();
        }
        return true;
    };
}
