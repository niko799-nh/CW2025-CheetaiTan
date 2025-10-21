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
            //Load the main Tetris game layout
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gameLayout.fxml"));
            Parent root = loader.load();
            GuiController guiController = loader.getController();

            //Connect GUI to game logic
            new GameController(guiController);

            //Switch scene
            Stage stage = (Stage) startButton.getScene().getWindow();
            Scene gameScene = new Scene(root, 500, 600);
            stage.setScene(gameScene);
            stage.centerOnScreen();
            stage.show();

            //Keep background music playing
            Main.startMusic();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void exitGame(ActionEvent event) {
        Main.stopMusic();
        System.exit(0);
    }
}
