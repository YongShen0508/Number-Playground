package com.game.app.gui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.game.app.R;
import com.game.app.game.GameManager;
import com.game.app.game.GameType;
import com.game.app.util.ButtonAnimation;
import com.game.app.util.Localization;
import com.game.app.util.MessageDialog;
import com.game.app.util.MusicServices;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends BaseActivity{
    private Button startButton;
    private Button settingsButton;
    private Button quitButton;
    private Intent serviceIntent;
    private GameManager gameManager = GameManager.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


        serviceIntent = new Intent(this, MusicServices.class);
        startService(serviceIntent);

        // Initialize buttons
        startButton = findViewById(R.id.startGameButton);
        settingsButton = findViewById(R.id.gameSettingsButton);
        quitButton = findViewById(R.id.quitGameButton);
        // Set button actions
        startButton.setOnClickListener(v -> {
            ButtonAnimation.playButtonPress(this, v);
            Intent intent = new Intent(MainActivity.this, GameSelection.class);
            startActivity(intent);
        });

        settingsButton.setOnClickListener(v -> {
            ButtonAnimation.playButtonPress(this, v);
            Intent intent = new Intent(MainActivity.this, GameSettings.class);
            startActivity(intent);
        });

        quitButton.setOnClickListener(v -> {
            ButtonAnimation.playButtonPress(this, v);
            showQuitConfirmation();
        } );
    }
    private void showQuitConfirmation() {
        MessageDialog.creatLeaveGameDialog(this, getString(R.string.exitGameTitle),getString(R.string.exitGameInfo), (Boolean isExit) -> {
            if (isExit) {
                if (serviceIntent != null) {
                    finishAffinity();
                }
            }});

    }
}