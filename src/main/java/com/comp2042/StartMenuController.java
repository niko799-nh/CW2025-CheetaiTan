package com.comp2042;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class StartMenuController {

    @FXML
    private Button startButton;

    @FXML
    private Label bestScoreLabel;

    @FXML
    public void initialize() {
        bestScoreLabel.setText("🏆 Best Score: " + getBestScore());
    }

    private int getBestScore() {
        return BestScore.loadBestScore();
    }

    @FXML
    private void startGame(ActionEvent event) {
        try {
            // Load the main Tetris layout
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gameLayout.fxml"));
            Parent root = loader.load();
            GuiController guiController = loader.getController();
            new GameController(guiController);

            //Switch to game scene
            Stage stage = (Stage) startButton.getScene().getWindow();
            Scene gameScene = new Scene(root, 500, 600);
            stage.setScene(gameScene);
            stage.centerOnScreen();
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void exitGame(ActionEvent event) {
        //Stop music when exiting game
        SoundEffect.stopMusic();
        System.exit(0);
    }
}
