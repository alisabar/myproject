package com.example.alisa.myproject;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Alisa on 12/29/2016.
 */

public class GameView extends View {
    private static final int NUM_FRAMES = 64;
    private static final float X_SPEED = 28f;
    Bitmap bgBitmap;
    Character[]  ch;
    RectF dst0 = new RectF();
    RectF dst1 = new RectF();
    Rect[] frames = new Rect[NUM_FRAMES];
    int mCharHeight;
    int mCharWidth;
    int naiveFrameNam;
    Bitmap Bitmapb1;
    Bitmap Bitmapb2;
    Bitmap Bitmapb3;
    Paint topRectPaint = new Paint();
    private int mViewHeight;
    private int mViewWidth;
    public GameView(Context context) {
        super(context);
    }

    public GameView(Context context,AttributeSet attributeSet ) {
        super(context,attributeSet );
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(0,0,20,20,0,0,true,new Paint());
        Resources res = getResources();
        //Drawable drawable = res.getDrawable(R.drawable.bird1);
        //drawable.draw(canvas);
    }
    private void prepareCharacter() {
       Bitmap spritesBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.newbird_sprite);
        // setup the rects
        mCharWidth = spritesBitmap.getWidth() / 3;
        mCharHeight = spritesBitmap.getHeight();

        int i = 0; // rect index
        for (int y = 0; y < 6; y++) { // row
            for (int x = 0; x < 12; x++) { // column
                frames[i] = new Rect(x * mCharWidth, y * mCharHeight, (x + 1) * mCharWidth, (y + 1) * mCharHeight);
                i++;
                if (i >= NUM_FRAMES) {
                    break;
                }
            }
        }
    }

}
