package com.game.app.gui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.game.app.R;
import com.game.app.game.GameManager;
import com.game.app.util.Localization;
import com.game.app.util.MusicServices;

import java.util.Arrays;

public class BaseActivity extends AppCompatActivity {
    private boolean isMovingToAnotherActivity = false;
    private GameManager gameManager = GameManager.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameManager.setAllGameTypeLocalizedName(Arrays.asList(getString(R.string.firstSelection),getString(R.string.secondSelection),getString(R.string.thirdSelection),getString(R.string.fourthSelection)));
        gameManager.setAllGameDiffLocalizedName(Arrays.asList(getString(R.string.easyLevel),getString(R.string.moderateLevel),getString(R.string.hardLevel)));
        getWindow().setBackgroundDrawableResource(R.drawable.background_image);
        Localization.setLocale(this,Localization.getSavedLanguage(this));



    }
        @Override
    protected void onPause() {
        super.onPause();
        if (!isMovingToAnotherActivity) { // Only pause if going to home screen
            Intent intent = new Intent(this, MusicServices.class);
            intent.putExtra("ACTION", "PAUSE");
            startService(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(this, MusicServices.class);
        intent.putExtra("ACTION", "RESUME");
        startService(intent);
    }

    @Override
    public void onBackPressed() {
        if (isTaskRoot()) { // Check if this is the last activity
            stopService(new Intent(this, MusicServices.class));
            finishAffinity(); // Close all activities
        } else {
            isMovingToAnotherActivity = true; // Prevent pausing when switching activities
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
