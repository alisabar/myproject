package ac.shenkar.alisa.myproject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.view.GestureDetectorCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class BirdSpriteActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bird_sprite);
        ((GameView)findViewById(R.id.gameView)).onCreate();
       // initTouch();

    }

    private GestureDetectorCompat mDetector;



    public boolean onTouchEvent(MotionEvent event){
        View view = ((GameView)findViewById(R.id.gameView));
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override

            public boolean onTouch (View view, MotionEvent event){
                if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
                    Log.d("TouchTest", "Touch down");

                    //while (!(event.getAction() == android._view.MotionEvent.ACTION_UP)) {
                        view.onTouchEvent(event);
                    //}
                }
                return false;

            }
        });
        return false;
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
