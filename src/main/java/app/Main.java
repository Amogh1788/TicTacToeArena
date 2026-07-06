package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.IntroScreen;
import javafx.scene.paint.Color;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        IntroScreen intro = new IntroScreen(stage);

        Scene scene = new Scene(intro, 1280, 720);
        scene.setFill(javafx.scene.paint.Color.BLACK);

        stage.setTitle("TicTacToe Arena");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}