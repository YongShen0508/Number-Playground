package com.game.app.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.game.app.R;
import com.game.app.game.GameManager;

public class GameDashboard extends AppCompatActivity {

    private GameManager gameManager = GameManager.getInstance();
    private Button continueButton;
    private TextView gameText, difficultyText,pointText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_dashboard);
        getWindow().setBackgroundDrawableResource(R.drawable.background_image);

        continueButton = findViewById(R.id.btnContinue);
        gameText = findViewById(R.id.gameTitle);
        pointText = findViewById(R.id.pointsTxt);
        difficultyText = findViewById(R.id.difficultyTxt);
        gameText.setText(getString(R.string.game) + " " + gameManager.getGameType());
        pointText.setText(getString(R.string.score) + " " + gameManager.getPoint());
        difficultyText.setText(getString(R.string.difficulty) + " " + gameManager.getGameDifficulty());
        continueButton.setOnClickListener(v->{
            backToMenu();
            gameManager.endGame();
        });
    }
    public void backToMenu(){
        Intent intent = new Intent(GameDashboard.this, GameSelection.class);
        startActivity(intent);
    }
}