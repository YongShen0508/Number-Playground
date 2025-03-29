package com.game.app.game;

public class GameDifficulty {
    private static final String[] gameDifficulty = {"easy","medium","hard"};
    private String difficulty;

    public GameDifficulty()
    {

    }
    static String[] getAllGameDifficulty()
    {
        return gameDifficulty;
    }

    public void setGameDifficulty(int difficultyNumber)
    {
        if(difficultyNumber < 3 && difficultyNumber >= 0)
        {
            difficulty = gameDifficulty[difficultyNumber];
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
}
