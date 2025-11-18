package com.comp2042;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClearRowTest {

    @Test
    public void testSingleRowClear() {
        SimpleBoard board = new SimpleBoard(25, 10);
        // Fill bottom row fullyyyy
        int[][] matrix = board.getBoardMatrix();
        int lastRow = matrix.length - 1;
        for (int col = 0; col < matrix[lastRow].length; col++) {
            matrix[lastRow][col] = 1; // any non-zero number
        }
        //Clear rows
        ClearRow result = board.clearRows();

        //Verify 1 row is cleared
        assertEquals(1, result.getLinesRemoved(), "Expected exactly 1 row to be cleared");

        // Verify the bottom row
        int[][] newMatrix = result.getNewMatrix();
        for (int col = 0; col < newMatrix[lastRow].length; col++) {
            assertEquals(0, newMatrix[lastRow][col], "Cleared row should be empty");
        }
    }
}
