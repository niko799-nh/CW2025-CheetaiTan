package com.comp2042.engine;

import com.comp2042.model.ClearRow;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class that handles matrix operations used by the Tetris board. */

public class MatrixOperations {


    // Private constructor to prevent creating an instance of this class
    private MatrixOperations(){

    }
    /**
     * Checks whether a brick placed at (x, y) would collide with the board or go out of bounds.
     * @param matrix the game board
     * @param brick the shape of the brick
     * @param x the left position of the brick
     * @param y the top position of the brick
     * @return true if the brick hits something or leaves the board; false otherwise
     */

    public static boolean intersect(final int[][] matrix, final int[][] brick, int x, int y) {
        for (int i = 0; i < brick.length; i++) {
            for (int j = 0; j < brick[i].length; j++) {
                int targetX = x + i;
                int targetY = y + j;
                if (brick[j][i] != 0 && (checkOutOfBound(matrix, targetX, targetY) || matrix[targetY][targetX] != 0)) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Merges a brick into the board after it lands.
     * @param filledFields the current board
     * @param brick the brick shape to merge
     * @param x the x-position of the brick
     * @param y the y-position of the brick
     * @return a new matrix containing the merged brick
     */
    public static int[][] merge(int[][] filledFields, int[][] brick, int x, int y) {
        int[][] copy = copy(filledFields);
        for (int i = 0; i < brick.length; i++) {
            for (int j = 0; j < brick[i].length; j++) {
                if (brick[i][j] != 0) {
                    int targetY = y + i;
                    int targetX = x + j;
                    if (!checkOutOfBound(copy, targetX, targetY)) {
                        copy[targetY][targetX] = brick[i][j];
                    }
                }
            }
        }
        return copy;
    }
    /**
     * Checks whether a position is outside the board.
     * @param matrix the board
     * @param targetX the x-position
     * @param targetY the y-position
     * @return true if outside the board; false otherwise
     */

    private static boolean checkOutOfBound(int[][] matrix, int targetX, int targetY) {
        if (targetX < 0 || targetY < 0) return true; //prevent negative index
        if (targetY >= matrix.length) return true;
        if (targetX >= matrix[targetY].length) return true;
        return false;
    }
    /**
     * Creates a deep copy of the board matrix.
     * @param original the matrix to copy
     * @return a new duplicated matrix
     */

    public static int[][] copy(int[][] original) {
        int[][] myInt = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            int[] aMatrix = original[i];
            int aLength = aMatrix.length;
            myInt[i] = new int[aLength];
            System.arraycopy(aMatrix, 0, myInt[i], 0, aLength);
        }
        return myInt;
    }
    /**
     * Removes full rows from the board and pushes remaining rows down.
     * @param matrix the current board
     */

    public static ClearRow checkRemoving(final int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] tmp = new int[rows][cols];
        Deque<int[]> newRows = new ArrayDeque<>();
        int clearedCount = 0;

        for (int i = rows - 1; i >= 0; i--) {
            boolean fullRow = true;
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] == 0) {
                    fullRow = false;
                    break;
                }
            }
            //TRy
            if (fullRow) {
                clearedCount++;
            } else {
                newRows.addFirst(matrix[i]);
            }
        }


        //Fill tmp matrix from bottom
        int newRowIndex = rows - 1;
        while (!newRows.isEmpty()) {
            tmp[newRowIndex--] = newRows.pollLast();
        }
        for (int i = newRowIndex; i >= 0; i--) {
            tmp[i] = new int[cols];
        }

        int scoreBonus = 50 * clearedCount * clearedCount;
        return new ClearRow(clearedCount, tmp, scoreBonus);
    }
    /**
     * Creates a deep copy of a list of brick shapes.
     * @param list the list of matrices to copy
     * @return a new list with copied matrices
     */
    public static List<int[][]> deepCopyList(List<int[][]> list){
        return list.stream().map(MatrixOperations::copy).collect(Collectors.toList());
    }

}
