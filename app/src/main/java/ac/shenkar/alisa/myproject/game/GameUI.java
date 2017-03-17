package ac.shenkar.alisa.myproject.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Alisa on 3/17/2017.
 */

public abstract class GameUI {

    protected final Context _context;
    protected  final View _view;
    protected final Game _game;
    protected final Point _location;
    protected Point _size;

    public GameUI(Context context, View view, Game game, Point location)
    {
        this._context = context;
        this._view = view;
        this._game = game;
        this._location = location;

        this._size= getSize();

        _view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                if(event.getAction() != MotionEvent.ACTION_UP){
                    return false;
                }

                RectF rect=new RectF(
                        _location.x
                        ,_location.y
                        ,_location.x+_size.x
                        ,_location.y+_size.y);


                if(rect.contains(event.getX(),event.getY())){
                    GameUI.this.onCLick();
                }

                return false;
            }
        });

    }

    protected abstract void onCLick();

    protected abstract Point getSize();

    public abstract void draw(Canvas canvas);

}
