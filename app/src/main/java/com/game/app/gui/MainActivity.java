package com.game.app.gui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.game.app.R;
import com.game.app.util.MusicServices;


public class MainActivity extends AppCompatActivity {

    private Button startButton;
    private Button settingsButton;
    private Button quitButton;
    private View splashScreen;
    private View mainTitle;
    private View mainContent;

    private Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Start the MusicService
        serviceIntent = new Intent(this, MusicServices.class);
        startService(serviceIntent);

        // Get references to views
        splashScreen = findViewById(R.id.splashScreen);
        mainTitle = findViewById(R.id.mainMenuTitle);
        mainContent = findViewById(R.id.mainMenuContents);

        splashScreen.setVisibility(View.VISIBLE);
        mainTitle.setVisibility(View.GONE);
        mainContent.setVisibility(View.GONE);

        // Delay 2 seconds, then show main content
        new Handler().postDelayed(() -> {
            splashScreen.setVisibility(View.GONE);
            mainTitle.setVisibility(View.VISIBLE);
            mainContent.setVisibility(View.VISIBLE);
        }, 2000);

        // Initialize buttons
        startButton = findViewById(R.id.startGameButton);
        settingsButton = findViewById(R.id.gameSettingsButton);
        quitButton = findViewById(R.id.quitGameButton);

        // Set button actions
        startButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GameSelection.class);
            startActivity(intent);
        });

        settingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GameSettings.class);
            startActivity(intent);
        });

        quitButton.setOnClickListener(v -> showQuitConfirmation());
    }


    private void showQuitConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Quit Game");
        builder.setMessage("Are you sure you want to exit?");

        builder.setPositiveButton("Yes", (dialog, which) -> {
            stopService(serviceIntent);
            finish(); // Close the app
        });
        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss()); // Close the dialog, stay in the app
        builder.show();
    }

}