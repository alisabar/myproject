package ac.shenkar.alisa.myproject.game;

import android.content.Context;
import android.graphics.Point;
import android.view.View;

import ac.shenkar.alisa.myproject.R;

/**
 * Created by Alisa on 1/21/2017.
 */

public class LifeBonusObject extends GameObject {
    public LifeBonusObject(Context context, View view, Game game, Point location) {
        super(context, view, game, location);
    }

    @Override
    protected int getNumberOfFramesInSprite() {
        return 1;
    }

    @Override
    protected int getSpriteResourceId() {
        return R.drawable.heart_full_32x32;
    }

    @Override
    public void updateState() {
        float directionY=10;
        getLocation().offset(0,directionY);

        Point screenSize = _game.getScreenSize();
        if(getLocation().centerY() >  screenSize.y){
            setAlive(false);
        }
    }

    @Override
    public void collideWithPlayer() {
        _game.getPlayer().increaseLife(1);
        setAlive(false);
    }
}
