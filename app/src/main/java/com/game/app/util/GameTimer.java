package com.game.app.util;

import android.os.CountDownTimer;

public class GameTimer {
    private CountDownTimer countDownTimer;
    private TimerListener listener;
    private long timeLeftInMillis;

    public interface TimerListener {
        void onTick(long millisUntilFinished);
        void onFinish();
    }

    public GameTimer(long startTime, TimerListener listener) {
        this.timeLeftInMillis = startTime;
        this.listener = listener;
    }

    public void start() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                if (listener != null) listener.onTick(millisUntilFinished);
            }

            public void onFinish() {
                if (listener != null) listener.onFinish();
            }
        }.start();
    }

    public void pause() {
        if (countDownTimer != null) countDownTimer.cancel();
    }

    public void resume() {
        start();  // Just restart with remaining time
    }
}

