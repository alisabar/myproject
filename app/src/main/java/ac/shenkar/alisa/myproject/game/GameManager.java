package ac.shenkar.alisa.myproject.game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import ac.shenkar.alisa.myproject.BirdSpriteActivity;
import ac.shenkar.alisa.myproject.LevelCompleteActivity;

/**
 * Created by Alisa on 1/21/2017.
 */
public class GameManager {


    static Object lock=new Object();
    int _currentLevelNum =0;
    private static GameManager _instance=null;

    public static GameManager instance() {
        synchronized (lock) {
            return _instance = _instance != null ? _instance : new GameManager();
        }
    };

    Game _currentLevel;

    public void nextLevel(View view) {

        //set next level number
        if(_currentLevel==null){
            return;
        }

        _currentLevelNum = _currentLevel.get_levelNumber()+1;

        ((Activity)view.getContext()).finish();

        //start transition activity;
        view.getContext().startActivity(new Intent(view.getContext(),LevelCompleteActivity.class));

    }

    public void newGame(Context context){
        _currentLevelNum=0;
        _currentLevel=null;
        context.startActivity(new Intent(context,BirdSpriteActivity.class));

    }

    public Game getCurrentLevel(Context context, View view)
    {
        //if current level is not the level we are playing now by _currentLevelNum - assign current level to null
        if (_currentLevel!=null && _currentLevel.get_levelNumber() != _currentLevelNum){
            _currentLevel=null;
        }

        //if we already created current level -> return it.
        if(_currentLevel!=null)
        {
            return _currentLevel;
        }

        //otherwise.. create level by current level number
       else if(_currentLevelNum ==0)
       {
           _currentLevel=new Game(context, view);
           return _currentLevel;
       }

        //level not supported
       return null;

    }

    public boolean isWin() {
        return _currentLevelNum > 0;
    }
}
