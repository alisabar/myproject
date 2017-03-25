package ac.shenkar.alisa.myproject.game;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.view.View;

import java.util.Random;

/**
 * Created by Alisa on 1/7/2017.
 */


public abstract class GameObjectCreator
{
    protected final Context _context;
    protected final View _view;
    protected final Game _game;
    private long _createIntervalMilli;


    public GameObjectCreator(Context context, View view, Game game, long createIntervalMilli){
        _context = context.getApplicationContext();
        _view = view;
        _game = game;
        set_createIntervalMilli(createIntervalMilli);
    }

    long _lastCreated;

    public void createObject()
    {
        if (isTimeToCreate()){
            create();
        }
    }

    protected void create() {
        GameObject newObject = CreateInstance();
        _game.addGameObject(newObject);
        lastGameObject=newObject;
        _lastCreated =System.currentTimeMillis();

    }

    @NonNull
    protected abstract GameObject CreateInstance();

    protected Point getNewLocation() {
        return getNextFreeLocation(_game.getScreenSize().x);
    }

    protected boolean isTimeToCreate() {
        return System.currentTimeMillis() - _lastCreated > get_createIntervalMilli();
    }

    static Random random =new Random(System.currentTimeMillis());

    protected Point getNextFreeLocation(int screenWidth)
    {

        float lastX  = lastGameObject!=null? lastGameObject.getLocation().centerX() : -100;
        int nextX=0;
        int minXDiff=30;
        do {
            nextX = random.nextInt(screenWidth);
        }while(Math.abs(lastX-nextX)<minXDiff);
        return new Point(nextX,0);
    }

    static GameObject lastGameObject;


    public long get_createIntervalMilli() {
        return _createIntervalMilli;
    }

    public void set_createIntervalMilli(long _createIntervalMilli) {
        this._createIntervalMilli = _createIntervalMilli;
    }
}

