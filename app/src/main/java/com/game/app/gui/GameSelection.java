package com.game.app.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.*;
import android.os.Bundle;
import android.widget.*;

import com.game.app.R;
import com.game.app.game.GameManager;

public class GameSelection extends AppCompatActivity {
    private Button numberClash;
    private Button numberLadder;
    private Button numberBuilder;
    private Button mathsMaster;
    private ImageButton backMenuButton;
    private GameManager gameManager = GameManager.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_selection);
        getWindow().setBackgroundDrawableResource(R.drawable.background_image);

        numberClash = findViewById(R.id.firstSelection);
        numberLadder = findViewById(R.id.secondSelection);
        numberBuilder = findViewById(R.id.thirdSelection);
        mathsMaster = findViewById(R.id.fourthSelection);
        backMenuButton = findViewById(R.id.backToMenu);
        numberClash.setOnClickListener(v -> {
            gameManager.setGameType(0);
            Intent intent = new Intent(GameSelection.this, GameDifficultySelection.class);
            startActivity(intent);
        });

        numberLadder.setOnClickListener(v -> {
            gameManager.setGameType(1);
            Intent intent = new Intent(GameSelection.this, GameDifficultySelection.class);
            startActivity(intent);
        });

        numberBuilder.setOnClickListener(v -> {
            gameManager.setGameType(2);
            Intent intent = new Intent(GameSelection.this, GameDifficultySelection.class);
            startActivity(intent);
        });

        mathsMaster.setOnClickListener(v -> {
            gameManager.setGameType(3);
            Intent intent = new Intent(GameSelection.this, GameDifficultySelection.class);
            startActivity(intent);
        });

        backMenuButton.setOnClickListener(v -> {
            Intent intent = new Intent(GameSelection.this, MainActivity.class);
            startActivity(intent);
        });
    }
}