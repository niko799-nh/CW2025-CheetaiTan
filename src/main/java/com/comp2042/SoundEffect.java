package com.comp2042;

import javafx.scene.media.AudioClip;

public class SoundEffect {

    private static final AudioClip moveSound = new AudioClip(SoundEffect.class.getResource("/sounds/move.wav").toString());
    private static final AudioClip clearSound = new AudioClip(SoundEffect.class.getResource("/sounds/clear.wav").toString());
    private static final AudioClip gameOverSound = new AudioClip(SoundEffect.class.getResource("/sounds/gameover.wav").toString());

    public static void playMove() {
        moveSound.play();
    }

    public static void playClear() {
        clearSound.play();
    }

    public static void playGameOver() {
        gameOverSound.play();
    }
}
