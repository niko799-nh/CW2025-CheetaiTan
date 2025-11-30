package com.comp2042.controller;

import com.comp2042.model.ViewData;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
/**
 * This is the Painter class.
 * It handles all the graphics. It draws the board, the falling brick, the ghost
 * and the 'Next Brick' preview. If screen looks ugly, blame this class.
 */
public class GameRenderer {

    private static final int BRICK_SIZE = 20;
    private static final int OFFSET_X = -107;
    private static final int OFFSET_Y = -42;

    private static final Paint[] BRICK_COLORS = {
            Color.TRANSPARENT, Color.AQUA, Color.BLUEVIOLET, Color.DARKGREEN,
            Color.YELLOW, Color.RED, Color.BEIGE, Color.BURLYWOOD
    };

    private final GridPane gamePanel;
    private final GridPane brickPanel;
    private final GridPane nextBrickPanel;
    private Rectangle[][] displayMatrix;
    private Rectangle[][] rectangles;
    /**
     * Setup the painter with the 3 main Panels.
     */
    public GameRenderer(GridPane gamePanel, GridPane brickPanel, GridPane nextBrickPanel) {
        this.gamePanel = gamePanel;
        this.brickPanel = brickPanel;
        this.nextBrickPanel = nextBrickPanel;
    }
    /**
     * Initialize the view when game starts.
     * To create all the empty rectangles first and put them in the grid.
     * Then just change their color, no need create new object every time .
     * @param boardMatrix The logical grid of the game.
     * @param brick The current falling brick data.
     */
    public void initGameView(int[][] boardMatrix, ViewData brick) {
        gamePanel.getChildren().clear();
        brickPanel.getChildren().clear();

        displayMatrix = new Rectangle[boardMatrix.length][boardMatrix[0].length];
        for (int i = 2; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(Color.TRANSPARENT);
                displayMatrix[i][j] = rectangle;
                gamePanel.add(rectangle, j, i - 2);
            }
        }

        rectangles = new Rectangle[brick.getBrickData().length][brick.getBrickData()[0].length];
        for (int i = 0; i < brick.getBrickData().length; i++) {
            for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(getFillColor(brick.getBrickData()[i][j]));
                rectangles[i][j] = rectangle;
                brickPanel.add(rectangle, j, i);
            }
        }
        updateBrickPanelPosition(brick);
    }
    /**
     * This is the main draw loop.
     * Clear old ghost.Draw new ghost.Update the falling brick position.
     * @param brick Current falling brick.
     * @param controller We need this to ask where the ghost should be.
     */
    public void renderGame(ViewData brick, GameController controller) {
        gamePanel.getChildren().removeIf(node -> node.getStyleClass().contains("ghost"));
        if (controller != null) {
            var ghostPos = controller.getBoard().getGhostPosition();
            for (int i = 0; i < brick.getBrickData().length; i++) {
                for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                    if (brick.getBrickData()[i][j] != 0) {
                        int targetRow = i + ghostPos.y - 2;
                        int targetCol = j + ghostPos.x;
                        if (targetRow >= 0) {
                            Rectangle ghostBlock = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                            ghostBlock.setFill(Color.web("#FFFFFF", 0.25));
                            ghostBlock.setArcHeight(9);
                            ghostBlock.setArcWidth(9);
                            ghostBlock.getStyleClass().add("ghost");
                            gamePanel.add(ghostBlock, targetCol, targetRow);
                        }
                    }
                }
            }
        }
        updateBrickPanelPosition(brick);

        for (int i = 0; i < brick.getBrickData().length; i++) {
            for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                setRectangleData(brick.getBrickData()[i][j], rectangles[i][j]);
            }
        }
    }
    /**
     * Calculates the exact pixel position on screen.
     */
    private void updateBrickPanelPosition(ViewData brick) {
        double xPos = OFFSET_X + brick.getxPosition() * (brickPanel.getVgap() + BRICK_SIZE);
        double yPos = OFFSET_Y + gamePanel.getLayoutY() + brick.getyPosition() * (brickPanel.getHgap() + BRICK_SIZE);

        brickPanel.setTranslateX(xPos);
        brickPanel.setTranslateY(yPos);
    }
    /**
     * Refreshes the board background.
     * When a brick lands, it becomes part of the board, so we paint it here.
     */
    public void refreshBackground(int[][] board) {
        for (int i = 2; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                setRectangleData(board[i][j], displayMatrix[i][j]);
            }
        }
    }/**
     * Wipe everything clean.Used when game over or restarting.
     */
    public void clearEverything() {
        if (rectangles != null) {
            for (int i = 0; i < rectangles.length; i++) {
                for (int j = 0; j < rectangles[i].length; j++) {
                    if (rectangles[i][j] != null) {
                        rectangles[i][j].setFill(Color.TRANSPARENT);
                    }
                }
            }
        }
        gamePanel.getChildren().removeIf(node -> node.getStyleClass().contains("ghost"));
    }

    private void setRectangleData(int color, Rectangle rectangle) {
        rectangle.setFill(getFillColor(color));
        rectangle.setArcHeight(9);
        rectangle.setArcWidth(9);
    }
    /**
     * Maps the number ID to actual Color object.
     */
    private Paint getFillColor(int i) {
        if (i >= 0 && i < BRICK_COLORS.length) return BRICK_COLORS[i];
        return Color.WHITE;
    }
    /**
     * Draws the upcoming bricks in the side panel.
     * A bit complex math here to make sure the bricks are centered nicely.
     */
    public void updateNextBricks(java.util.List<int[][]> nextBricks) {
        nextBrickPanel.getChildren().clear();

        int offsetY = 5;
        int gapBetweenBricks = 20;
        for (int[][] brickShape : nextBricks) {

            int minX = Integer.MAX_VALUE;
            int minY = Integer.MAX_VALUE;
            int maxX = Integer.MIN_VALUE;
            int maxY = Integer.MIN_VALUE;

            for (int i = 0; i < brickShape.length; i++) {
                for (int j = 0; j < brickShape[i].length; j++) {
                    if (brickShape[i][j] != 0) {
                        if (j < minX) minX = j;
                        if (i < minY) minY = i;
                        if (j > maxX) maxX = j;
                        if (i > maxY) maxY = i;
                    }
                }
            }

            int brickWidth = maxX - minX + 1;
            int brickHeight = maxY - minY + 1;

            int panelWidth = 6;
            int startX = (panelWidth - brickWidth) / 2;

            for (int i = 0; i < brickShape.length; i++) {
                for (int j = 0; j < brickShape[i].length; j++) {
                    if (brickShape[i][j] != 0) {
                        Rectangle r = new Rectangle(18, 18);
                        r.setArcHeight(6);
                        r.setArcWidth(6);
                        r.setFill(getFillColor(brickShape[i][j]));
                        nextBrickPanel.add(r, j - minX + startX, i - minY + offsetY);
                    }
                }
            }
            offsetY += brickHeight + gapBetweenBricks;
        }
    }
}