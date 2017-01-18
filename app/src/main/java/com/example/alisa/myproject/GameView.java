package com.example.alisa.myproject;
import android.os.Handler;
import android.util.Log;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.example.alisa.myproject.game.Game;

import static android.os.Looper.getMainLooper;

/**
 * Created by Alisa on 12/29/2016.
 */

public class GameView extends android.support.constraint.ConstraintLayout {
    private MyMusicRunnable _myMusicRunnable;
    private boolean _paused;

    public GameView(Context context) {
        super(context);
    }
    public GameView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setWillNotDraw(false);


    }
    Game _game;

    private void initMusicThread() {
        _myMusicRunnable =new MyMusicRunnable(getContext());
        new Thread(_myMusicRunnable).start();
    }

    public void initGameThread(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(!_game.gameEnded() && !_paused) {
                    _game.updateState();
                    redraw();

                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                //game ended
                //cleanup music
                _myMusicRunnable.stopMusic();
            }
        }).start();

    }

    private void redraw() {
        Handler mainHandler = new Handler(getMainLooper());
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    invalidate();
                } catch (Exception ex) {
                    Log.getStackTraceString(ex);
                }
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        _game.draw(canvas);
    }


    public void onPause() {
        _paused=true;
        _myMusicRunnable.stopMusic();
    }

    public void onResume() {
        _paused=false;
        initMusicThread();
        initGameThread();
    }

    public void onDestroy() {

    }

    public void onCreate() {
        _game=new Game(getContext(),this);
    }
}

