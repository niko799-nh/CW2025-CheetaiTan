package com.comp2042;

import com.comp2042.model.ClearRow;
import com.comp2042.model.SimpleBoard;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
 * The row-clearing function of the SimpleBoard class is tested using this test class. It primarily tests the
 * ability to identify and appropriately remove a single full row.
 */
public class ClearRowTest {
    /**
     * This test calls clearRows() after the bottom row is fully filled.
     * A result indicating that one row has been cleared should be returned by the method.
     * Additionally, it verifies that the cleared row in the new matrix becomes empty.
     */

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
