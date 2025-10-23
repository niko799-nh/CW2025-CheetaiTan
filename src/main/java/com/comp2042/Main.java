package com.comp2042;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/StartMenu.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 500, 600);
        primaryStage.setTitle("Tetris Game");
        primaryStage.setScene(scene);
        primaryStage.show();
        SoundEffect.playMenuMusic();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
