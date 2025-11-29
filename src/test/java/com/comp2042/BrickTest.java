package com.comp2042;

import com.comp2042.logic.bricks.Brick;
import com.comp2042.logic.bricks.RandomBrickGenerator;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class BrickTest {

    //Test that the generator creates valid bricks with 4by4 grids
    @Test
    public void testGeneratedBricksValidity() {
        RandomBrickGenerator generator = new RandomBrickGenerator();

        // Generate 100 bricks to make sure we test all types (I, J, L, O, etc.)
        for (int i = 0; i < 100; i++) {
            Brick brick = generator.getBrick();

            //Basic check: Brick shouldn't be null
            assertNotNull(brick, "Generator generated a null brick");

            //Shape checking
            List<int[][]> shapes = brick.getShapeMatrix();
            assertNotNull(shapes, "Shape matrix is missing");
            assertFalse(shapes.isEmpty(), "Brick has no rotation states");

            //Every rotation must be a 4by4 grid
            for (int[][] matrix : shapes) {
                assertEquals(4, matrix.length, "Brick width must be 4");
                assertEquals(4, matrix[0].length, "Brick height must be 4");
            }
        }
    }
}