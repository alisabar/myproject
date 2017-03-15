package ac.shenkar.alisa.myproject.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.View;

import ac.shenkar.alisa.myproject.R;

/**
 * Created by Alisa on 1/7/2017.
 */

public class BirdObstecale extends GameObject {
    int directionY=10;

    public BirdObstecale(Context context, View view,Game game, Point location) {
        super(context, view,game, location);
    }

    @Override
    public void collideWithPlayer() {
        _game.getPlayer().decreaseLife(1);
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


}