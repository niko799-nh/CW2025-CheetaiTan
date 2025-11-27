package com.comp2042.model;

import com.comp2042.engine.MatrixOperations;

/**
 * This class just keeps all the info needed to show the falling brick on screen lah.
 * It stores the brick shape, and also where it should appear (x,y).
 * Also include the next brick preview so GUI can display nicely loh.
 */
public final class ViewData {

    private final int[][] brickData;
    private final int xPosition;
    private final int yPosition;
    private final int[][] nextBrickData;

    /**
     * Create a ViewData object to pass info to the GUI.
     *
     * @param brickData the current brick shape (2D array)
     * @param xPosition where the brick is now on the board (x-axis)
     * @param yPosition where the brick is now on the board (y-axis)
     * @param nextBrickData shape of the next brick so UI can preview lah
     */
    public ViewData(int[][] brickData, int xPosition, int yPosition, int[][] nextBrickData) {
        this.brickData = brickData;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.nextBrickData = nextBrickData;
    }

    /**
     * @return a copy of the brick shape (so cannot accidentally modify original loh)
     */
    public int[][] getBrickData() {
        return MatrixOperations.copy(brickData);
    }

    /**
     * @return the x position of the brick
     */
    public int getxPosition() {
        return xPosition;
    }

    /**
     * @return the y position of the brick
     */
    public int getyPosition() {
        return yPosition;
    }

    /**
     * @return copy of the next brick shape for preview
     */
    public int[][] getNextBrickData() {
        return MatrixOperations.copy(nextBrickData);
    }
}
