package ac.shenkar.alisa.myproject.game;



/**
 * Created by Alisa on 3/25/2017.
 */

public class HighScore {
    static HighScore instance;
    static Object instanceLock=new Object();


    public static HighScore Instance()
    {
        if(instance!=null){
            return instance;
        }
synchronized (instanceLock){
    instance=instance!=null?instance:new HighScore();
    return instance;
}
    }

    private int score=0;
    public void newScore(){
        score=0;
    }

    public void addScore(int score){
        this.score+=score;
    }

    public int getScore(){
        return score;
    }

}
