package com.game.app.gui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.game.app.R;

import java.util.Random;

public class MathsMaster extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Ensure proper lifecycle handling
        setContentView(R.layout.activity_maths_master); // Ensure layout is set

        Random random = new Random();
        int index = random.nextInt(3) + 1; // Generates a number between 1 and 3

        Intent intent;
        if (index == 1) {
            intent = new Intent(this, NumberClash.class);
        } else if (index == 2) {
            intent = new Intent(this, NumberLadder.class);
        } else {
            intent = new Intent(this, NumberBuilder.class);
        }

        startActivity(intent);
    }
}