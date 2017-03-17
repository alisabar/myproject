package ac.shenkar.alisa.myproject.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.View;

import ac.shenkar.alisa.myproject.R;

/**
 * Created by Alisa on 3/17/2017.
 */

public class LifeBar extends GameUI {
    private Bitmap _sun;

    public LifeBar(Context context, View view, Game game, Point location) {
        super(context, view, game, location);


        _sun= BitmapFactory.decodeResource(_view.getResources(), R.drawable.sun);
        _sun= Bitmap.createScaledBitmap(_sun,_size.x,_size.y,true);

    }

    @Override
    protected void onCLick() {

    }

    @Override
    protected Point getSize() {
        return new Point(100,100);
    }

    @Override
    public void draw(Canvas canvas) {
        int lifePts=_game.getPlayer().getLifePoints();
        for (int i = 0; i < lifePts; i++) {
           // canvas.drawRoundRect(10+i*20,40,(float)10+20+i*20,(float)40+20,3,3,new Paint());
            canvas.drawBitmap(_sun,10+i*_size.x,0,null);
        }

    }
}
