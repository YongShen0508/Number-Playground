/*
    gameDifficulty will affect the number of boxes
    easy - 3    medium - 4  hard - 5
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
import android.os.CountDownTimer;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class NumberLadder extends AppCompatActivity implements GameTimer.TimerListener {

    private final List<TextView> boxes = new ArrayList<>();
    private Button checkButton;

    private String[] sentenceData;

    private TextView scoreText;
    private TextView roundText;
    private ImageButton leaveCurrentGameButton;
    private TextView timeLeftText;

    private TextView questionText;
    private int displayNumber;
    private int sentenceIndex;
    private GameTimer gameTimer;
    private GameManager gameManager = GameManager.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_ladder);
        getWindow().setBackgroundDrawableResource(R.drawable.background_image);

        scoreText = findViewById(R.id.scores);
        roundText = findViewById(R.id.rounds);
        timeLeftText = findViewById(R.id.times);
        leaveCurrentGameButton = findViewById(R.id.pauseButton);
        checkButton = findViewById(R.id.submitButton);
        questionText = findViewById(R.id.questionsInfo);

        sentenceData = new String[]{
                getString(R.string.increasingOrder),
                getString(R.string.decreasingOrder)
        };
        Random random = new Random();
        sentenceIndex = random.nextInt(sentenceData.length);
        questionText.setText(getString(R.string.arrange) + " " + sentenceData[sentenceIndex]);

        int[] boxIds = {R.id.firstBox, R.id.secondBox, R.id.thirdBox, R.id.fourthBox, R.id.fifthBox,R.id.sixthBox};

        String[] allGameDifficulty = gameManager.getAllGameDifficulty();
        String gameDifficulty = gameManager.getGameDifficulty();
        int[] boxAndNumDisplay = new int[]{4,5,6};
        displayNumber = boxAndNumDisplay[Arrays.asList(allGameDifficulty).indexOf(gameDifficulty)];

        for(int i =0;i<6;i++)
        {
            boxes.add(findViewById(boxIds[i]));
            boxes.get(i).setVisibility(View.GONE);
            if(i < displayNumber){
                boxes.get(i).setVisibility(View.VISIBLE);
                enableDrop(boxes.get(i));
                enableDrag(boxes.get(i));
            }
        }

        setQuestion();
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
            ViewCompat.startDragAndDrop(v, data, shadowBuilder, v, 0);
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
            TextView targetBox = (TextView) v;

            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    return true;
                case DragEvent.ACTION_DROP:
                    // Get dragged number
                    ClipData.Item item = event.getClipData().getItemAt(0);
                    String draggedNumber = item.getText().toString();

                    // Find source box
                    View sourceView = (View) event.getLocalState();
                    if (sourceView instanceof TextView) {
                        swapText((TextView) sourceView, targetBox, draggedNumber);
                    }

                    return true;

                default:
                    return false;
            }
        });
    }

    // Swap the text between source and target only if valid
    private void swapText(TextView sourceBox, TextView targetBox, String draggedNumber) {
        String targetNumber = targetBox.getText().toString();

        if (!draggedNumber.isEmpty() && !targetNumber.isEmpty()) {
            sourceBox.setText(targetNumber);
            targetBox.setText(draggedNumber);
        }
    }


    private void setQuestion(){
        Set<Integer> uniqueNumbers = new HashSet<>();
        Random random = new Random();
        int maxNumber = gameManager.getNumberRange();
        if (maxNumber == -1) {
            maxNumber = 99;
        }

        for (int i = 0; i < displayNumber; i++) {
            boxes.get(i).setVisibility(View.VISIBLE);

            int randomNum;
            do {
                randomNum = random.nextInt(maxNumber) + 1;
            } while (uniqueNumbers.contains(randomNum));

            uniqueNumbers.add(randomNum);
            boxes.get(i).setText(String.valueOf(randomNum));
        }
    }

    private void submitAnswer(){
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.activity_display_answer_dialog, null);

        TextView dialogTitle = dialogView.findViewById(R.id.textAnswerTitle);
        TextView dialogInfo = dialogView.findViewById(R.id.textAnswerInfo);
        Button btnContinue = dialogView.findViewById(R.id.continueButton);

        List<Integer> userNumbers = new ArrayList<>();

        // Retrieve numbers from boxes
        for (TextView box : boxes) {
            if (box.getVisibility() == View.VISIBLE) {
                String text = box.getText().toString();
                if (!text.isEmpty()) {
                    userNumbers.add(Integer.parseInt(text));
                }
            }
        }

        // Example condition: Check if numbers are unique and sorted in ascending order
        boolean isIncreasing = true, isDecreasing = true;
        for (int i = 1; i < userNumbers.size(); i++) {
            if (userNumbers.get(i) < userNumbers.get(i - 1) && sentenceIndex == 0) {
                isIncreasing = false;
                break;
            }
            if (userNumbers.get(i) > userNumbers.get(i - 1) && sentenceIndex == 1) {
                isDecreasing = false;
                break;
            }
        }

        if(isIncreasing == false){
            Collections.sort(userNumbers); // Sort in increasing order
        }
        else if(isDecreasing == false){
            userNumbers.sort(Collections.reverseOrder());
        }

        if(isIncreasing && isDecreasing){
            dialogTitle.setText(getString(R.string.congratulation));
            dialogInfo.setText(getString(R.string.ansCorrect));
        }
        else {
            StringBuilder infoText = new StringBuilder();

            for (int num : userNumbers) {
                infoText.append(num).append(" ");
            }

            String finalInfoText = infoText.toString().trim();
            dialogTitle.setText(getString(R.string.answerTitle));
            dialogInfo.setText(finalInfoText);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        builder.setCancelable(false);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        btnContinue.setOnClickListener(v -> {
            gameManager.addGameRound();
            Intent intent;
            if(gameManager.getGameRounds() == 10)
            {
                intent = new Intent(NumberLadder.this, GameDashboard.class);
            }
            else if(gameManager.getGameType().equals(gameManager.getAllGameType()[3])){
                intent = new Intent(NumberLadder.this, MathsMaster.class);
            }
            else {
                intent = new Intent(NumberLadder.this, NumberLadder.class);
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

    @Override
    public void onTick(long millisUntilFinished) {
        timeLeftText.setText(getString(R.string.timeLeft) + " : " + millisUntilFinished / 1000 + "s");
    }

    @Override
    public void onFinish() {
        submitAnswer();
    }
}