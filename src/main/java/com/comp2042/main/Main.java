package com.comp2042.main;

import com.comp2042.audio.SoundEffect;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * This is the main entry to loads the Start Menu UI and starts the JavaFX window.
 */
public class Main extends Application {
    /**
     * This method opens the JavaFX application, opens the FXML file, set up the main stage,
     * and plays the background music for the menu.
     * @param primaryStage the main window of the application
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/StartMenu.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 500, 600);
        primaryStage.setTitle("Tetris Game");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
        SoundEffect.playMenuMusic();
    }
    /**
     * The main function that starts the JavaFX program.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
