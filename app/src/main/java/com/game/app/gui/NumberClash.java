package com.game.app.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.game.app.R;
import com.game.app.game.GameManager;

public class NumberClash extends AppCompatActivity {

    GameManager gameManager = GameManager.getInstance();
    private TextView xBox, yBox, num1, num2;
    private Button checkButton;
    private String xValue = "", yValue = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        xBox = findViewById(R.id.xBox);
        yBox = findViewById(R.id.yBox);
        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        checkButton = findViewById(R.id.checkButton);

        // Enable drag for number options
        enableDrag(num1);
        enableDrag(num2);

        // Enable drop for X and Y boxes
        enableDrop(xBox);
        enableDrop(yBox);

        // Check Answer Button
        checkButton.setOnClickListener(v -> checkAnswer());
    }

    private void enableDrag(TextView number) {
        number.setOnLongClickListener(v -> {
            ClipData data = ClipData.newPlainText("number", number.getText().toString());
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(number);
            v.startDragAndDrop(data, shadowBuilder, v, 0);
            return true;
        });
    }

    private void enableDrop(TextView box) {
        box.setOnDragListener((v, event) -> {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    return true;

                case DragEvent.ACTION_DROP:
                    ClipData.Item item = event.getClipData().getItemAt(0);
                    String draggedNumber = item.getText().toString();
                    ((TextView) v).setText(draggedNumber);

                    // Save values
                    if (v.getId() == R.id.xBox) {
                        xValue = draggedNumber;
                    } else if (v.getId() == R.id.yBox) {
                        yValue = draggedNumber;
                    }

                    return true;

                default:
                    return false;
            }
        });
    }

    private void checkAnswer() {
        if (!xValue.isEmpty() && !yValue.isEmpty()) {
            int xNum = Integer.parseInt(xValue);
            int yNum = Integer.parseInt(yValue);

            if (xNum > yNum) {
                Toast.makeText(this, "Correct! " + xNum + " is greater than " + yNum, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Try Again! " + xNum + " is NOT greater than " + yNum, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Drag numbers to X and Y first!", Toast.LENGTH_SHORT).show();
        }
    }
}