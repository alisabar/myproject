package ac.shenkar.alisa.myproject;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import ac.shenkar.alisa.myproject.common.Utils;
import ac.shenkar.alisa.myproject.game.Game;
import ac.shenkar.alisa.myproject.game.GameManager;

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
    //Game _game;

    private void initMusicThread() {
        _myMusicRunnable =new MyMusicRunnable(getContext());
        Utils.ThreadPool().execute(_myMusicRunnable);
    }

    public void initGameThread(){

        Utils.ThreadPool().execute(new Runnable() {
            @Override
            public void run() {

                Game game=GameManager.instance().getCurrentLevel(getContext(), GameView.this);

                while(!game.gameEnded() && !_paused) {
                    game.updateState();
                    redraw();

                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                //_game ended
                //cleanup music
                _myMusicRunnable.stopMusic();
            }
        });

    }

    private void redraw() {
        Handler mainHandler = new Handler(getMainLooper());
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    invalidate();
                } catch (Exception ex) {
                    Utils.logError(ex,"error drawing");
                }
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        GameManager.instance().getCurrentLevel(getContext(), this).draw(canvas);
    }


    public void onPause() {
        _paused=true;
        _myMusicRunnable.stopMusic();
        GameManager.instance().getCurrentLevel(getContext(), this).stop();
    }

    public void onResume() {
        _paused=false;
        initMusicThread();
        initGameThread();
        GameManager.instance().getCurrentLevel(getContext(), this).start();
    }

    public void onDestroy() {

    }

    public void onCreate() {
        Game _game = GameManager.instance().getCurrentLevel(getContext(), this);
        _game.start();
    }

    public boolean onTouchEvent(MotionEvent event) {
        return GameManager.instance().getCurrentLevel(getContext(), this).onTouchEvent(event);
    }

    public void onBackPressed() {
        GameManager.instance().getCurrentLevel(getContext(), this).onBackPressed();
    }
}

