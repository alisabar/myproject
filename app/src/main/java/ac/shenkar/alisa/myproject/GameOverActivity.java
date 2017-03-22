package ac.shenkar.alisa.myproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ac.shenkar.alisa.myproject.game.LifeBar;

public class GameOverActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

    //    TextView score=(TextView) findViewById(R.id.Score);
     //   SharedPreferences sharedprep=getSharedPreferences("level info", Context.MODE_PRIVATE);

   //     int life= sharedprep.getInt("life remained",0);

    //    _sun = BitmapFactory.decodeResource(this.getResources(), R.drawable.sun);
    //    for(int i=0;i<life;i++){
    //        ImageView img=new ImageView(this);
    //        img.setImageBitmap(_sun);

    //        ((LinearLayout)view).addView(img);
     //   }

        }


    public void goToMainMenu(View view) {

        finish();
        startActivity(new Intent(this,MainActivity.class));


    }


}
