package com.comp2042;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MatrixOperationsTest {

    @Test
    public void testCheckRemovingDetectsFullRow() {
        int[][] board = new int[5][5];

        //Fill bottom row completely
        for (int j = 0; j < 5; j++) {
            board[4][j] = 1;
        }
        //Run method
        ClearRow result = MatrixOperations.checkRemoving(board);
        assertEquals(1, result.getLinesRemoved(), "Should clear one full row");
    }
    @Test
    public void testCheckRemovingNoFullRow() {
        int[][] board = new int[4][4];
        board[3][0] = 1; // not full
        ClearRow result = MatrixOperations.checkRemoving(board);
        assertEquals(0, result.getLinesRemoved(), "No full rows should be cleared");
    }
    @Test
    public void testScoreBonusFormula() {
        int[][] board = new int[4][4];
        for (int j = 0; j < 4; j++) {
            board[3][j] = 1;
            board[2][j] = 1;
        }
        ClearRow result = MatrixOperations.checkRemoving(board);
        int expectedBonus = 50 * 2 * 2;
        assertEquals(expectedBonus, result.getScoreBonus(), "Score bonus should match formula 50*n^2");
    }
}
