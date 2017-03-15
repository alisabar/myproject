package ac.shenkar.alisa.myproject.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Alisa on 12/28/2016.
 */

public abstract class GameObject  {

    int directionX=0;

    private final int NUM_FRAMES = getNumberOfFramesInSprite();
    private static final float X_SPEED = 28f;
    Bitmap bgBitmap;
    Character[] ch;
    float birdXPosition = 10;
    private RectF location;
    Rect[] frames = new Rect[NUM_FRAMES];
    int mCharHeight;
    int mCharWidth;
    int naiveFrameNam=0;
    Bitmap spritesBitmap;

    protected final View _view;
    protected final Context _context;
    protected Game _game;
    //private Bitmap smallBirdSprite;

    private boolean alive;


    public GameObject(
            Context context
            , View view
            ,Game game
            ,Point location
    )
    {
        _view=view;
        _context=context;
        _game = game;

        prepareCharacter();
        float width = frames[0].right-frames[0].left;
        float height = frames[0].bottom-frames[0].top;
        this.setLocation(new RectF(location.x,location.y,location.x+width,location.y+height));
        this.setAlive(true);
    }


    private void prepareCharacter() {
        spritesBitmap = BitmapFactory.decodeResource(_view.getResources(), getSpriteResourceId());
        spritesBitmap=createSmallBitmap(spritesBitmap);

        // setup the rects

        int frameNum= getNumberOfFramesInSprite();

        mCharWidth = spritesBitmap.getWidth() / frameNum;
        mCharHeight = spritesBitmap.getHeight();

        int i = 0; // rect index
        for (int x = 0; x < frameNum; x++) { // column
            frames[i] = new Rect(x * mCharWidth, 0, (x + 1) * mCharWidth, mCharHeight);
            i++;
            if (i >= NUM_FRAMES) {
                break;
            }
        }
        setLocation(new RectF(birdXPosition, 0,
                birdXPosition+mCharWidth,
                mCharHeight));

    }

    protected Bitmap createSmallBitmap(Bitmap spritesBitmap)
    {
        return spritesBitmap;
    }

    protected abstract int getNumberOfFramesInSprite();

    protected abstract  int getSpriteResourceId() ;

    public abstract void updateState();

    public void draw(Canvas canvas)
    {
        naiveFrameNam=(naiveFrameNam+1)%frames.length;
        canvas.drawBitmap(spritesBitmap, frames[naiveFrameNam], getLocation(), null);
    }

    public RectF getLocation() {
        return location;
    }

    public void setLocation(RectF location) {
        this.location = location;
    }



    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void collideWithPlayer() {

    }


}