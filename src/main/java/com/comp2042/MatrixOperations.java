package com.comp2042;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class MatrixOperations {


    //We don't want to instantiate this utility class
    private MatrixOperations(){

    }

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

    private static boolean checkOutOfBound(int[][] matrix, int targetX, int targetY) {
        if (targetX < 0 || targetY < 0) return true; //prevent negative index
        if (targetY >= matrix.length) return true;
        if (targetX >= matrix[targetY].length) return true;
        return false;
    }
    
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


    public static List<int[][]> deepCopyList(List<int[][]> list){
        return list.stream().map(MatrixOperations::copy).collect(Collectors.toList());
    }

}
