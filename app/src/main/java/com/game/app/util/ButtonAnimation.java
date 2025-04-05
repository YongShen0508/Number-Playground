package com.game.app.util;

import android.content.Context;
import android.media.AudioManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.game.app.R;

public class ButtonAnimation {
    public static void playButtonPress(Context context, View view) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        Animation buttonPressAnim = AnimationUtils.loadAnimation(context, R.anim.button_pressed);
        if (audioManager != null) {
            audioManager.playSoundEffect(AudioManager.FX_KEY_CLICK, 1.0f);
        }
        view.startAnimation(buttonPressAnim);
    }
}
