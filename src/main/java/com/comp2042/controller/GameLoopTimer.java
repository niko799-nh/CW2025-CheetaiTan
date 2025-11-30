package com.comp2042.controller;

import com.comp2042.model.EventSource;
import com.comp2042.model.EventType;
import com.comp2042.model.MoveEvent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
/**
 * This class is the engine for the gravity.
 * It makes the brick fall down automatically based on a timer.
 * No need user to press down, the block will turun sendiri.
 */
public class GameLoopTimer {

    private Timeline timeline;
    private final GuiController controller;
    private double currentSpeed = 400;
    /**
     * Setup the timer.
     * @param controller We need this so when the timer ticks, we can tell the Controller to move the block.
     */

    public GameLoopTimer(GuiController controller) {
        this.controller = controller;
    }
    /**
     * Start the timer!
     * First stop any existing one to avoid double timing.
     * Then we create a new Timeline. Every tick, send a DOWN event to the controller.
     * Set to INDEFINITE so it keeps running forever.
     */

    public void start() {
        stop();

        timeline = new Timeline(new KeyFrame(
                Duration.millis(currentSpeed),
                ae -> controller.moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD))
        ));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    /**
     * Stop the timer completely.
     * Use this when game over or exiting.
     */

    public void stop() {
        if (timeline != null) {
            timeline.stop();
        }
    }
    /**
     * Pause. Doesn't kill the timeline, just freeze it. Use for Pause menu.
     */

    public void pause() {
        if (timeline != null) {
            timeline.pause();
        }
    }
    /**
     * Resume the timer from where it paused.
     */
    public void resume() {
        if (timeline != null) {
            timeline.play();
        }
    }
    /**
     * Reset the speed back to default (400ms).
     */
    public void resetSpeed() {
        currentSpeed = 400;
    }
    /**
     * Level up means speed up!
     * Calculates new speed based on the level.
     * @param level The current game level. Higher level = faster drop.
     */
    public void updateSpeed(int level) {
        double newSpeed = Math.max(100, 400 - (level - 1) * 40);

        if (newSpeed != currentSpeed) {
            currentSpeed = newSpeed;
            start(); // Restart with new speed
        }
    }
}