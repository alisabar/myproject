package com.example.alisa.myproject.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

import com.example.alisa.myproject.R;

/**
 * Created by Alisa on 1/7/2017.
 */

public class Game {

    private final View _view;
    private final Context _context;


    int posX=0;
    int posY=0;

    int directionY=10;
    int directionX=0;

    private static final int NUM_FRAMES = 3;
    private static final float X_SPEED = 28f;
    Bitmap bgBitmap;
    Character[] ch;
    float birdXPosition = 10;
    RectF dst0;
    RectF dst1 = new RectF();
    Rect[] frames = new Rect[NUM_FRAMES];
    int mCharHeight;
    int mCharWidth;
    int naiveFrameNam=0;
    Bitmap spritesBitmap;
    Bitmap Bitmapb1;
    Bitmap Bitmapb2;
    Bitmap Bitmapb3;

    Paint topRectPaint = new Paint();
    private int mViewHeight;
    private int mViewWidth;
    private Bitmap smallBirdSprite;

    public Game(Context context, View view)
    {
        _view =view;
        _context=context;

        dst0 = new RectF(frames[0]);
        dst0.offset(10, 10); // like translate for canvas
        prepareCharacter();

    }

    private void prepareCharacter() {
        spritesBitmap = BitmapFactory.decodeResource(_view.getResources(), R.drawable.newbird_sprite);

        double ratio = spritesBitmap.getHeight()/(float)spritesBitmap.getWidth();
        int smallWidth=700;
        int smallHeight = (int)(smallWidth*ratio);
        smallBirdSprite = Bitmap.createScaledBitmap(spritesBitmap, smallWidth, smallHeight, false);


        // setup the rects
        mCharWidth = smallBirdSprite.getWidth() / 3;
        mCharHeight = smallBirdSprite.getHeight();

        int i = 0; // rect index
        for (int x = 0; x < 3; x++) { // column
            frames[i] = new Rect(x * mCharWidth, 0, (x + 1) * mCharWidth, mCharHeight);
            i++;
            if (i >= NUM_FRAMES) {
                break;
            }
        }
        dst0=new RectF(birdXPosition, 0,
                birdXPosition+mCharWidth,
                mCharHeight);

    }

    private void createBackgroundImage(Canvas canvas) {

        if (bgBitmap != null) {
            bgBitmap.recycle();
        }
    }


    //API

    public void updateState()
    {
        posY++;

        if(dst0.centerY()>100 || dst0.centerY()<0)
        {
            directionY=directionY*-1;
        }
        dst0.offset(0,directionY);

        naiveFrameNam=(naiveFrameNam+1)%frames.length;

    }

    public void draw(Canvas canvas)
    {
        createBackgroundImage(canvas);

        canvas.drawBitmap(smallBirdSprite, frames[naiveFrameNam], dst0, null);
    }

    //API
}
