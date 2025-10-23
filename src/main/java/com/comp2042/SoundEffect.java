package com.comp2042;

import javafx.scene.media.AudioClip;

public class SoundEffect {

    private static AudioClip bgMusic;
    private static final AudioClip moveSound = new AudioClip(SoundEffect.class.getResource("/sounds/move.wav").toString());
    private static final AudioClip clearSound = new AudioClip(SoundEffect.class.getResource("/sounds/clear.wav").toString());
    private static final AudioClip gameOverSound = new AudioClip(SoundEffect.class.getResource("/sounds/gameover.wav").toString());

    public static void playMenuMusic() {
        if (bgMusic == null) {
            bgMusic = new AudioClip(SoundEffect.class.getResource("/sounds/background.mp3").toString());
            bgMusic.setCycleCount(AudioClip.INDEFINITE);
            bgMusic.setVolume(0.3);
        }

        //continue
        if (!bgMusic.isPlaying()) {
            bgMusic.play();
        }
    }

    public static void stopMusic() {
        if (bgMusic != null && bgMusic.isPlaying()) {
            bgMusic.stop();
        }
    }

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
