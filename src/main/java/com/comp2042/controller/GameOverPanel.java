package com.comp2042.controller;

import com.comp2042.model.BestScore;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Game over panel shown when the game ends.
 * It displays the player's final score, the saved best score, and a hint for starting a new game.
 * This class extends VBox so it can stack labels vertically.
 */
public class GameOverPanel extends VBox {

    /**
     * Empty constructor required by JavaFX when loading from FXML.*/
    public GameOverPanel() {
        // Required empty constructor
    }

    /**
     * Creates a Game Over panel using the player's final score. It also loads the best score from file and updates it if needed.
     * @param score The final score the player achieved before losing.*/
    // Constructor with score
    public GameOverPanel(int score) {
        setSpacing(10);

        Label gameOverLabel = new Label("GAME OVER");
        gameOverLabel.getStyleClass().add("gameOverStyle");

        // Load and compare best score
        int bestScore = BestScore.loadBestScore();

        if (score > bestScore) {
            BestScore.saveBestScore(score);
            bestScore = score;
        }

        Label scoreLabel = new Label("Current Score: " + score);
        scoreLabel.setTextFill(Color.LIGHTGREEN);

        Label bestLabel = new Label("Best Score: " + bestScore);
        bestLabel.setTextFill(Color.GOLD);

        //instruction next game
        Label hintLabel = new Label("Press 'N' for New Game \nPress 'E' for Exit");
        hintLabel.setTextFill(Color.LIGHTBLUE);
        hintLabel.setStyle("-fx-font-size: 14px; -fx-font-style: italic;");

        // Add everything
        getChildren().addAll(gameOverLabel, scoreLabel, bestLabel, hintLabel);
    }
}
