package com.game.app.gui;

import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.game.app.R;
import com.game.app.util.ButtonAnimation;
import com.game.app.util.Localization;
import com.game.app.util.MusicServices;

public class GameSettings extends BaseActivity {

    private ImageButton btnClose;
    private Button btnSaveSettings;
    private Spinner languageSpinner = null;
    private String[] languageCodes;
    private String selectedLanguageCode;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "SettingsPrefs";
    private static final String SOUND_STATE_KEY = "soundState";

    private SwitchCompat soundSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_settings);

        btnClose = findViewById(R.id.closeButton);
        btnSaveSettings = findViewById(R.id.saveSettings);
        languageSpinner = findViewById(R.id.languageSpinner);
        soundSwitch = findViewById(R.id.soundSwitchButton);

        btnClose.setOnClickListener(v-> {
            ButtonAnimation.playButtonPress(this, v);
            Intent intent = new Intent(GameSettings.this, MainActivity.class);
            startActivity(intent);
        });

        btnSaveSettings.setOnClickListener(v ->{
            ButtonAnimation.playButtonPress(this, v);
            saveSettings();
            Intent intent = new Intent(GameSettings.this, MainActivity.class);
            startActivity(intent);
        });

        // Get SharedPreferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        boolean isChecked = sharedPreferences.getBoolean(SOUND_STATE_KEY, true);
        soundSwitch.setChecked(isChecked);

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
        Localization.setLocale(this, selectedLanguageCode); // Apply new language setting\

        boolean isSoundOn = soundSwitch.isChecked();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SOUND_STATE_KEY, isSoundOn);
        editor.apply();

        // Control music service based on switch state
        if (isSoundOn) {
            // Send resume action to the MusicService
            Intent intent = new Intent(GameSettings.this, MusicServices.class);
            intent.putExtra("ACTION", "RESUME");
            startService(intent);
        } else {
            // Send pause action to the MusicService
            Intent intent = new Intent(GameSettings.this, MusicServices.class);
            intent.putExtra("ACTION", "PAUSE");
            startService(intent);
        }

        recreate(); // Restart the activity to apply language changes
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Stop service when GameSettings is destroyed
        Intent intent = new Intent(GameSettings.this, MusicServices.class);
        intent.putExtra("ACTION", "STOP");
        startService(intent);
    }
}