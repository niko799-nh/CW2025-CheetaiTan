package com.comp2042;

import com.comp2042.engine.MatrixOperations;
import com.comp2042.model.ClearRow;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
 * The MatrixOperations class's row checking and score calculation functions are verified by this test class.
 */
public class MatrixOperationsTest {
    /**
     * This test verifies that the method is capable of identifying a complete row.
     * The method is expected to return that one row has been cleared.
     */
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
    /**
     * When there isn't a complete row, this test makes sure the method doesn't clear any rows.
     */
    @Test
    public void testCheckRemovingNoFullRow() {
        int[][] board = new int[4][4];
        board[3][0] = 1; // not full
        ClearRow result = MatrixOperations.checkRemoving(board);
        assertEquals(0, result.getLinesRemoved(), "No full rows should be cleared");
    }
    /**
     * This test checks the bonus point formula when multiple line cleared.
     */
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
