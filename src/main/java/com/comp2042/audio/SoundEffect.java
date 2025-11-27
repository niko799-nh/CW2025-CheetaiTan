package com.comp2042.audio;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Handles all game audio, including background music and sound effects like move, clear, and game over.
 */
public class SoundEffect {

    private static MediaPlayer bgMusicPlayer;
    private static final String MOVE = "/sounds/move.wav";
    private static final String CLEAR = "/sounds/clear.wav";
    private static final String GAME_OVER = "/sounds/gameover.wav";

    /**
     * Starts the background music on the menu and during gameplay.
     * Music loops forever.
     */
    public static void playMenuMusic() {
        if (bgMusicPlayer == null) {
            Media bgMusic = new Media(SoundEffect.class.getResource("/sounds/background.mp3").toString());
            bgMusicPlayer = new MediaPlayer(bgMusic);
            bgMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            bgMusicPlayer.setVolume(0.3);
        }

        if (bgMusicPlayer.getStatus() != MediaPlayer.Status.PLAYING) {
            bgMusicPlayer.play();
        }
    }

    /**
     * Pauses the background music when used is paused */
    public static void pauseMusic() {
        if (bgMusicPlayer != null && bgMusicPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            bgMusicPlayer.pause();
        }
    }

    /**
     * Resumes playing background music. */
    public static void resumeMusic() {
        if (bgMusicPlayer != null && bgMusicPlayer.getStatus() == MediaPlayer.Status.PAUSED) {
            bgMusicPlayer.play();
        }
    }

    /**
     * Stops background music completely. */
    public static void stopMusic() {
        if (bgMusicPlayer != null) {
            bgMusicPlayer.stop();
        }
    }

    /**
     * Plays a single sound effect from a given file.
     * Each sound has its own MediaPlayer instance.
     */
    private static void playSound(String filePath) {
        MediaPlayer sound = new MediaPlayer(new Media(SoundEffect.class.getResource(filePath).toString()));
        sound.setVolume(0.5);
        sound.play();
        sound.setOnEndOfMedia(sound::dispose);
    }

    /**
     * Plays the movement sound when the player moves or rotates the brick. */
    public static void playMove() {
        playSound(MOVE);
    }

    /**
     * Plays the line clear sound. */
    public static void playClear() {
        playSound(CLEAR);
    }

    /**
     * Plays the game over sound. */
    public static void playGameOver() {
        playSound(GAME_OVER);
    }
}
