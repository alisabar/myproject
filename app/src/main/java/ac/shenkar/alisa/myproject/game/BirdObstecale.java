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

    MySFxRunnable sfx;
    private  Thread t;

    public BirdObstecale(Context context, View view,Game game, Point location) {
        super(context, view,game, location);
        sfx= new MySFxRunnable(context);
        t=new Thread(sfx);
        t.start();
        featherBitmap=createFeatherBitmap();
    }

    @Override
    public void collideWithPlayer() {
        was_colision=true;

    }


    public void draw(Canvas canvas){
        try {

            if (was_colision ) {
                sfx.play(R.raw.birdcry);
                drawCollision(canvas);
            } else{
                super.draw(canvas);
            }

        }
        catch (Exception e)
        {
            Log.e(
                    "birdGame" + getClass().getName()
                    ,"Bird obstacle draw didnt succed "+e.getMessage(),e);
        }
    }

    private void drawCollision(Canvas canvas) {
        int save = canvas.save();
        canvas.scale(1.3f, 1.3f, getLocation().centerX(), getLocation().centerY());
        canvas.drawBitmap(featherBitmap, featherFrame[0], getLocation(), null);
        canvas.restoreToCount(save);
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
        updateStateOutOfScreen(screenSize);
        updateStateHitPlayer();
    }

    private void updateStateHitPlayer() {
        if(was_colision){
            if(k++>10){
                setAlive(false);
                _game.getPlayer().decreaseLife(1);
            }
        }
    }

    private void updateStateOutOfScreen(Point screenSize) {
        if(getLocation().centerY() >  screenSize.y){
            stopMusicThread();
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
    private Bitmap createFeatherBitmap(){
        Bitmap featherBitmap = BitmapFactory.decodeResource(_view.getResources(), R.drawable.bbb);
        double ratio2 = featherBitmap.getHeight()/(float)featherBitmap.getWidth();
        int largeWidth=4000;
        int largeHeight = (int)(largeWidth*ratio2);
        return Bitmap.createScaledBitmap(featherBitmap, largeWidth, largeHeight, false);

    }

    public Bitmap getFeatherBitmap() {
        return featherBitmap;
    }

    public void stopMusicThread(){
        if(t!=null){
            t.interrupt();
            t=null;
        }
    }

}
