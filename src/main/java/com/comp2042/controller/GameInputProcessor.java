package com.comp2042.controller;

import com.comp2042.audio.SoundEffect;
import com.comp2042.model.EventSource;
import com.comp2042.model.EventType;
import com.comp2042.model.MoveEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * This class handles all the keyboard pressing one.
 * It helps take the heavy work from GuiController, so that code won't be so serabut (messy).
 * Basically, separate the logic la, easier to maintain.
 */
public class GameInputProcessor {

    private final GuiController gui;

    /**
     * Constructor to setup the processor.
     * @param gui The main controller. Must pass this in, if not then cannot control the game.
     */

    public GameInputProcessor(GuiController gui) {
        this.gui = gui;
    }

    /**
     * This is the main place we check what key the user press.
     * <p>
     * If the game is running (not pause, not game over), we let them move the blocks.
     * Also check for global keys like 'P' for pause and 'N' for new game here.
     * </p>
     * @param keyEvent The key event from JavaFX, check the code inside.
     */

    public void handleInput(KeyEvent keyEvent) {
        if (gui.isPause().getValue() == Boolean.FALSE && gui.isGameOver().getValue() == Boolean.FALSE) {

            if (gui.getEventListener() == null) return;

            switch (keyEvent.getCode()) {
                case LEFT:
                case A:
                    gui.refreshBrick(gui.getEventListener().onLeftEvent(new MoveEvent(EventType.LEFT, EventSource.USER)));
                    SoundEffect.playMove();
                    break;
                case RIGHT:
                case D:
                    gui.refreshBrick(gui.getEventListener().onRightEvent(new MoveEvent(EventType.RIGHT, EventSource.USER)));
                    SoundEffect.playMove();
                    break;
                case UP:
                case W:
                    gui.refreshBrick(gui.getEventListener().onRotateEvent(new MoveEvent(EventType.ROTATE, EventSource.USER)));
                    SoundEffect.playMove();
                    break;
                case DOWN:
                case S:
                    gui.moveDown(new MoveEvent(EventType.DOWN, EventSource.USER));
                    SoundEffect.playMove();
                    break;
                case ENTER:
                case SPACE:
                    gui.down(new MoveEvent(EventType.DOWN, EventSource.USER));
                    break;
            }
        }

        if (keyEvent.getCode() == KeyCode.N) gui.newGame(null);
        if (keyEvent.getCode() == KeyCode.P && !gui.isGameOver().getValue()) gui.pauseGame(null);
        if (keyEvent.getCode() == KeyCode.E) gui.exitToMenu(null);

        keyEvent.consume();
    }
}