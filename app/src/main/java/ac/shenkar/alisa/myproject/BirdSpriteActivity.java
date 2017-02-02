package ac.shenkar.alisa.myproject;

import android.app.Activity;
import android.support.v4.view.GestureDetectorCompat;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class BirdSpriteActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bird_sprite);
        ((GameView)findViewById(R.id.gameView)).onCreate();
        initTouch();

    }
    private GestureDetectorCompat mDetector;


    private void initTouch() {
        mDetector = new GestureDetectorCompat(this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                ((GameView)findViewById(R.id.gameView)).onFling(e1,e2,velocityX,velocityY);
                return true;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ((GameView)findViewById(R.id.gameView)).onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((GameView)findViewById(R.id.gameView)).onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((GameView)findViewById(R.id.gameView)).onDestroy();
    }
}
