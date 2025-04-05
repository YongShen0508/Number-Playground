package com.game.app.util;

import android.os.CountDownTimer;

public class GameTimer {
    private CountDownTimer countDownTimer;
    private TimerListener listener;
    private long timeLeftInMillis;
    private boolean isRunning = false; // Track timer state

    public interface TimerListener {
        void onTick(long millisUntilFinished);
        void onFinish();
    }

    public GameTimer(long startTime, TimerListener listener) {
        this.timeLeftInMillis = startTime;
        this.listener = listener;
    }

    public void start() {
        if (isRunning) return; // Prevent restarting while running

        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                if (listener != null) listener.onTick(millisUntilFinished);
            }

            public void onFinish() {
                isRunning = false;
                if (listener != null) listener.onFinish();
            }
        }.start();
        isRunning = true;
    }

    public void pause() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            isRunning = false;
        }
    }

    public void resume() {
        if (!isRunning) {
            start();
        }
    }

    public void stop() { // Ensure complete stop
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
        isRunning = false;
    }

    public boolean isRunning() {
        return isRunning;
    }
}
