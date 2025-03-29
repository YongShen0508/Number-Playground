package com.game.app.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import android.content.*;
import com.game.app.R;

import com.game.app.game.GameManager;

public class GameDifficultySelection extends AppCompatActivity {
    private Button easyButton;
    private Button moderateButton;
    private Button hardButton;
    private ImageButton backSelectionButton;
    private GameManager gameManager = GameManager.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_difficulty_selection);
        getWindow().setBackgroundDrawableResource(R.drawable.background_image);

        easyButton = findViewById(R.id.easyMode);
        moderateButton = findViewById(R.id.moderateMode);
        hardButton = findViewById(R.id.hardMode);
        backSelectionButton = findViewById(R.id.backToSelection);

        easyButton.setOnClickListener(v ->{
                gameManager.setGameDifficulty(0);
                gameStarted();
       });
        moderateButton.setOnClickListener(v -> {
                gameManager.setGameDifficulty(1);
                gameStarted();
        });
        hardButton.setOnClickListener(v -> {
                gameManager.setGameDifficulty(2);
                gameStarted();
        });
        backSelectionButton.setOnClickListener(v->{
                Intent intent = new Intent(GameDifficultySelection.this, GameSelection.class);
                startActivity(intent);
        });
    }

    private void gameStarted(){
        String[] gameType = gameManager.getAllGameType();
        gameManager.startGame();

        if(gameManager.getGameType().equals(gameType[0]))
        {
            Intent intent = new Intent(GameDifficultySelection.this, NumberClash.class);
            startActivity(intent);
        }

        else if(gameManager.getGameType().equals(gameType[1]))
        {
            Log.d("", gameManager.getGameType() + "  " + gameType[1]);
            Intent intent = new Intent(GameDifficultySelection.this, NumberLadder.class);
            startActivity(intent);
        }

        else if(gameManager.getGameType().equals(gameType[2]))
        {
            Intent intent = new Intent(GameDifficultySelection.this, NumberBuilder.class);
            startActivity(intent);
        }

        else if(gameManager.getGameType().equals(gameType[3]))
        {
            Intent intent = new Intent(GameDifficultySelection.this, MathsMaster.class);
            startActivity(intent);
        }
    }
}