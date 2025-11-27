package com.comp2042.model;

import com.comp2042.engine.BrickRotator;
import com.comp2042.engine.MatrixOperations;
import com.comp2042.logic.bricks.Brick;
import com.comp2042.logic.bricks.BrickGenerator;
import com.comp2042.logic.bricks.RandomBrickGenerator;
import java.util.LinkedList;
import java.util.Queue;

import java.awt.*;
/**
 * SimpleBoard is the main class that controls the game state.
 * It stores the board grid, the current falling brick, the next upcoming bricks,and handles movement, rotation, row clearing and score updates.
 * This class is used by GameController to update the game and by GuiController to draw everything on screen.
 */
public class SimpleBoard implements Board {

    private final int width;
    private final int height;
    private final BrickGenerator brickGenerator;
    private final Queue<Brick> nextBricks = new LinkedList<>();
    private final BrickRotator brickRotator;
    private int[][] currentGameMatrix;
    private Point currentOffset;
    private final Score score;
    /**
     * Creates a new game board with the given width and height.
     * A fresh Score object is created and the board is completely empty.
     * @param width  number of rows in the board
     * @param height number of columns in the board
     */

    public SimpleBoard(int width, int height) {
        this.width = width;
        this.height = height;
        currentGameMatrix = new int[width][height];
        brickGenerator = new RandomBrickGenerator();
        brickRotator = new BrickRotator();
        score = new Score();
    }
    /**
     * Moves the active brick down by one row.
     * If the brick cannot move down this method returns false so the controller knows it must merge the brick.
     * @return true if the move is valid, false if blocked
     */

    @Override
    public boolean moveBrickDown() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        p.translate(0, 1);
        boolean conflict = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }
    /**
     * Moves the active brick left
     * @return true if movement is allowed, false otherwise
     */

    @Override
    public boolean moveBrickLeft() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        p.translate(-1, 0);
        boolean conflict = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }
    /**
     * Moves the active brick right
     * @return true if movement is allowed, false otherwise
     */

    @Override
    public boolean moveBrickRight() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        p.translate(1, 0);
        boolean conflict = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }
    /**
     * Attempts to rotate the current brick.
     * A simple wall kick, if rotation is blocked, the game tries shifting 1 step left or right to make it fit.
     * @return true if rotation succeeds, false if no rotation is possible
     */

    @Override
    public boolean rotateLeftBrick() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        NextShapeInfo nextShape = brickRotator.getNextShape();
        int x = (int) currentOffset.getX();
        int y = (int) currentOffset.getY();
        if (!MatrixOperations.intersect(currentMatrix, nextShape.getShape(), x, y)) {
            brickRotator.setCurrentShape(nextShape.getPosition());
            return true;
        }
        if (!MatrixOperations.intersect(currentMatrix, nextShape.getShape(), x - 1, y)) {
            currentOffset.translate(-1, 0);
            brickRotator.setCurrentShape(nextShape.getPosition());
            return true;
        }
        if (!MatrixOperations.intersect(currentMatrix, nextShape.getShape(), x + 1, y)) {
            currentOffset.translate(1, 0);
            brickRotator.setCurrentShape(nextShape.getPosition());
            return true;
        }
        return false;
    }
    /**
     * Creates the next falling brick, taken from the queue. Always ensures that there are 3 upcoming bricks in the queue.
     * Also sets the starting position of the new brick.
     * @return true if the new brick immediately collides (game over)
     */

    @Override
    public boolean createNewBrick() {
        while (nextBricks.size() < 3) {
            nextBricks.add(brickGenerator.getBrick()); }
        Brick currentBrick = nextBricks.poll();
        brickRotator.setBrick(currentBrick);
        //maintain 3 upcoming
        nextBricks.add(brickGenerator.getBrick());
        currentOffset = new Point(4, 0);
        return MatrixOperations.intersect(
                currentGameMatrix,
                brickRotator.getCurrentShape(),
                (int) currentOffset.getX(),
                (int) currentOffset.getY()
        );
    }

    /**
     * Returns the full game matrix including background blocks.
     * @return board grid as a 2D array
     */

    @Override
    public int[][] getBoardMatrix() {
        return currentGameMatrix;
    }
    /**
     * Returns the current brick shape and its position.
     * GuiController uses this to draw the falling brick.
     * @return a ViewData object containing brick info
     */

    @Override
    public ViewData getViewData() {
        return new ViewData(brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY(), brickGenerator.getNextBrick().getShapeMatrix().get(0));
    }
    /**
     * Copies the falling brick into the background matrix,
     * making it part of the board permanently.
     */

    @Override
    public void mergeBrickToBackground() {
        currentGameMatrix = MatrixOperations.merge(currentGameMatrix, brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
    }
    /**
     * Checks the board for any full rows, removes them,
     * shifts everything down, and calculates score bonus.
     * @return ClearRow object containing new matrix and removed row count
     */

    @Override
    public ClearRow clearRows() {
        ClearRow clearRow = MatrixOperations.checkRemoving(currentGameMatrix);
        currentGameMatrix = clearRow.getNewMatrix();
        return clearRow;

    }
    /**
     * Gets the score object so the UI can bind it and update automatically.
     * @return Score instance for this game
     */

    @Override
    public Score getScore() {
        return score;
    }
    /**
     * Resets everything for a new game: clears the board,
     * resets score, and spawns a new brick.
     */

    @Override
    public void newGame() {
        currentGameMatrix = new int[width][height];
        score.reset();
        createNewBrick();

    }
    /**
     * Calculates the ghost position by simulating how far the brick can fall before collision.
     * @return Point containing ghost X/Y position
     */
    @Override
    public Point getGhostPosition() {
        Point ghostPos = new Point(currentOffset);
        int[][] shape = brickRotator.getCurrentShape();

        // Move ghost piece down until collision
        while (!MatrixOperations.intersect(currentGameMatrix, shape, (int) ghostPos.getX(), (int) ghostPos.getY() + 1)) {
            ghostPos.translate(0, 1);
        }
        return ghostPos;
    }
    /**
     * Gets the next brick shape (the one appearing after the current one).
     * @return a 2D array representing the shape
     */
    @Override
    public int[][] getNextBrickShape() {
        return nextBricks.peek().getShapeMatrix().get(0);
    }
    /**
     * Returns all 3 upcoming brick shapes so the UI can show previews.
     * @return list of brick shapes
     */

    public java.util.List<int[][]> getNextThreeBrickShapes() {
        java.util.List<int[][]> shapes = new java.util.ArrayList<>();
        for (Brick b : nextBricks) {
            shapes.add(b.getShapeMatrix().get(0));
        }
        return shapes;
    }


}
