package com.game.app.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.*;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;

import com.game.app.R;
import com.game.app.game.GameManager;
import com.game.app.util.ButtonAnimation;
import com.game.app.util.MusicServices;

public class GameSelection extends BaseActivity {
    private Button numberClash;
    private Button numberLadder;
    private Button numberBuilder;
    private Button mathsMaster;
    private ImageButton backMenuButton;
    private GameManager gameManager = GameManager.getInstance();
    private Intent serviceIntent;
    private AudioManager audioManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_selection);

        numberClash = findViewById(R.id.firstSelection);
        numberLadder = findViewById(R.id.secondSelection);
        numberBuilder = findViewById(R.id.thirdSelection);
        mathsMaster = findViewById(R.id.fourthSelection);
        backMenuButton = findViewById(R.id.backToMenu);

        numberClash.setOnClickListener(v -> {
            ButtonAnimation.playButtonPress(this, v);
            gameManager.setGameType(0);
            Intent intent = new Intent(GameSelection.this, GameDifficultySelection.class);
            startActivity(intent);
        });

        numberLadder.setOnClickListener(v -> {
            ButtonAnimation.playButtonPress(this, v);
            gameManager.setGameType(1);
            Intent intent = new Intent(GameSelection.this, GameDifficultySelection.class);
            startActivity(intent);
        });

        numberBuilder.setOnClickListener(v -> {
            ButtonAnimation.playButtonPress(this, v);
            gameManager.setGameType(2);
            Intent intent = new Intent(GameSelection.this, GameDifficultySelection.class);
            startActivity(intent);
        });

        mathsMaster.setOnClickListener(v -> {
            ButtonAnimation.playButtonPress(this, v);
            gameManager.setGameType(3);
            Intent intent = new Intent(GameSelection.this, GameDifficultySelection.class);
            startActivity(intent);
        });

        backMenuButton.setOnClickListener(v -> {
            ButtonAnimation.playButtonPress(this, v);
            Intent intent = new Intent(GameSelection.this, MainActivity.class);
            startActivity(intent);
        });
    }
}