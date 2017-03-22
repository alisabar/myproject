package ac.shenkar.alisa.myproject.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import ac.shenkar.alisa.myproject.R;

/**
 * Created by Alisa on 1/7/2017.
 */

public class BirdObstecale extends GameObject {
    int k=1;
    Rect[] featherFrame = new Rect[4];
    int directionY=10;
    Bitmap featherBitmap;

    public BirdObstecale(Context context, View view,Game game, Point location) {
        super(context, view,game, location);
    }

    @Override
    public void collideWithPlayer() {
        was_colision=true;

    }
    public void draw(Canvas canvas){
        try {
            if (was_colision ) {
                if (k!=0){
                featherBitmap = getFeatherBitmap();
                k = (k + 1) % featherFrame.length;
                canvas.drawBitmap(featherBitmap, featherFrame[k], getLocation(), null);
                 }
                if(k==0)
                {

                    was_colision = false;
                    _game.getPlayer().decreaseLife(1);
                    setAlive(false);
                }

            } else{
                super.draw(canvas);
            }

        }
        catch (Exception e)
        {
            Log.e("Bird obstacle draw didnt succed",e.getMessage());

        }
    }
    @Override
    protected int getNumberOfFramesInSprite() {
        return 3;
    }

    @Override
    protected int getSpriteResourceId() {
        return R.drawable.newbird_sprite_down;
    }

    @Override
    public void updateState() {
        getLocation().offset(0,directionY);

        Point screenSize = _game.getScreenSize();
        if(getLocation().centerY() >  screenSize.y){
            setAlive(false);
        }
    }
    @Override
    protected Bitmap createSmallBitmap(Bitmap bitmap){
        double ratio = spritesBitmap.getHeight()/(float)spritesBitmap.getWidth();
        int smallWidth=700;
        int smallHeight = (int)(smallWidth*ratio);
        return Bitmap.createScaledBitmap(spritesBitmap, smallWidth, smallHeight, false);

    }
    public Bitmap createFeatherBitmap(){
        featherBitmap = BitmapFactory.decodeResource(_view.getResources(), R.drawable.bbb);
        double ratio2 = featherBitmap.getHeight()/(float)featherBitmap.getWidth();
        int largeWidth=4000;
        int largeHeight = (int)(largeWidth*ratio2);
        return Bitmap.createScaledBitmap(featherBitmap, largeWidth, largeHeight, false);

    }

    public Bitmap getFeatherBitmap() {
        return createFeatherBitmap();
    }
}
