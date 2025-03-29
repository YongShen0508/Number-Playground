package com.game.app.game;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    private GameType gameType = new GameType();
    private GameDifficulty gameDifficulty = new GameDifficulty();
    private int gamePoints;
    private int gameRounds;
    private boolean isActive;
    private static GameManager instance;

    private List<Integer> gameTypeDifficultyRule = new ArrayList<>();
    private GameManager() {
        this.isActive = false;
        this.gamePoints = 0;
        this.gameRounds = 0;
    }
    public static GameManager getInstance()
    {
        if(instance == null)
        {
            instance = new GameManager();
        }
        return instance;
    }

    public void setGameType(int gameNumber) {
        gameType.setGameType(gameNumber);
    }

    public void setGameDifficulty(int difficultyNumber) {
        gameDifficulty.setGameDifficulty(difficultyNumber);
    }
    public String getGameDifficulty()
    {
        return gameDifficulty.getGameDifficulty();
    }

    public String getGameType()
    {
        return gameType.getGameType();
    }


    public String[] getAllGameType()
    {
        return GameType.getAllGameType();
    }
    public String[] getAllGameDifficulty()
    {
        return GameDifficulty.getAllGameDifficulty();
    }

    public void startGame()
    {
        isActive = true;
        gameRounds = 1;
        gamePoints = 0;
        gameTypeDifficultyRule = DifficultyRules.getDifficultyRules(gameDifficulty.getGameDifficulty());
    }
    public void endGame()
    {
        isActive = false;
        gameRounds = 0;
        gameType = new GameType();
        gameDifficulty = new GameDifficulty();
        gameTypeDifficultyRule.clear();
    }

    public void addPoint()
    {
        gamePoints++;
    }

    public int getPoint(){
        return gamePoints;
    }

    public void addGameRound(){
        gameRounds++;
    }

    public int getGameRounds()
    {
        return gameRounds;
    }

    public int getNumberRange(){
        if (gameTypeDifficultyRule.isEmpty())
                return -1;
        return gameTypeDifficultyRule.get(0);
    }
    public int getTimeLimit(){
        if (gameTypeDifficultyRule.isEmpty())
            return -1;
        return gameTypeDifficultyRule.get(1);
    }
    public int getGameTotalRounds(){
        if (gameTypeDifficultyRule.isEmpty())
            return -1;
        return gameTypeDifficultyRule.get(2);
    }


}
