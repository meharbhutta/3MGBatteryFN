package com.muhammadmehar.mmmgbatteryfn;

import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Context mContext = this;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        final SharedPreferences.Editor editor = preferences.edit();
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.check(preferences.getInt( getString(R.string.rad), -1));
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                try {
                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                    }
                    if (i == R.id.radioButton1) {
                        mediaPlayer = MediaPlayer.create(mContext, R.raw.audio1);
                    } else if (i == R.id.radioButton2) {
                        mediaPlayer = MediaPlayer.create(mContext, R.raw.audio2);
                    } else if (i == R.id.radioButton3) {
                        mediaPlayer = MediaPlayer.create(mContext, R.raw.audio3);
                    }
                    if (mediaPlayer != null) {
                        mediaPlayer.setVolume(0.9f,0.9f);
                        mediaPlayer.start();
                    }
                } catch (Exception e) {

                }
                editor.putInt(getString(R.string.rad), i);
                editor.apply();
            }
        });
        final SwitchCompat switchControl = (SwitchCompat) findViewById(R.id.switchControl);
        switchControl.setChecked(preferences.getBoolean(getString(R.string.setting_switch), false));
        switchControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switchControl.isChecked()){
                    editor.putBoolean( mContext.getString(R.string.setting_switch), true);
                    editor.apply();
                    PackageManager p = mContext.getPackageManager();
                    ComponentName componentName = new ComponentName( mContext, MainActivity.class);
                    p.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                } else {
                    editor.putBoolean( mContext.getString(R.string.setting_switch), false);
                    editor.apply();
                }
            }
        });
    }
}
