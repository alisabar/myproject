package com.example.alisa.myproject;
import android.os.Handler;
import android.util.Log;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.example.alisa.myproject.game.Game;

import static android.os.Looper.getMainLooper;

/**
 * Created by Alisa on 12/29/2016.
 */

public class GameView extends android.support.constraint.ConstraintLayout {
    public GameView(Context context) {
        super(context);
    }
    public GameView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setWillNotDraw(false);
        _game=new Game(getContext(),this);
      initThread();

    }
    Game _game;

    public void initThread(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    _game.updateState();
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


                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        _game.draw(canvas);
    }
}

