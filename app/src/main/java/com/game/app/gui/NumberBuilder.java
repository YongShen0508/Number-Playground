package com.game.app.gui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.game.app.R;
import com.game.app.game.GameManager;
import com.game.app.util.GameTimer;
import com.game.app.util.MessageDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class NumberBuilder extends BaseActivity implements GameTimer.TimerListener {

    private final List<String> arithmeticWords = new ArrayList<>();
    private List<TextView> boxes = new ArrayList<>();
    private List<TextView> numbers = new ArrayList<>();
    private final int numDisplay[] = {4,5,6};
    GameManager gameManager = GameManager.getInstance();
    private Button checkButton;
    private TextView questionText;

    private TextView scoreText;
    private TextView roundText;
    private ImageButton leaveCurrentGameButton,askButton;
    private TextView timeLeftText;
    private GameTimer gameTimer;
    private int ansValue;
    private List<Integer> ansList = new ArrayList<>();
    private int displayNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_builder);

        arithmeticWords.addAll(Arrays.asList(
                getString(R.string.addition),
                getString(R.string.substraction),
                getString(R.string.multiplication),
                getString(R.string.division)
        ));
        scoreText = findViewById(R.id.scores);
        roundText = findViewById(R.id.rounds);
        timeLeftText = findViewById(R.id.times);
        leaveCurrentGameButton = findViewById(R.id.pauseButton);
        checkButton = findViewById(R.id.submitButton);
        questionText = findViewById(R.id.questionsInfo);
        askButton = findViewById(R.id.btnAsk);


        int[] boxIds = {R.id.firstBox, R.id.secondBox, R.id.thirdBox};
        int[] numIds = {R.id.firstNumber,R.id.secondNumber,R.id.thirdNumber,R.id.fourthNumber,R.id.fifthNumber,R.id.sixthNumber};
        List<String> allGameDifficulty = gameManager.getAllGameDifficulty();
        String gameDifficulty = gameManager.getGameDifficulty();
        displayNumber = numDisplay[allGameDifficulty.indexOf(gameDifficulty)];

        for(int i =0;i<6;i++)
        {
            if(i<3){
                boxes.add(findViewById(boxIds[i]));
                boxes.get(i).setVisibility(View.VISIBLE);
                if(i!=1){
                    enableDrop(boxes.get(i));
                }
            }

            numbers.add(findViewById(numIds[i]));
            numbers.get(i).setVisibility(View.GONE);

            if(i < displayNumber){
                numbers.get(i).setVisibility(View.VISIBLE);
                enableDrag(numbers.get(i));
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

    private void setQuestion(){
        Random random = new Random();
        int maxNumber = gameManager.getNumberRange();
        int firstNumber = random.nextInt(maxNumber / 2) + 1;
        int secondNumber = -1;
        int index = -1;
        boolean valid = false;

        while (!valid) {
            index = random.nextInt(arithmeticWords.size()); // Get a random arithmetic operation
            secondNumber = random.nextInt(maxNumber / 2) + 1;

            switch (index) {
                case 0: // Addition
                    if (firstNumber + secondNumber < maxNumber) {
                        ansValue = firstNumber + secondNumber;
                        valid = true;
                    }
                    break;
                case 1: // Subtraction
                    if (firstNumber - secondNumber > 0) {
                        ansValue = firstNumber - secondNumber;
                        valid = true;
                    }
                    break;
                case 2: // Multiplication
                    if (firstNumber * secondNumber < maxNumber) {
                        ansValue = firstNumber * secondNumber;
                        valid = true;
                    }
                    break;
                case 3: // Division
                    if (secondNumber != 0 && firstNumber % secondNumber == 0 && firstNumber / secondNumber < maxNumber) {
                        ansValue = firstNumber / secondNumber;
                        valid = true;
                    }
                    break;
            }
        }
        ansList.addAll(Arrays.asList(firstNumber,secondNumber));
        questionText.setText(getString(R.string.composedNumberText) + " " + ansValue);
        boxes.get(1).setText(arithmeticWords.get(index));

        List<Integer> numbersList = new ArrayList<>(Arrays.asList(firstNumber, secondNumber));
        while (numbersList.size() < displayNumber) {
            int randomNum = random.nextInt(maxNumber) + 1;
            if (!numbersList.contains(randomNum)) {
                numbersList.add(randomNum);
            }
        }

        Collections.shuffle(numbersList);
        for (int i = 0; i < displayNumber; i++) {
            numbers.get(i).setText(String.valueOf(numbersList.get(i)));
        }
    }
    private void submitAnswer(){
        if(gameTimer.isRunning())
            gameTimer.stop();


        int index = arithmeticWords.indexOf(boxes.get(1).getText().toString());
        int value = 0;  // Ensure initialization
        boolean correctAns = false;

        try {
            int num1 = Integer.parseInt(boxes.get(0).getText().toString());
            int num2 = Integer.parseInt(boxes.get(2).getText().toString());

            switch (index) {
                case 0:  // Addition
                    value = num1 + num2;
                    break;
                case 1:  // Subtraction
                    value = num1 - num2;
                    break;
                case 2:  // Multiplication
                    value = num1 * num2;
                    break;
                case 3:  // Division (Ensure num2 is not zero)
                    if (num2 != 0) {
                        value = num1 / num2;
                    } else {
                        value = -1;  // Invalid division case
                    }
                    break;
                default:
                    value = -1;  // Invalid index case
                    break;
            }

            correctAns = (ansValue == value);  // Check if answer matches

        } catch (NumberFormatException e) {
            correctAns = false;  // Handle invalid number input
        }

        String title,messages;
        if(correctAns){
            title = getString(R.string.congratulation);
            messages = getString(R.string.ansCorrect);
        }
        else {
            String infoText = ansList.get(0) + "  " +arithmeticWords.get(index) + "  " + ansList.get(1);
            title = getString(R.string.answerTitle);
            messages = infoText;
        }
        MessageDialog.createContinueGameDialog(this, title,messages, (Boolean continueGame) -> {
            if (continueGame) {
                gameManager.addGameRound();
                Intent intent;
                if(gameManager.getGameRounds() == 10 + 1)
                {
                    intent = new Intent(NumberBuilder.this, GameDashboard.class);
                }
                else if(gameManager.getGameType().equals(gameManager.getAllGameType().get(3))){
                    intent = new Intent(NumberBuilder.this, MathsMaster.class);
                }
                else {
                    intent = new Intent(NumberBuilder.this, NumberBuilder.class);

                }
                startActivity(intent);
                finish();
            }});
    }
    private void showInstruction(){
        String instructions = getString(R.string.instruction_builder);
        Toast.makeText(this, instructions, Toast.LENGTH_LONG).show();
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
            TextView targetBox = (TextView) v;

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

                    return true;

                default:
                    return false;
            }
        });
    }

    private void leaveCurrentGame() {
        gameTimer.pause();
        MessageDialog.creatLeaveGameDialog(this, getString(R.string.exitCurrentGameTitle),getString(R.string.exitCurrentGameInfo), (Boolean isExit) -> {
            if (isExit) {
                if(gameTimer.isRunning())
                    gameTimer.stop();
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