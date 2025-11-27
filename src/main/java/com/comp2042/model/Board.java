package com.comp2042.model;

import java.awt.Point;
/**
 * The main game board that defines how bricks move, rotate, merge, and how the score and rows are managed.*/

public interface Board {

    boolean moveBrickDown();

    boolean moveBrickLeft();

    boolean moveBrickRight();

    boolean rotateLeftBrick();

    boolean createNewBrick();

    int[][] getBoardMatrix();

    ViewData getViewData();

    void mergeBrickToBackground();

    ClearRow clearRows();

    Score getScore();

    void newGame();

    Point getGhostPosition();

    java.util.List<int[][]> getNextThreeBrickShapes();

    int[][] getNextBrickShape();

}
