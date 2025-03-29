/*
 in this number clash, there will be random greater than or less than
 users are allow to drag and drop into the bracket.
 */
package com.game.app.gui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.game.app.R;
import com.game.app.game.GameManager;
import com.game.app.util.GameTimer;
import com.game.app.util.MessageDialog;

import java.util.Random;

public class NumberClash extends AppCompatActivity implements GameTimer.TimerListener {

    GameManager gameManager = GameManager.getInstance();
    private TextView box1, box2, num1, num2;
    private Button checkButton;
    private String xValue = "", yValue = "";

    private String[] sentenceData;
    private TextView sentence;

    private TextView scoreText;
    private TextView roundText;
    private ImageButton leaveCurrentGameButton,askButton;
    private TextView timeLeftText;
    private GameTimer gameTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_clash);
        getWindow().setBackgroundDrawableResource(R.drawable.background_image);

        box1 = findViewById(R.id.firstBox);
        box2 = findViewById(R.id.secondBox);
        num1 = findViewById(R.id.firstNumber);
        num2 = findViewById(R.id.secondNumber);
        checkButton = findViewById(R.id.submitButton);
        sentence = findViewById(R.id.sentenceText);
        askButton = findViewById(R.id.btnAsk);

        scoreText = findViewById(R.id.scores);
        roundText = findViewById(R.id.rounds);
        timeLeftText = findViewById(R.id.times);
        leaveCurrentGameButton = findViewById(R.id.pauseButton);

        sentenceData = new String[]{
                getString(R.string.greaterThan),
                getString(R.string.lessThan)
        };
        Random random = new Random();
        int randomIndex = random.nextInt(sentenceData.length);
        sentence.setText(sentenceData[randomIndex]);


        enableDrag(num1);
        enableDrag(num2);

        enableDrop(box1);
        enableDrop(box2);

        setQuestions();

        checkButton.setOnClickListener(v -> submitAnswer());

        scoreText.setText(getString(R.string.score) + " : " + gameManager.getPoint());
        roundText.setText(getString(R.string.round) +" : " + gameManager.getGameRounds() + "/" + gameManager.getGameTotalRounds());
        gameTimer = new GameTimer((long) gameManager.getTimeLimit() * 1000, this);
        gameTimer.start();

        leaveCurrentGameButton.setOnClickListener(v-> leaveCurrentGame());
    }

    @SuppressLint("ClickableViewAccessibility")
    private void enableDrag(TextView number) {
        number.setOnTouchListener(this::onNumberTouched);
        number.setOnClickListener(this::onNumberClicked);
    }

    private boolean onNumberTouched(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            TextView number = (TextView) v;
            ClipData data = ClipData.newPlainText("number", number.getText().toString());
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(number);
            ViewCompat.startDragAndDrop(v, data, shadowBuilder, null, 0);
            return true; // Dragging starts immediately
        }
        return false;
    }

    private void onNumberClicked(View v) {
        TextView number = (TextView) v;
        // Example click behavior: highlight the number
        number.setBackgroundColor(Color.LTGRAY);
    }

    private void enableDrop(TextView box) {
        box.setOnDragListener((v, event) -> {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    return true;
                case DragEvent.ACTION_DROP:
                    ClipData.Item item = event.getClipData().getItemAt(0);
                    String draggedNumber = item.getText().toString();
                    TextView view = (TextView) v;
                    view.setText(draggedNumber);
                    view.setBackgroundColor(Color.parseColor("#B3E5FC"));
                    view.setTextColor(Color.parseColor("#263238"));
                    // Save values
                    if (v.getId() == R.id.firstBox) {
                        xValue = draggedNumber;
                    } else if (v.getId() == R.id.secondBox) {
                        yValue = draggedNumber;
                    }
                    return true;
                default:
                    return false;
            }
        });
    }

    private void submitAnswer() {

        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.activity_display_answer_dialog, null);

        TextView dialogTitle = dialogView.findViewById(R.id.textAnswerTitle);
        TextView dialogInfo = dialogView.findViewById(R.id.textAnswerInfo);
        Button btnContinue = dialogView.findViewById(R.id.continueButton);

        int xNum, yNum;
        String infoText ="";
        boolean correctAnswer = false;

        if (xValue.equals(yValue)) {
            xNum = Integer.parseInt(num1.getText().toString());
            yNum = Integer.parseInt(num2.getText().toString());

        } else if (!xValue.isEmpty() && !yValue.isEmpty()) {
            xNum = Integer.parseInt(xValue);
            yNum = Integer.parseInt(yValue);
            if ((sentence.getText().equals(sentenceData[0]) && xNum > yNum) ||
                    (sentence.getText().equals(sentenceData[1]) && xNum < yNum)) {
                correctAnswer = true;
                gameManager.addPoint();
            }
        } else {
            xNum = Integer.parseInt(num1.getText().toString());
            yNum = Integer.parseInt(num2.getText().toString());
        }

        //error handling
        if (sentence.getText().equals(sentenceData[0])) { // "greater than"
            infoText = Math.max(xNum, yNum) + " " + sentenceData[0] + " " + Math.min(xNum, yNum);
        }
        else if (sentence.getText().equals(sentenceData[1])) { // "lesser than"
            infoText = Math.min(xNum, yNum) + " " + sentenceData[1] + " " + Math.max(xNum, yNum);
        }


        if(correctAnswer){
            dialogTitle.setText(getString(R.string.congratulation));
            dialogInfo.setText(getString(R.string.ansCorrect));
        }
        else {
            dialogTitle.setText(getString(R.string.answerTitle));
            dialogInfo.setText(infoText);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        builder.setCancelable(false);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        // Button Listener
        btnContinue.setOnClickListener(v -> {
            gameManager.addGameRound();
            Intent intent;
            if(gameManager.getGameRounds() == 10)
            {
                intent = new Intent(NumberClash.this, GameDashboard.class);
            }
            else if(gameManager.getGameType().equals(gameManager.getAllGameType()[3])){
                intent = new Intent(NumberClash.this, MathsMaster.class);
            }
            else {
                intent = new Intent(NumberClash.this, NumberClash.class);
            }
            startActivity(intent);
        });
    }
    private void leaveCurrentGame() {
        gameTimer.pause();

        MessageDialog.createDialog(this, getString(R.string.exitCurrentGameTitle),getString(R.string.exitCurrentGameInfo), (Boolean isExit) -> {
            if (isExit) {
                Intent intent = new Intent(this, GameSelection.class);
                startActivity(intent);
                finish();
            }
            else {
                gameTimer.resume();
            }});
    }

    private void setQuestions() {
        Random random = new Random();
        int maxNumber = gameManager.getNumberRange();
        if(maxNumber == -1){
            maxNumber = 99;
        }
        int randomNum1 = random.nextInt(maxNumber) + 1;
        int randomNum2;
        do {
            randomNum2 = random.nextInt(maxNumber) + 1;
        } while (randomNum2 == randomNum1);

        // Set the numbers to the TextViews
        num1.setText(String.valueOf(randomNum1));
        num2.setText(String.valueOf(randomNum2));


    }

    @Override
    public void onTick(long millisUntilFinished) {
        timeLeftText.setText(getString(R.string.timeLeft) + " : " + millisUntilFinished / 1000 + "s");
    }

    @Override
    public void onFinish() {
        submitAnswer();
    }
}