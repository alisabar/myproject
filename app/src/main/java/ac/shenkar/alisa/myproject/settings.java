package ac.shenkar.alisa.myproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

public class settings extends AppCompatActivity {

    private static SeekBar seek_bar;
    int progress_value;
    Toast t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        seek_bar = (SeekBar)findViewById(R.id.seekBar);
        final SharedPreferences sharedprep = getSharedPreferences("User_settings", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedprep.edit();
        progress_value=sharedprep.getInt("background_volume", 0);
        seek_bar.setProgress(progress_value);
        seek_bar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {


                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                        progress_value=i;
                          t.setText(progress_value+"%");
                        t.show();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                     t=Toast.makeText(settings.this,progress_value+"%",Toast.LENGTH_SHORT);
                       t.show();
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        editor.putInt("background_volume", progress_value);
                        editor.commit();
                    }
                });
    }

    public void menuButton(View view) {
        startActivity(new Intent(this,MainActivity.class));
    }
}
