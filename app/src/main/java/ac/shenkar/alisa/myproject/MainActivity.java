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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Fabric.with(this, new Crashlytics());
    }

    public void PlayButton(View view){
        GameManager.instance().newGame(this);
    }

    public void SettingButton(View view) {
        startActivity(new Intent(this,settings.class));

    }

    public void openAboutActivity(View view) {
        startActivity(new Intent(this,About.class));
    }
}
