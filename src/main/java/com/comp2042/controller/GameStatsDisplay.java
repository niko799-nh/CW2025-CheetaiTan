package com.comp2042.controller;

import com.comp2042.ui.NotificationPanel;
import javafx.beans.property.IntegerProperty;
import javafx.scene.Group;
import javafx.scene.control.Label;
/**
 * This class is like the scoreboard manager.
 * It handles all the text you see on screen: Score, Level, Lines, and the PAUSED sign.
 * It also handles the popups like "LEVEL UP!" so the player feels syok (excited).
 */
public class GameStatsDisplay {

    private final Group groupNotification;
    private Label scoreDisplay;
    private Label levelDisplay;
    private Label linesDisplay;
    private Label pauseLabel;
    /**
     * Setup the display manager.
     * We pass in the group so we know where to stick the labels.
     */
    public GameStatsDisplay(Group groupNotification) {
        this.groupNotification = groupNotification;
        initLabels();
    }
    /**
     * Show the "PAUSED" text.
     * Use this when player go toilet or want to rest.
     */
    public void showPause() {
        if (pauseLabel == null) {
            pauseLabel = new Label("PAUSED");
            pauseLabel.setStyle("-fx-text-fill: red; -fx-font-size: 36px; -fx-font-weight: bold;");
            pauseLabel.setLayoutX(155);

        }
        if (!groupNotification.getChildren().contains(pauseLabel)) {
            groupNotification.getChildren().add(pauseLabel);
        }
    }
    /**
     * Remove the "PAUSED" text.
     */
    public void hidePause() {
        if (pauseLabel != null) {
            groupNotification.getChildren().remove(pauseLabel);
        }
    }
    /**
     * Create the default labels for Score, Level, and Lines.
     */
    private void initLabels() {
        // Create Score Label
        scoreDisplay = new Label("Score: 0");
        styleLabel(scoreDisplay, -190);
        groupNotification.getChildren().add(scoreDisplay);

        // Create Level Label
        levelDisplay = new Label("Level: 1");
        styleLabel(levelDisplay, -160);

        // Create Lines Label
        linesDisplay = new Label("Lines: 0");
        styleLabel(linesDisplay, -130);

        groupNotification.getChildren().addAll(levelDisplay, linesDisplay);
    }
    /**
     * Helper to make the text look handsome.
     */
    private void styleLabel(Label label, int yPos) {
        label.setStyle("-fx-text-fill: #00FA9A; -fx-font-size: 18px; -fx-font-weight: bold;");
        label.setLayoutX(360);
        label.setLayoutY(yPos);
    }
    /**
     * This one is powerful boss. We bind the label to the score property.
     * Means when the logic side updates the score number, this text changes auto.
     * No need to manually call set text every time. Magic!!!!!!!
     * @param scoreProperty The score value from the logic side.
     */
    public void bindScore(IntegerProperty scoreProperty) {
        scoreDisplay.textProperty().bind(scoreProperty.asString("Score: %d"));
    }
    /**
     * Update the Level and Lines text manually.
     */
    public void updateLevelAndLines(int level, int lines) {
        levelDisplay.setText("Level: " + level);
        linesDisplay.setText("Lines: " + lines);
    }
    /**
     * Reset everything back to zero .Used for New Game.
     */
    public void reset() {
        levelDisplay.setText("Level: 1");
        linesDisplay.setText("Lines: 0");
    }
    /**
     * Show the "LEVEL UP!" popup.
     */
    public void showLevelUp() {
        NotificationPanel lvlUp = new NotificationPanel("LEVEL UP!");
        lvlUp.setLayoutX(120);
        groupNotification.getChildren().add(lvlUp);
        lvlUp.showScore(groupNotification.getChildren());
    }
    /**
     * Show floating bonus points. +50
     */
    public void showScoreBonus(int bonus) {
        NotificationPanel notificationPanel = new NotificationPanel("+" + bonus);
        notificationPanel.setLayoutX(110);
        notificationPanel.setLayoutY(50);
        groupNotification.getChildren().add(notificationPanel);
        notificationPanel.showScore(groupNotification.getChildren());
    }
}