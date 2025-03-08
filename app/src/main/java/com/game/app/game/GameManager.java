package com.game.app.game;

public class GameManager {
    private GameType gameType = new GameType();
    private GameDifficulty gameDifficulty = new GameDifficulty();
    private int gamePoints;
    private int gameHeart;
    private int gameRounds;
    private Boolean isActive;
    private static GameManager instance;

    public GameManager() {
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

    public String getGameType()
    {
        return gameType.getGameType();
    }

    public String getGameDifficulty()
    {
        return gameDifficulty.getGameDifficulty();
    }

    public String[] getAllGameType()
    {
        return gameType.getAllGameType();
    }
    public String[] getAllGameDifficulty()
    {
        return gameDifficulty.getAllGameDifficulty();
    }

    public void startGame()
    {
        isActive = true;
    }
    public void endGame()
    {
        isActive = false;
    }
    public void addPoint()
    {
        gamePoints++;
    }
    public int getPoint(){
        return gamePoints;
    }

    public void deductHeart(){
        gameHeart--;
    }
    public void setGameHeart(int gameHeart){
        this.gameHeart = gameHeart;
    }
    public int getGameHeart()
    {
        return gameHeart;
    }
    public void addGameRound(){
        gameRounds++;
    }

    public int getGameRounds()
    {
        return gameRounds;
    }

}
