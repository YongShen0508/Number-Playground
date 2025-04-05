package com.game.app.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameType {
    private static final List<String> gameType = new ArrayList<>(Arrays.asList("Number Clash", "Number Ladder", "Number Builder", "Maths Master"));
    private static final List<String> gameTypesLocalized = new ArrayList<>();
    private String gameName;

    public GameType()
    {

    }
    static List<String> getAllGameType()
    {
        return gameType;
    }


    public void setGameType(int gameNumber)
    {
        if(gameNumber < 4 && gameNumber >= 0 )
        {
            gameName = gameType.get(gameNumber);
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


    public void setLocalizedNames(List<String> localizedNames) {
        if (localizedNames != null && localizedNames.size() == gameType.size()) {
            gameTypesLocalized.clear();
            gameTypesLocalized.addAll(localizedNames);
        } else {
            throw new IllegalArgumentException("Localized names list must match the number of game types.");
        }
    }
    public String  getLocalizedNames(int gameNumber){
        return gameTypesLocalized.get(gameNumber);
    }

}
