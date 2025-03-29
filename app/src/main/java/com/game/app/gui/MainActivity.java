package com.game.app.gui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.game.app.R;
import com.game.app.util.Localization;
import com.game.app.util.MusicServices;


public class MainActivity extends AppCompatActivity {
    private Button startButton;
    private Button settingsButton;
    private Button quitButton;
    private Intent serviceIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        getWindow().setBackgroundDrawableResource(R.drawable.background_image);

        // Start the MusicService
        serviceIntent = new Intent(this, MusicServices.class);
        //startService(serviceIntent);

        // Initialize buttons
        startButton = findViewById(R.id.startGameButton);
        settingsButton = findViewById(R.id.gameSettingsButton);
        quitButton = findViewById(R.id.quitGameButton);
        Animation buttonPressAnim = AnimationUtils.loadAnimation(this, R.anim.button_pressed);

        // Set button actions
        startButton.setOnClickListener(v -> {
            v.startAnimation(buttonPressAnim);

            Intent intent = new Intent(MainActivity.this, GameSelection.class);
            startActivity(intent);
        });

        settingsButton.setOnClickListener(v -> {
            v.startAnimation(buttonPressAnim);

            Intent intent = new Intent(MainActivity.this, GameSettings.class);
            startActivity(intent);
        });

        quitButton.setOnClickListener(v -> {
            v.startAnimation(buttonPressAnim);
            showQuitConfirmation();
        } );
    }


    private void showQuitConfirmation() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.activity_exit_dialog, null);

        Button btnCancel = dialogView.findViewById(R.id.btnCancel);
        Button btnExit = dialogView.findViewById(R.id.btnExit);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);  // Assign the custom XML layout
        builder.setCancelable(false); // Prevent dismissing by tapping outside

        AlertDialog alertDialog = builder.create();
        alertDialog.show();  // Show the dialog

        btnExit.setOnClickListener(v -> {
            if (serviceIntent != null) {
                stopService(serviceIntent); // Stop service safely
            }
            alertDialog.dismiss(); // Dismiss dialog to prevent memory leaks

            finishAffinity();

        });

        btnCancel.setOnClickListener(v -> alertDialog.dismiss()); // Close the dialog

    }
}