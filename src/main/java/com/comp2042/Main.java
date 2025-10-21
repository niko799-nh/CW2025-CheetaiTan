package com.comp2042;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

public class Main extends Application {

    private static AudioClip bgMusic;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/StartMenu.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 500, 600);

        primaryStage.setTitle("Tetris Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        //Play background music globally
        bgMusic = new AudioClip(getClass().getResource("/sounds/background.mp3").toString());
        bgMusic.setCycleCount(AudioClip.INDEFINITE);
        bgMusic.setVolume(0.3);
        bgMusic.play();
    }

    public static void stopMusic() {
        if (bgMusic != null) bgMusic.stop();
    }

    public static void startMusic() {
        if (bgMusic != null && !bgMusic.isPlaying()) bgMusic.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
