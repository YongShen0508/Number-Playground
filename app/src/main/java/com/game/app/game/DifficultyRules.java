/*
    difficulties -> easy, medium, hard
    numberRange -> max numbers for particular difficulties
    timeLimit -> time limits for particular difficulties
    round -> 10 rounds for each mode and game

    getDifficultiesRules -> numberRange, timeLimit and round for each difficulty
 */

package com.game.app.game;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DifficultyRules {
    private final static List<Integer> numberRange = new ArrayList<>(Arrays.asList(20,60,99));
    private final static List<Integer> timeLimit  = new ArrayList<>(Arrays.asList(20,15,12));
    private final static int rounds = 10;
    public static List<Integer> getNumberRange(){
        return numberRange;
    }
    public static List<Integer> getTimeLimit(){
        return timeLimit;
    }
    public static int getRounds(){
        return rounds;
    }
    @Nullable
    public static List<Integer> getDifficultyRules(String difficulty){
        List<Integer> difficultyRules = new ArrayList<>();
        List<String> gameDifficulty = new ArrayList<>(GameDifficulty.getAllGameDifficulty());
        int index = gameDifficulty.indexOf(difficulty);
        if(index == -1)
        {
            return null;
        }
        difficultyRules.add(numberRange.get(index));
        difficultyRules.add(timeLimit.get(index));
        difficultyRules.add(rounds);
        return difficultyRules;
    }
}
