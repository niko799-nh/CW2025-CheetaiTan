package com.comp2042.engine;

import com.comp2042.model.NextShapeInfo;
import com.comp2042.logic.bricks.Brick;

/**
 * Handles rotation for the current falling brick.
 * <p>
 * Each brick has several rotation states (90°).
 * This class keeps track of which rotation is currently active and provides the next rotation when the player rotates the brick.
 */
public class BrickRotator {

    /** The brick currently falling in the game. */
    private Brick brick;

    /** Index of the current rotation state. */
    private int currentShape = 0;

    /**
     * Returns information about the next rotation of the brick.
     * @return NextShapeInfo containing the rotated shape and rotation index.
     */
    public NextShapeInfo getNextShape() {
        int nextShape = currentShape;
        nextShape = (++nextShape) % brick.getShapeMatrix().size();
        return new NextShapeInfo(brick.getShapeMatrix().get(nextShape), nextShape);
    }

    /**
     * Gets the brick's current rotation.
     * @return 2D array representing the brick's current shape.
     */
    public int[][] getCurrentShape() {
        return brick.getShapeMatrix().get(currentShape);
    }

    /**
     * Updates the rotation index so the brick switches to the new rotated shape.
     * @param currentShape the rotation index to switch to
     */
    public void setCurrentShape(int currentShape) {
        this.currentShape = currentShape;
    }

    /**
     * Sets a new brick as the active falling brick.
     * When a new brick appears, its rotation resets to the first state.
     * @param brick the new brick to control
     */
    public void setBrick(Brick brick) {
        this.brick = brick;
        currentShape = 0;
    }
}
