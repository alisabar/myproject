package ac.shenkar.alisa.myproject.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import ac.shenkar.alisa.myproject.R;

/**
 * Created by Alisa on 3/17/2017.
 */

public class PlayPauseButton extends GameUI {
    private Bitmap _pauseButtonImage;
    private Bitmap _playButtonImage;
    MySFxRunnable sfx;
    private  Thread t;


    public PlayPauseButton(Context context, View view, Game game, Point location) {

        super(context,view,game,location);

        _playButtonImage= BitmapFactory.decodeResource(_view.getResources(), R.drawable.playbutton);
        _playButtonImage= Bitmap.createScaledBitmap(_playButtonImage,_size.x,_size.y,true);

        _pauseButtonImage= BitmapFactory.decodeResource(_view.getResources(), R.drawable.pausebutton);
        _pauseButtonImage= Bitmap.createScaledBitmap(_pauseButtonImage,_size.x,_size.y,true);
        sfx= new MySFxRunnable(context);
        t=new Thread(sfx);
        t.start();
    }

    @NonNull
    protected Point getSize() {
        return new Point(100,100);
    }

    public void draw(Canvas canvas)
    {
        //todo
        Bitmap image;
        if(_game.isPaused()){
            image=_playButtonImage;
        }
        else{
            image=_pauseButtonImage;
        }
        canvas.drawBitmap(image,_location.x,_location.y,null);

    }

    protected void onCLick()
    {
      sfx.play(R.raw.pause);
        _game.togglePause();
    }


}


