package com.comp2042.controller;

import com.comp2042.model.BestScore;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
/**
 * This class is the Game Over panel la.
 * <p>
 * Basically when the user game over, this screen will pop up.
 */
public class GameOverPanel extends VBox {
    /**
     * Constructor to setup the Game Over screen.Inside here we do a few things:
     * Check if your score is power enough to beat the Best Score. If yes, save it.
     * Add the Restart and Exit buttons.
     * </p>
     * @param score      The score just got from the game
     * @param controller The main controller, use this to tell game to Restart or Go Menu.
     */

    public GameOverPanel(int score, GuiController controller) {

        this.getStyleClass().clear();
        this.setStyle("");

        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.setPrefWidth(470);
        this.setMinHeight(590);
        this.setMaxWidth(450);
        this.setPadding(new Insets(40, 40, 40, 40));

        this.getStyleClass().add("gameOverPanel");

        Label title = new Label("GAME OVER");
        title.getStyleClass().add("gameOverTitle");

        int bestScore = BestScore.loadBestScore();
        if (score > bestScore) {
            BestScore.saveBestScore(score);
            bestScore = score;
        }

        Label current = new Label("CURRENT SCORE: " + score);
        current.getStyleClass().add("scoreText");

        Label best = new Label("BEST SCORE: " + bestScore);
        best.getStyleClass().add("scoreText");

        Button restart = new Button("RESTART (N)");
        restart.setPrefSize(220, 55);
        restart.getStyleClass().add("pastelButton");
        restart.setOnAction(e -> controller.newGame(null));

        Button exit = new Button("EXIT (E)");
        exit.setPrefSize(220, 55);
        exit.getStyleClass().add("pastelButton");
        exit.setOnAction(e -> controller.exitToMenu(null));

        this.getChildren().addAll(title, current, best, restart, exit);
    }
}
