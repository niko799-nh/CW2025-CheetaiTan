package com.comp2042.controller;
/**
 *This small class helps us count the progress.
 * It tracks how many lines the player cleared and decides the Level.
 * Higher level means harder game!
 */
public class LevelTracker {

    private int linesCleared = 0;
    private int level = 1;

    public void reset() {
        linesCleared = 0;
        level = 1;
    }
    /**
     * Add the number of lines cleared to the total.
     * Also checks if we need to level up.
     * The rule here is: Every 8 lines level up.
     * @param count How many lines were cleared just now (1, 2, 3, or 4).
     */
    public void addLines(int count) {
        linesCleared += count;
        int newLevel = (linesCleared / 8) + 1;
        if (newLevel > level) {
            level = newLevel;
        }
    }

    public int getLevel() {
        return level;
    }

    public int getLinesCleared() {
        return linesCleared;
    }
}