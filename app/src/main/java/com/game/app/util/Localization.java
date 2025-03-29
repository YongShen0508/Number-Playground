package com.game.app.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class Localization {

    private static final String PREFS_NAME = "AppPrefs";
    private static final String LANGUAGE_KEY = "LANGUAGE";
    public static void setLocale(Context context, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        context.getResources().updateConfiguration(config, resources.getDisplayMetrics());

        // Save selected language
        saveLanguage(context, languageCode);
    }

    // Save selected language
    public static void saveLanguage(Context context, String languageCode) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(LANGUAGE_KEY, languageCode).apply();
    }

    // Get saved language (default to English)
    public static String getSavedLanguage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(LANGUAGE_KEY, "en");
    }

    // Get language position in the spinner
    public static int getLanguagePosition(String[] languageCodes, String languageCode) {
        for (int i = 0; i < languageCodes.length; i++) {
            if (languageCodes[i].equals(languageCode)) {
                return i;
            }
        }
        return 0; // Default to English if not found
    }
}
