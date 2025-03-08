package com.game.app.game;

public class GameType {
    private final String[] gameType = {"Number Clash","Number Ladder","Number Builder","Maths master"};
    private String gameName;

    public GameType()
    {

    }
    String[] getAllGameType()
    {
        return gameType;
    }


    public void setGameType(int gameNumber)
    {
        if(gameNumber < 4 && gameNumber >= 0 )
        {
            gameName = gameType[gameNumber];
        }
        else
        {
            gameName = null;
        }
    }
    public String getGameType(){
        return gameName;
    }
    public Boolean checkGameType(int gameNumber)
    {
        return gameName != null;
    }

}
