package com.game.app.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.game.app.R;
import com.game.app.util.Localization;

public class GameSettings extends AppCompatActivity {

    private ImageButton btnClose;
    private Button btnSaveSettings;
    private Spinner languageSpinner = null;
    private String[] languageCodes;
    private String selectedLanguageCode;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_settings);
        getWindow().setBackgroundDrawableResource(R.drawable.gradient_background);

        btnClose = findViewById(R.id.closeButton);
        btnSaveSettings = findViewById(R.id.saveSettings);
        languageSpinner = findViewById(R.id.languageSpinner);


        Animation buttonPressAnim = AnimationUtils.loadAnimation(this, R.anim.button_pressed);
        btnClose.setOnClickListener(v-> {
            v.startAnimation(buttonPressAnim);
            Intent intent = new Intent(GameSettings.this, MainActivity.class);
            startActivity(intent);
        });

        btnSaveSettings.setOnClickListener(v ->{
            saveSettings();
            v.startAnimation(buttonPressAnim);
            Intent intent = new Intent(GameSettings.this, MainActivity.class);
            startActivity(intent);
        });


        // Get language names & codes from resources
        String[] languageNames = getResources().getStringArray(R.array.language_array);
        String[] languageCodes = getResources().getStringArray(R.array.language_codes);

        // Setup Spinner Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, languageNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(adapter);

        // Retrieve saved language from SharedPreferences using Localization class
        String savedLanguage = Localization.getSavedLanguage(this);
        int position = Localization.getLanguagePosition(languageCodes, savedLanguage);
        languageSpinner.setSelection(position);

        // Store the currently selected language without applying it immediately
        selectedLanguageCode = savedLanguage;
        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedLanguageCode = languageCodes[position]; // Store selection but do not apply it yet
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

    }

    private void saveSettings() {
        Localization.setLocale(this, selectedLanguageCode); // Apply new language setting
        recreate(); // Restart the activity to apply language changes
    }
}