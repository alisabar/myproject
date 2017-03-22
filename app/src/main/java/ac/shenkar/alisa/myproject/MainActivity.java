package ac.shenkar.alisa.myproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ac.shenkar.alisa.myproject.game.GameManager;

import com.google.firebase.analytics.FirebaseAnalytics;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;
    MyMainActivityMusicRunnable m_music;
    Thread t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Fabric.with(this, new Crashlytics());

        m_music=new MyMainActivityMusicRunnable(this);

        t=new Thread(m_music);
        t.start();
    }

    public void PlayButton(View view){
           m_music.stopMusic();
           t.interrupt();

        GameManager.instance().newGame(this);
    }

    public void SettingButton(View view) {
        startActivity(new Intent(this,settings.class));

    }

    public void openAboutActivity(View view) {
        startActivity(new Intent(this,About.class));
    }

    @Override
    protected void onRestart() {
       t.run();
        super.onRestart();
    }

    @Override
    protected void onStop() {
        m_music.stopMusic();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        m_music.stopMusic();
        t.interrupt();
        super.onDestroy();
    }
}


