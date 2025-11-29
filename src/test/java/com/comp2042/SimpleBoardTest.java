package com.comp2042;

import com.comp2042.model.SimpleBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.Point;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleBoardTest {

    private SimpleBoard board;
    private final int WIDTH = 10;
    private final int HEIGHT = 20;

    @BeforeEach
    public void setUp() {
        board = new SimpleBoard(WIDTH, HEIGHT);
        board.createNewBrick(); // We need an active brick to test movement
    }

    /**
     * The test verifies that a new board is created with the correct dimensions.
     */
    @Test
    public void testBoardDimensions() {
        int[][] matrix = board.getBoardMatrix();
        assertEquals(WIDTH, matrix.length, "Board width should be 10");
        assertEquals(HEIGHT, matrix[0].length, "Board height should be 20");
    }

    @Test
    public void testMoveBrickDown() {
        int initialY = board.getViewData().getyPosition();
        boolean result = board.moveBrickDown();

        assertTrue(result, "Brick should move down successfully");
        assertEquals(initialY + 1, board.getViewData().getyPosition(), "Y position should increase by 1");
    }

    @Test
    public void testWallCollisionLeft() {
        // Move left repeatedly until hitting the wall
        boolean hitWall = false;
        // The board is 10 wide, trying 15 moves ensures we hit the edge
        for (int i = 0; i < 15; i++) {
            if (!board.moveBrickLeft()) {
                hitWall = true;
                break;
            }
        }
        assertTrue(hitWall, "Brick should eventually return false when hitting the left wall");
    }

    @Test
    public void testWallCollisionRight() {
        boolean hitWall = false;
        for (int i = 0; i < 15; i++) {
            if (!board.moveBrickRight()) {
                hitWall = true;
                break;
            }
        }
        assertTrue(hitWall, "Brick should eventually return false when hitting the right wall");
    }

    @Test
    public void testNewGameResetsScore() {
        board.getScore().add(500); // Add fake score
        board.newGame(); // Reset
        assertEquals(0, board.getScore().scoreProperty().get(), "Score should be 0 after new game");
    }

    @Test
    public void testGhostPosition() {
        Point ghost = board.getGhostPosition();
        assertNotNull(ghost, "Ghost position should not be null");
        assertTrue(ghost.getY() >= board.getViewData().getyPosition(), "Ghost should always be equal to or below current brick");
    }


}