package com.example.alisa.myproject;

import android.graphics.Bitmap;
import android.graphics.Point;

import static android.R.attr.bitmap;

/**
 * Created by Alisa on 12/28/2016.
 */

public class Player {

    private Point position;
    int screen_limit;
    int lives;
    Bitmap image;

    public Player(int screen_limit,Bitmap pic)
    {
        lives=3;
        this.screen_limit=screen_limit;
        position=new Point(screen_limit/2,30);
        this.image=pic;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setScreen_limit(int limit){
        this.screen_limit=limit;
    }
    public void setPosition(Point location)
    {
        position=location;
    }
    public Point getPosition()
    {
       return position;
    }

    public void moveLeft()
    {
        int x=position.x;
        if(x>10) x-=10;
         position.x=x;
    }

    public int getScreen_limit()
    {
        return screen_limit;
    }

    public void move_right()
    {
     int x=position.x;
        if(x<screen_limit-10) x+=10;
        position.x=x;
    }
}
