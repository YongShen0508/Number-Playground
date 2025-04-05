package com.game.app.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.game.app.R;
import com.game.app.game.GameManager;
import com.game.app.game.GameType;
import com.game.app.util.ButtonAnimation;
import com.game.app.util.Localization;

public class GameDashboard extends BaseActivity {

    private GameManager gameManager = GameManager.getInstance();
    private Button continueButton;
    private TextView gameText, difficultyText,pointText,msgText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_dashboard);

        continueButton = findViewById(R.id.btnContinue);
        gameText = findViewById(R.id.gameTitle);
        pointText = findViewById(R.id.pointsTxt);
        difficultyText = findViewById(R.id.difficultyTxt);
        msgText = findViewById(R.id.messageText);
        gameText.setText(" [ " + gameManager.getLocalizedGameTypeName(gameManager.getAllGameType().indexOf(gameManager.getGameType())) + " ]");
        difficultyText.setText(getString(R.string.difficulty) + " " + gameManager.getLocalizedGameDiffName(gameManager.getAllGameDifficulty().indexOf(gameManager.getGameDifficulty())));
        pointText.setText(getString(R.string.score) + " >> " + gameManager.getPoint());
        if(gameManager.getPoint()==10){
            msgText.setText(getString(R.string.you_win));
            msgText.setTextColor(Color.parseColor("#27AE60"));
        }
        else {
            msgText.setText(getString(R.string.good_luck_next_time));
            msgText.setTextColor(Color.parseColor("#FF0000"));
        }
        continueButton.setOnClickListener(v->{
            ButtonAnimation.playButtonPress(this, v);
            backToMenu();
            gameManager.endGame();
        });
    }
    public void backToMenu(){

        Intent intent = new Intent(GameDashboard.this, GameSelection.class);
        startActivity(intent);
        finish();
    }
}