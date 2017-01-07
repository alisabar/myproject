package com.example.alisa.myproject.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

import com.example.alisa.myproject.R;

/**
 * Created by Alisa on 12/28/2016.
 */

public class GameObject {

    int directionY=10;
    int directionX=0;

    private static final int NUM_FRAMES = 3;
    private static final float X_SPEED = 28f;
    Bitmap bgBitmap;
    Character[] ch;
    float birdXPosition = 10;
    RectF dst0;
    Rect[] frames = new Rect[NUM_FRAMES];
    int mCharHeight;
    int mCharWidth;
    int naiveFrameNam=0;
    Bitmap spritesBitmap;

    private final View _view;
    private final Context _context;
    private Bitmap smallBirdSprite;

    public GameObject(Context context, View view )
    {
        _view=view;
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


    public void updateState()
    {

        if(dst0.centerY()>100 || dst0.centerY()<0)
        {
            directionY=directionY*-1;
        }
        dst0.offset(0,directionY);

        naiveFrameNam=(naiveFrameNam+1)%frames.length;

    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(smallBirdSprite, frames[naiveFrameNam], dst0, null);
    }

}