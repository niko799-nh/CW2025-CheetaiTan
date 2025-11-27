package com.comp2042.model;

import com.comp2042.engine.MatrixOperations;

/**
 * This class just keeps track of the next rotated shape of a brick.Basically, when the player rotates, we check this shape first.
 * Simple info holder only lah.
 */
public final class NextShapeInfo {

    private final int[][] shape;
    private final int position;

    /**
     * Create a NextShapeInfo object.
     * @param shape  The rotated brick shape.
     * @param position  The index of this shape in the rotation list.
     */
    public NextShapeInfo(final int[][] shape, final int position) {
        this.shape = shape;
        this.position = position;
    }

    /**
     * Get the rotated shape.
     * Return a copy so outside code won’t accidentally modify it loh.
     * @return A safe copy of the shape.
     */
    public int[][] getShape() {
        return MatrixOperations.copy(shape);
    }

    /**
     * Get the rotation index.
     * @return the position in the rotation cycle.
     */
    public int getPosition() {
        return position;
    }
}
