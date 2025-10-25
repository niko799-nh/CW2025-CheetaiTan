package com.comp2042;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class SoundEffect {

    private static MediaPlayer bgMusicPlayer;
    private static final String MOVE = "/sounds/move.wav";
    private static final String CLEAR = "/sounds/clear.wav";
    private static final String GAME_OVER = "/sounds/gameover.wav";

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

    //Pause background music
    public static void pauseMusic() {
        if (bgMusicPlayer != null && bgMusicPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            bgMusicPlayer.pause();
        }
    }

    //Resume background music
    public static void resumeMusic() {
        if (bgMusicPlayer != null && bgMusicPlayer.getStatus() == MediaPlayer.Status.PAUSED) {
            bgMusicPlayer.play();
        }
    }

    //Stop music
    public static void stopMusic() {
        if (bgMusicPlayer != null) {
            bgMusicPlayer.stop();
        }
    }

    //sound move, clear, and game over effects
    private static void playSound(String filePath) {
        MediaPlayer sound = new MediaPlayer(new Media(SoundEffect.class.getResource(filePath).toString()));
        sound.setVolume(0.5);
        sound.play();
        sound.setOnEndOfMedia(sound::dispose); // clean up
    }

    public static void playMove() {
        playSound(MOVE);
    }

    public static void playClear() {
        playSound(CLEAR);
    }

    public static void playGameOver() {
        playSound(GAME_OVER);
    }
}
