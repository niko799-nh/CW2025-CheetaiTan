package com.comp2042.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * The Score class keeps track of the player's score during the game.
 * <p>
 * The score is stored as a JavaFX {@link IntegerProperty} so that the UI can automatically update whenever the score changes.
 * </p>
 */
public final class Score {

    /** The current score value that the game UI can observe. */
    private final IntegerProperty score = new SimpleIntegerProperty(0);

    /**
     * Gets the score property so it can be used for display or binding.
     * @return the IntegerProperty representing the current score
     */
    public IntegerProperty scoreProperty() {
        return score;
    }

    /**
     * Adds points to the current score.
     * @param i the number of points to add
     */
    public void add(int i){
        score.setValue(score.getValue() + i);
    }

    /**
     * Resets the score back to zero.
     * Used when starting a new game.
     */
    public void reset() {
        score.setValue(0);
    }
}
