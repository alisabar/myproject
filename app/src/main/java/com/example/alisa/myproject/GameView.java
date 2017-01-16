package com.example.alisa.myproject;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

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


    }
    Game _game;

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        _game=new Game(getContext(),this);
        initThread();
        MyMusicRunnable mp=new MyMusicRunnable(getContext());
        new Thread(mp).start();
    }

    public void initThread(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    _game.updateState();
                    redraw();

                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
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
}

