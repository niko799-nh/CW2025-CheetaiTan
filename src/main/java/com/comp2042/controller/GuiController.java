package com.comp2042.controller;

import com.comp2042.audio.SoundEffect;
import com.comp2042.model.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;
/**
 * This class handles all the UI things for the Tetris game.
 * Basically it updates the screen, listens for keyboard,
 * shows next bricks, ghost piece, score, level everything lah.
 * I only added features like:
 * ghost preview
 * next 3 bricks display
 * level + speed system
 * pause/resume
 * instant drop (Enter/Space)
 * Game exit
 */
public class GuiController implements Initializable {

    private GameRenderer renderer;
    private GameStatsDisplay statsDisplay;
    private GameLoopTimer gameLoop;
    private LevelTracker levelTracker;

    @FXML private GridPane nextBrickPanel;
    @FXML private Label scoreDisplay;
    @FXML private GridPane gamePanel;
    @FXML private Group groupNotification;
    @FXML private GridPane brickPanel;
    @FXML private Button btnPause;
    @FXML private Button btnRestart;
    @FXML private Button btnExit;
    @FXML private Label labelNext;

    private InputEventListener eventListener;
    private final BooleanProperty isPause = new SimpleBooleanProperty();
    private final BooleanProperty isGameOver = new SimpleBooleanProperty();

    /**
     * Here I prepare the game panel, load the digital font,and set up all the keyboard controls.
     * This is basically the startup for the GUI.
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        renderer = new GameRenderer(gamePanel, brickPanel, nextBrickPanel);
        statsDisplay = new GameStatsDisplay(groupNotification);
        gameLoop = new GameLoopTimer(this);
        levelTracker = new LevelTracker();
        GameInputProcessor inputProcessor = new GameInputProcessor(this);
        try {
            Font.loadFont(getClass().getClassLoader().getResource("digital.ttf").toExternalForm(), 38);
        } catch (Exception e) {
            e.printStackTrace();
        }

        gamePanel.setFocusTraversable(true);
        gamePanel.requestFocus();
        gamePanel.setOnKeyPressed(keyEvent -> inputProcessor.handleInput(keyEvent));

        final Reflection reflection = new Reflection();
        reflection.setFraction(0.8);
        reflection.setTopOpacity(0.9);
        reflection.setTopOffset(-12);
    }
    /**
     * Set up the game board visuals and show the first brick.This one only runs once.
     * Creates the board grid, creates the brick blocks,and starts the falling timer.
     */
    public void initGameView(int[][] boardMatrix, ViewData brick) {
        renderer.initGameView(boardMatrix, brick);
        gameLoop.start(); // Much cleaner!
    }

    /**
     * Update the brick position on the screen, also draws the ghost piece.*/
    public void refreshBrick(ViewData brick) {
        if (isPause.getValue() == Boolean.FALSE && isGameOver.getValue() == Boolean.FALSE) {
            GameController gameController = null;
            if (eventListener instanceof GameController) {
                gameController = (GameController) eventListener;
            }
            renderer.renderGame(brick, gameController);
        }
    }
    /**
     * Updates the background matrix on the board,showing bricks that have already landed.*/
    public void refreshGameBackground(int[][] board) {
        renderer.refreshBackground(board);
    }
    /**
     * Handles the gravity movement (Tick Tock).
     * @param event The move event
     */
    public void moveDown(MoveEvent event) {
        if (isPause.getValue() == Boolean.FALSE && isGameOver.getValue() == Boolean.FALSE) {
            DownData downData = eventListener.onDownEvent(event);
            if (downData != null && downData.getViewData() != null && isGameOver.getValue() == Boolean.FALSE) {
                refreshBrick(downData.getViewData());
            }
        }
        gamePanel.requestFocus();
    }
    /**
     * Connects the Logic brain to this Body
     */
    public void setEventListener(InputEventListener eventListener) {
        this.eventListener = eventListener;
    }

    /**
     * Connects the score property to the UI display so it updates automatically.
     * Also sets up the Level and Lines Cleared labels.*/
    public void bindScore(IntegerProperty scoreProperty) {
        statsDisplay.bindScore(scoreProperty);
    }
    /**
     * Show the Game Over panel and stop the game and play the gama over sound
     */
    public void gameOver(int score) {
        gameLoop.stop();
        renderer.clearEverything();

        if (btnPause != null) btnPause.setVisible(false);
        if (btnRestart != null) btnRestart.setVisible(false);
        if (btnExit != null) btnExit.setVisible(false);
        if (nextBrickPanel != null) nextBrickPanel.setVisible(false);
        if (labelNext != null) labelNext.setVisible(false);

        SoundEffect.playGameOver();
        GameOverPanel panel = new GameOverPanel(score, this);
        panel.setLayoutX(10); // Adjust this X until it looks centered
        panel.setLayoutY(-200);
        panel.setStyle("-fx-background-color: rgba(0, 0, 0, 0.85); -fx-background-radius: 20; -fx-padding: 20; -fx-alignment: center; -fx-border-color: red; -fx-border-width: 2; -fx-border-radius: 20;");

        groupNotification.getChildren().clear();
        groupNotification.getChildren().add(panel);

        isGameOver.setValue(Boolean.TRUE);
    }

    /**
     * Reset everything and start a new game.*/

    public void newGame(ActionEvent actionEvent) {
        gameLoop.stop();
        //Hide Game Over
        groupNotification.getChildren().clear();
        if (btnPause != null) btnPause.setDisable(false);btnPause.setVisible(true);
        if (btnRestart != null) btnRestart.setDisable(false);btnRestart.setVisible(true);
        if (btnExit != null) btnExit.setDisable(false);btnExit.setVisible(true);
        if (nextBrickPanel != null) nextBrickPanel.setVisible(true);
        if (labelNext != null) labelNext.setVisible(true);
        //the control buttons for the new game
        statsDisplay = new GameStatsDisplay(groupNotification);
        statsDisplay.bindScore(((GameController) eventListener).getBoard().getScore().scoreProperty());
        gameLoop.resetSpeed();
        //Reset panels
        if (nextBrickPanel != null) nextBrickPanel.getChildren().clear();

        eventListener.createNewGame();
        updateNextBricks(((GameController) eventListener).getBoard().getNextThreeBrickShapes());
        //Restart the falling speed
        gameLoop.start();

        isPause.setValue(Boolean.FALSE);
        isGameOver.setValue(Boolean.FALSE);
        gamePanel.requestFocus();
    }
    /**
     * Toggles pause and resume of game and background music.
     */
    public void pauseGame(ActionEvent actionEvent) {
        if (isPause.getValue()) {
            //game is paused thenresume
            gameLoop.resume();
            isPause.setValue(false);
            SoundEffect.resumeMusic();
            statsDisplay.hidePause();
        } else {
            //If game is running then pause
            gameLoop.pause();
            isPause.setValue(true);
            SoundEffect.pauseMusic();
            //Show "PAUSED" text in the middle
            statsDisplay.showPause();
        }
        gamePanel.requestFocus();
    }
    /**
     * Updates the panel that shows the next 3 upcoming bricks.*/
    public void updateNextBricks(java.util.List<int[][]> nextBricks) {
        // Delegate to renderer
        renderer.updateNextBricks(nextBricks);
    }
    private boolean canMoveFurther(ViewData brick) {
        return brick.getyPosition() < 30;
    }
    /**
     * Hard drop function (ENTER or SPACE).Plays clear sound when lines are removed.
     * Moves the brick all the way down instantly.
     * Also plays the clear sound if lines are destroyed.
     *@param event The input event.
     */
    public void down(MoveEvent event) {
        if (isPause.getValue() == Boolean.FALSE && isGameOver.getValue() == Boolean.FALSE) {
            DownData downData = null;
            ViewData viewData;
            //Keep moving down until brick can't move anymore
            while (true) {
                downData = eventListener.onDownEvent(event);
                // If nothing to show or game ended — stop loop
                if (downData == null || downData.getViewData() == null) {
                    break;
                }
                viewData = downData.getViewData();
                refreshBrick(viewData);
                //play clear sound
                if (downData.getClearRow() != null && downData.getClearRow().getLinesRemoved() > 0) {
                    statsDisplay.showScoreBonus(downData.getClearRow().getScoreBonus());
                    SoundEffect.playClear();
                }
                //Stop if brick cannot move anymore
                if (downData.getClearRow() != null || !canMoveFurther(viewData)) {
                    break;
                }
            }
            //Merge to background to finalize placement
            eventListener.onDownEvent(new MoveEvent(EventType.DOWN, EventSource.THREAD));
        }
    }
    /**
     *Updates line count, level, speed, and plays animations.
     */
    public void onLinesCleared(int removed) {
        if (removed <= 0) return;

        int oldLevel = levelTracker.getLevel();
        levelTracker.addLines(removed);
        int currentLevel = levelTracker.getLevel();

        // Update UI
        statsDisplay.updateLevelAndLines(currentLevel, levelTracker.getLinesCleared());

        // Check for Level Up
        if (currentLevel > oldLevel) {
            statsDisplay.showLevelUp();
            gameLoop.updateSpeed(currentLevel);
        }
    }
    /**
     * Goes back to the main menu when the player press the Exit button.
     * This reload the start menu screen and continue the music.
     * @param event the button click action
     */
    @FXML
    public void exitToMenu(ActionEvent event) {
        try {
            gameLoop.stop();
            SoundEffect.playMenuMusic();
            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("/StartMenu.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) gamePanel.getScene().getWindow();
            Scene scene = new Scene(root, 500, 600);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public BooleanProperty isPause() {
        return isPause;
    }
    public BooleanProperty isGameOver() {
        return isGameOver;
    }

    public InputEventListener getEventListener() {
        return eventListener;
    }
}