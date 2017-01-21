package com.example.alisa.myproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.alisa.myproject.game.GameManager;

/**
 * Created by Alisa on 1/21/2017.
 */
public class LevelCompleteActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_completed);

        findViewById(R.id.btnClickToContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to next level

                finish();

                //if game manager has next level - go to next level
                if(!GameManager.instance().isWin()){
                    startActivity(new Intent(LevelCompleteActivity.this, BirdSpriteActivity.class));
                }else{
                    // otherwise - open game success
                    startActivity(new Intent(LevelCompleteActivity.this, GameWinActivity.class));
                }
            }
        });
    }
}
