package com.game.app.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameDifficulty {
    private static final List<String> gameDifficulty = new ArrayList<>(Arrays.asList("easy","medium","hard"));
    private static final List<String> gameDifficultyLocalized = new ArrayList<>();

    private String difficulty;

    public GameDifficulty()
    {

    }
    static List<String> getAllGameDifficulty()
    {
        return gameDifficulty;
    }

    public void setGameDifficulty(int difficultyNumber)
    {
        if(difficultyNumber < 3 && difficultyNumber >= 0)
        {
            difficulty = gameDifficulty.get(difficultyNumber);
        }
        else
        {
            difficulty = null;
        }
    }

    public String getGameDifficulty()
    {
        return difficulty;
    }
    public void setLocalizedNames(List<String> localizedNames) {
        if (localizedNames != null && localizedNames.size() == gameDifficulty.size()) {
            gameDifficultyLocalized.clear();
            gameDifficultyLocalized.addAll(localizedNames);
        } else {
            throw new IllegalArgumentException("Localized names list must match the number of game types.");
        }
    }
    public String  getLocalizedNames(int gameNumber){
        return gameDifficultyLocalized.get(gameNumber);
    }

}
