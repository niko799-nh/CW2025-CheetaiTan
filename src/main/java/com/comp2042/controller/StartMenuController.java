package com.comp2042.controller;

import com.comp2042.audio.SoundEffect;
import com.comp2042.model.BestScore;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Controller for the start menu screen.
 * <p>
 * This class handles the menu buttons, shows the best score,
 * and switches to the main Tetris game when the player clicks "Start".
 */
public class StartMenuController {

    @FXML
    private Button startButton;

    @FXML
    private Label bestScoreLabel;

    /**
     * Runs automatically when the menu loads.
     * Updates the Best Score label using the saved value.
     */
    @FXML
    public void initialize() {
        bestScoreLabel.setText("🏆 Best Score: " + getBestScore());
    }

    /**
     * Reads the highest score stored on the player's computer.
     * @return the previously saved best score.
     */
    private int getBestScore() {
        return BestScore.loadBestScore();
    }

    /**
     * Handles the Start button.
     * <p>
     * Loads the main game layout and creates a new Tetris game, switches the window to the game scene.
     * @param event the button click
     */
    @FXML
    private void startGame(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gameLayout.fxml"));
            Parent root = loader.load();
            GuiController guiController = loader.getController();
            new GameController(guiController);

            Stage stage = (Stage) startButton.getScene().getWindow();
            Scene gameScene = new Scene(root, 500, 600);
            stage.setScene(gameScene);
            stage.centerOnScreen();
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the Exit button.
     * Stops the game music and closes the program.
     * @param event the button click
     */
    @FXML
    private void exitGame(ActionEvent event) {
        SoundEffect.stopMusic();
        System.exit(0);
    }
}