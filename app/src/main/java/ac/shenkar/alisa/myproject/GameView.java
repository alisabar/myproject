package ac.shenkar.alisa.myproject;
import android.os.Handler;
import android.util.Log;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;

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
    private Game _game;

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

    public void startGameLoopThread(){

        Utils.ThreadPool().execute(new Runnable() {
            @Override
            public void run() {

                Game game=GameManager.instance().getCurrentLevel(getContext(), GameView.this);
                try {
                    if(game==null){
                        Log.wtf(Utils.LOG_TAG,"could not load game");
                    }
                    while(!game.gameEnded() && !_paused) {
                        game.updateState();
                        drawGame();

                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    Utils.logError(e,"error during game loop");
                }

                //_game ended
                //cleanup music
                _myMusicRunnable.stopMusic();
            }
        });

    }

    private void drawGame() {
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
        if(_game!=null) {
            _game.draw(canvas);
        }
    }


    public void onPause() {
        _paused=true;
        _myMusicRunnable.stopMusic();
        _game.stop();
        _game=null;
    }

    public void onResume() {
        _paused=false;
        initMusicThread();
        startGameLoopThread();
        initGameInstance();
    }

    private void initGameInstance() {
        _game= GameManager.instance().getCurrentLevel(getContext(), this);
        if(_game!=null) {
            _game.start();
        }
        else{

            //if could not get game object...
            Log.e(Utils.LOG_TAG,"could not load game!");

            //go to main menu
            Utils.gotoMainMenu(this);

        }
    }

    public void onDestroy() {
        //stop current game when quiting game
        if(_game!=null){
            _game.stop();
            _game=null;
        }

    }

    public void onCreate() {
       //nothing
    }

    public boolean onTouchEvent(MotionEvent event) {

        //handle touch event , by passing touch event message to game , if game exists
        try {
            if(_game!=null) {
                return _game.onTouchEvent(event);
            }else {
                return false;
            }
        } catch (Exception e) {
            Utils.logError(e,"onTouchEvent");
            return false;
        }
    }

    public void onBackPressed() {

        //handle back pressed event , by passing back pressed event message to game , if game exists

        if(_game!=null){
            _game.onBackPressed();
        }

    }
}

