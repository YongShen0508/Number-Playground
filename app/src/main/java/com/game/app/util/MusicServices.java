package com.game.app.util;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.game.app.R;

public class MusicServices extends Service {
    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize the MediaPlayer
        mediaPlayer = MediaPlayer.create(this, R.raw.background);
        mediaPlayer.setLooping(true); // Loop the music
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Start playing the music
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
        return START_STICKY; // Service will be restarted if it gets terminated
    }

    @Override
    public void onDestroy() {
        // Release the MediaPlayer when the service is destroyed
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null; // We don't need binding for this example
    }
}
