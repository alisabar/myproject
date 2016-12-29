package com.example.alisa.myproject;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Alisa on 12/29/2016.
 */

public class GameView extends View {
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
}
