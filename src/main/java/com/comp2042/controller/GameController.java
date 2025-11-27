package com.comp2042.controller;

import com.comp2042.model.*;
import com.comp2042.audio.SoundEffect;
/**
 * The GameController connects the game logic (Board) with the GUI.
 * <p>
 * It listens to key events from the player and tells the board what to do. After the board updates, this controller sends the new data back to the GUI so the screen can refresh.
 * </p>
 */
public class GameController implements InputEventListener {

    private Board board = new SimpleBoard(25, 10);
    private final GuiController viewGuiController;

    /**
     * Creates a new GameController. It also prepares the first brick and sets up the link to the GUI.
     * @param c the GuiController that handles the visual part of the game
     */
    public GameController(GuiController c) {
        viewGuiController = c;
        board.createNewBrick();
        viewGuiController.setEventListener(this);
        viewGuiController.initGameView(board.getBoardMatrix(), board.getViewData());
        viewGuiController.updateNextBricks(board.getNextThreeBrickShapes());
        viewGuiController.bindScore(board.getScore().scoreProperty());
    }

    /**
     * Called when the player or the auto-drop tries to move the brick down.
     * <p>
     * If the brick can move down, thn just update the view.
     * If it cannot, then merge it into the background, clear rows,update score, spawn a new brick, and check for game over.
     * </p>
     * @param event information about who triggered the move (user or timer)
     * @return DownData containing info about cleared rows and the updated view
     */
    @Override
    public DownData onDownEvent(MoveEvent event) {
        boolean canMove = board.moveBrickDown();
        ClearRow clearRow = null;

        if (!canMove) {
            board.mergeBrickToBackground();
            clearRow = board.clearRows();

            if (clearRow.getLinesRemoved() > 0) {
                board.getScore().add(clearRow.getScoreBonus());
                viewGuiController.onLinesCleared(clearRow.getLinesRemoved());
            }

            if (board.createNewBrick()) {
                SoundEffect.stopMusic();
                SoundEffect.playGameOver();

                viewGuiController.gameOver(board.getScore().scoreProperty().get());
            }


            viewGuiController.updateNextBricks(board.getNextThreeBrickShapes());
            viewGuiController.refreshGameBackground(board.getBoardMatrix());

        } else {
            // small bonus for soft-drop by user
            if (event.getEventSource() == EventSource.USER) {
                board.getScore().add(1);
            }
        }

        return new DownData(clearRow, board.getViewData());
    }

    /**
     * Moves the brick to left.
     * @param event the move event
     * @return updated ViewData for the GUI
     */
    @Override
    public ViewData onLeftEvent(MoveEvent event) {
        board.moveBrickLeft();
        return board.getViewData();
    }

    /**
     * Moves the brick to right.
     * @param event the move event
     * @return updated ViewData for the GUI
     */
    @Override
    public ViewData onRightEvent(MoveEvent event) {
        board.moveBrickRight();
        return board.getViewData();
    }

    /**
     * Rotates the current brick.
     * @param event the move event
     * @return updated ViewData for the GUI
     */
    @Override
    public ViewData onRotateEvent(MoveEvent event) {
        board.rotateLeftBrick();
        return board.getViewData();
    }

    /**
     * Starts a fresh game by resetting the board and refreshing the GUI. */
    @Override
    public void createNewGame() {
        board.newGame();
        SoundEffect.playMenuMusic();

        viewGuiController.refreshGameBackground(board.getBoardMatrix());
    }


    /**
     * Returns the game board by this controller.
     * @return the Board object
     */
    public Board getBoard() {
        return board;
    }



}
