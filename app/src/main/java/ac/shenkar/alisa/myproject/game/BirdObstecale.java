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
import ac.shenkar.alisa.myproject.common.Utils;
import ac.shenkar.alisa.myproject.sound.SoundManager;

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

        featherBitmap=Utils.GetOrCreate(Utils.CACHE_BIRD_FEATHER_BITMAP, new Utils.Creator<Bitmap>() {

            @Override
            public Bitmap create() {
                return createFeatherBitmap();
            }
        });
    }

    @Override
    public void collideWithPlayer() {
        was_colision=true;
        playBirdSound();
    }


    public void draw(Canvas canvas){
        try {

            if (was_colision ) {
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

    private void playBirdSound() {
        SoundManager.Instance(_context).playSound(R.raw.birdcry);
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
            setAlive(false);
        }
    }

    @Override
    protected Bitmap createSmallBitmap(Bitmap bitmap){
        double ratio = bitmap.getHeight()/(float)bitmap.getWidth();
        int smallWidth=700;
        int smallHeight = (int)(smallWidth*ratio);
        return Bitmap.createScaledBitmap(bitmap, smallWidth, smallHeight, false);

    }
    private Bitmap createFeatherBitmap(){
        Bitmap featherBitmap = BitmapFactory.decodeResource(_view.getResources(), R.drawable.bbb);
        double ratio2 = featherBitmap.getHeight()/(float)featherBitmap.getWidth();
        int largeWidth=4000;
        int largeHeight = (int)(largeWidth*ratio2);
        return Bitmap.createScaledBitmap(featherBitmap, largeWidth, largeHeight, false);
    }


}
