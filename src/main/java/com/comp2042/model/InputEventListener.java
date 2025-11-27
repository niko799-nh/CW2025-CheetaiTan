package com.comp2042.model;

/**
 * This interface is used to handle all input events from the player,each method is called when the user make action.*/
public interface InputEventListener {

    /**
     * Called when the player presses the Down key.*/
    DownData onDownEvent(MoveEvent event);

    /**
     * Called when the player presses the Left key.*/
    ViewData onLeftEvent(MoveEvent event);

    /**
     * Called when the player presses the Right key.*/
    ViewData onRightEvent(MoveEvent event);

    /**
     * Called when the player presses the Rotate key.*/
    ViewData onRotateEvent(MoveEvent event);

    /**
     * Creates a new game. This resets everything without best score.
     */
    void createNewGame();

}