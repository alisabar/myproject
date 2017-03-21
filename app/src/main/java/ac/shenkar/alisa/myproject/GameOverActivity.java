package ac.shenkar.alisa.myproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameOverActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        TextView score=(TextView) findViewById(R.id.Score);
        SharedPreferences sharedprep=getSharedPreferences("HighScore", Context.MODE_PRIVATE);

        int high_m= sharedprep.getInt("minutes",0);
        int high_s= sharedprep.getInt("seconds",0);

        score.setText(String.valueOf(high_m)+ " minutes and "+String.valueOf(high_s)+" seconds");
    }

    public void goToMainMenu(View view) {

        finish();
        startActivity(new Intent(this,MainActivity.class));


    }
}
