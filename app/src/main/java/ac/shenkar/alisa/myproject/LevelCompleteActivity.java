package ac.shenkar.alisa.myproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import ac.shenkar.alisa.myproject.game.GameManager;

/**
 * Created by Alisa on 1/21/2017.
 */
public class LevelCompleteActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_completed);

        View view=findViewById(R.layout.activity_level_completed);

        SharedPreferences sharedprep=getSharedPreferences("level info", Context.MODE_PRIVATE);
        int life= sharedprep.getInt("life remained",0);

        TextView final_score=(TextView)findViewById(R.id.score);
        final_score.setText("your score is "+life);
        (findViewById(R.id.btnClickToContinue)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to next level

                finish();

                //if _game manager has next level - go to next level
                if(!GameManager.instance().isWin()){
                    startActivity(new Intent(LevelCompleteActivity.this, BirdSpriteActivity.class));
                }else{
                    // otherwise - open _game success
                    startActivity(new Intent(LevelCompleteActivity.this, GameWinActivity.class));
                }
            }
        });
    }
}
