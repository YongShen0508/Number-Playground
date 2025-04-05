package com.game.app.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import android.content.*;
import com.game.app.R;

import com.game.app.game.GameManager;
import com.game.app.util.ButtonAnimation;
import com.game.app.util.MusicServices;

import java.util.ArrayList;
import java.util.List;

public class GameDifficultySelection extends BaseActivity {
    private Button easyButton;
    private Button moderateButton;
    private Button hardButton;
    private ImageButton backSelectionButton;
    private GameManager gameManager = GameManager.getInstance();
    private Intent serviceIntent;
    private AudioManager audioManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_difficulty_selection);

        easyButton = findViewById(R.id.easyMode);
        moderateButton = findViewById(R.id.moderateMode);
        hardButton = findViewById(R.id.hardMode);
        backSelectionButton = findViewById(R.id.backToSelection);

        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        easyButton.setOnClickListener(v ->{
            ButtonAnimation.playButtonPress(this, v);
            gameManager.setGameDifficulty(0);
            gameStarted();
        });
        moderateButton.setOnClickListener(v -> {
            ButtonAnimation.playButtonPress(this, v);
            gameManager.setGameDifficulty(1);
            gameStarted();
        });
        hardButton.setOnClickListener(v -> {
            ButtonAnimation.playButtonPress(this, v);
            gameManager.setGameDifficulty(2);
            gameStarted();
        });
        backSelectionButton.setOnClickListener(v->{
            ButtonAnimation.playButtonPress(this, v);
            Intent intent = new Intent(GameDifficultySelection.this, GameSelection.class);
            startActivity(intent);
        });
    }

    private void gameStarted(){
        List<String> gameType =gameManager.getAllGameType();
        gameManager.startGame();

        if(gameManager.getGameType().equals(gameType.get(0)))
        {
            Intent intent = new Intent(GameDifficultySelection.this, NumberClash.class);
            startActivity(intent);
        }

        else if(gameManager.getGameType().equals(gameType.get(1)))
        {
            Intent intent = new Intent(GameDifficultySelection.this, NumberLadder.class);
            startActivity(intent);
        }

        else if(gameManager.getGameType().equals(gameType.get(2)))
        {
            Intent intent = new Intent(GameDifficultySelection.this, NumberBuilder.class);
            startActivity(intent);
        }

        else if(gameManager.getGameType().equals(gameType.get(3)))
        {
            Intent intent = new Intent(GameDifficultySelection.this, MathsMaster.class);
            startActivity(intent);
        }
        finish();
    }
}