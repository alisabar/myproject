package ac.shenkar.alisa.myproject.game;

import android.content.Context;
import android.graphics.Point;
import android.view.View;

import ac.shenkar.alisa.myproject.R;

/**
 * Created by Alisa on 1/21/2017.
 */

public class LifeBonusObject extends GameObject {


    private MySFxRunnable _MySfxRunnable;
    private  Thread t;

    public LifeBonusObject(Context context, View view, Game game, Point location) {
        super(context, view, game, location);

        _MySfxRunnable=new MySFxRunnable(context);
        t=new Thread(_MySfxRunnable);
        t.start();

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
       //     _MySfxRunnable.play(R.raw.collected);
            setAlive(false);
            stopThread();
        }
    }

    @Override
    public void collideWithPlayer() {
        _game.getPlayer().increaseLife(1);
        _MySfxRunnable.play(R.raw.collected);
        setAlive(false);
        stopThread();
    }
    public void stopThread(){
        if(t!=null){
           t.interrupt();
           t=null;
        }
    }
}
