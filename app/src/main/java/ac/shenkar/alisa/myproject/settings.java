package ac.shenkar.alisa.myproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class settings extends AppCompatActivity {

    private SeekBar seek_bar;
    int progress_value;
   TextView t;

    Switch effect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        seek_bar = (SeekBar)findViewById(R.id.seekBar);
        effect=(Switch) findViewById(R.id.soundEffects);

        final SharedPreferences sharedprep = getSharedPreferences("User_settings", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedprep.edit();
        t=(TextView)findViewById(R.id.seekbar_Data);
        progress_value=sharedprep.getInt("background_volume", 100);
        seek_bar.setProgress(progress_value);

        effect.setChecked(sharedprep.getBoolean("effects",true));

        t.setText(progress_value+"%");
        seek_bar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {


                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                        progress_value=i;
                          t.setText(progress_value+"%");
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        editor.putInt("background_volume", progress_value);
                        editor.commit();
                    }
                });


        effect.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        editor.putBoolean("effects",b);
                        editor.commit();
                    }
                }
        );

    }

    public void menuButton(View view) {
        startActivity(new Intent(this,MainActivity.class));
    }
}
