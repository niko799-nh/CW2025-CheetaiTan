package com.comp2042;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class GameOverPanel extends VBox {

    public GameOverPanel() {
        // Required empty constructor
    }

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
        Label hintLabel = new Label("Press 'N' for New Game");
        hintLabel.setTextFill(Color.LIGHTBLUE);
        hintLabel.setStyle("-fx-font-size: 14px; -fx-font-style: italic;");

        // Add everything
        getChildren().addAll(gameOverLabel, scoreLabel, bestLabel, hintLabel);
    }
}
