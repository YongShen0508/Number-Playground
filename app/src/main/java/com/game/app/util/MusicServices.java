package com.game.app.util;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import com.game.app.R;

public class MusicServices extends Service {
    private static MediaPlayer mediaPlayer;
    private static boolean isPausedByUser = false; // Track if music was manually paused
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "SettingsPrefs";
    private static final String SOUND_STATE_KEY = "soundState";
    @Override
    public void onCreate() {
        super.onCreate();
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.background_music);
            mediaPlayer.setLooping(true);
            mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build());
        }
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isMusicEnabled = sharedPreferences.getBoolean(SOUND_STATE_KEY, true);
        if (!isMusicEnabled) {
            stopMusic();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null) {
            String action = intent.getStringExtra("ACTION");

            if ("PAUSE".equals(action)) {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    isPausedByUser = true; // Mark that music was paused manually
                }
            } else if ("RESUME".equals(action)) {
                if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    isPausedByUser = false;
                }
            } else if ("STOP".equals(action)) {
                stopMusic();
            } else {
                if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                }
            }
        }
        return START_STICKY;
    }

    private void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopMusic();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
