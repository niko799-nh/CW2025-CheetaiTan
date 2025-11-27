package com.comp2042.model;

import com.comp2042.engine.MatrixOperations;

/**
 * Stores the result after checking and clearing full rows on the board.
 * <p>
 * When the game removes one or more full rows, this class keeps, how many rows were cleared,and how the updated board looks like and
 * how many bonus points should be awarded.
 */
public final class ClearRow {

    /** Number of full rows that were removed. */
    private final int linesRemoved;

    /** The updated board matrix after clearing rows. */
    private final int[][] newMatrix;

    /** Bonus score gained from removing rows. */
    private final int scoreBonus;

    /**
     * Creates a new ClearRow result.
     * @param linesRemoved how many rows were cleared
     * @param newMatrix the updated board after removing rows
     * @param scoreBonus extra score earned from this clear
     */
    public ClearRow(int linesRemoved, int[][] newMatrix, int scoreBonus) {
        this.linesRemoved = linesRemoved;
        this.newMatrix = newMatrix;
        this.scoreBonus = scoreBonus;
    }

    /**
     * @return number of rows that were cleared
     */
    public int getLinesRemoved() {
        return linesRemoved;
    }

    /**
     * Returns a copy of the updated board matrix. This prevents modifying the original stored matrix.
     * @return a safe copy of the updated board
     */
    public int[][] getNewMatrix() {
        return MatrixOperations.copy(newMatrix);
    }

    /**
     * @return score bonus earned for this clear
     */
    public int getScoreBonus() {
        return scoreBonus;
    }
}
