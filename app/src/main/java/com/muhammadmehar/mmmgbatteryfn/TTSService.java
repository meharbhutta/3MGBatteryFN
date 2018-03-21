package com.muhammadmehar.mmmgbatteryfn;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

/**
 * Created by Muhammad Mehar on 3/21/2018.
 */
public class TTSService extends Service {

    private SharedPreferences preferences;
    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        playSound(preferences.getInt(getString(R.string.rad), -1),getBaseContext());
        return super.onStartCommand(intent, flags, startId);
    }

    private void playSound(int check, Context context) {
        if (check == R.id.radioButton1) {
            mediaPlayer = MediaPlayer.create(context, R.raw.audio1);
        } else if (check == R.id.radioButton2){
            mediaPlayer = MediaPlayer.create(context, R.raw.audio2);
        } else if (check == R.id.radioButton3){
            mediaPlayer = MediaPlayer.create(context, R.raw.audio3);
        } else {
            mediaPlayer = null;
        }
        if (mediaPlayer != null) {
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.release();
                    stopSelf();
                }
            });
            mediaPlayer.setVolume(0.9f,0.9f);
            mediaPlayer.start();
        }
    }

    @Override
    public void onDestroy() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
