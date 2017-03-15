package ac.shenkar.alisa.myproject.game;

import android.graphics.Point;

/**
 * Created by Alisa on 12/28/2016.
 */

public class Obstacle {

    private Point position;

    public Obstacle(int x ,int y)
    {
        position=new Point(x,y);
    }

    public void setPosition(Point location)
    {
        position=location;
    }
    public Point getPosition()

    {
       return position;
    }

    public void movedown()
    {
        int y=position.y;
        y-=5;
        if(0>y) y=0;
         position.y=y;

    }

}