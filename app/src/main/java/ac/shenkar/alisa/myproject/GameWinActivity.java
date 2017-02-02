package ac.shenkar.alisa.myproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Alisa on 1/21/2017.
 */
public class GameWinActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_win);
        findViewById(R.id.btnClickToContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(GameWinActivity.this,MainActivity.class));
            }
        });
    }
}
