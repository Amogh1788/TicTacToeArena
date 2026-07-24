package ui;

import agstudios.audio.MusicManager;
import agstudios.audio.SoundManager;
import agstudios.utils.ScreenTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import ui.components.GameButton;

public class SettingsScreen extends StackPane {

    public SettingsScreen() {

        setStyle("-fx-background-color: #050505;");

        VBox root = new VBox(25);
        root.setAlignment(Pos.CENTER);

        // =========================
        // Title
        // =========================

        Label title = new Label("SETTINGS");
        VBox.setMargin(title, new javafx.geometry.Insets(0, 0, 20, 0));
        title.setFont(Font.font("Arial", 42));
        title.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        // =========================
        // Music
        // =========================

        Label musicLabel = new Label("🎵 Music Volume");
        musicLabel.setFont(Font.font("Arial", 20));
        musicLabel.setStyle("-fx-text-fill: white;");
        musicLabel.setPrefWidth(190);

        Slider musicSlider = new Slider(
                0,
                100,
                MusicManager.getVolume() * 100
        );
        musicSlider.setPrefWidth(320);

        Label musicValue = new Label(
                String.format("%.0f%%", MusicManager.getVolume() * 100)
        );
        musicValue.setFont(Font.font("Arial", 20));
        musicValue.setStyle("-fx-text-fill: white;");
        musicValue.setPrefWidth(60);
        musicValue.setAlignment(Pos.CENTER_RIGHT);

        musicSlider.valueProperty().addListener((obs, oldVal, newVal) -> {

            musicValue.setText(String.format("%.0f%%", newVal.doubleValue()));

            MusicManager.setVolume(newVal.doubleValue() / 100.0);

        });

        HBox musicBox = new HBox(20);
        musicBox.setAlignment(Pos.CENTER);
        musicBox.getChildren().addAll(
                musicLabel,
                musicSlider,
                musicValue
        );

        // =========================
        // Sound
        // =========================

        Label soundLabel = new Label("🔊 Sound Volume");
        soundLabel.setFont(Font.font("Arial", 20));
        soundLabel.setStyle("-fx-text-fill: white;");
        soundLabel.setPrefWidth(190);

        Slider soundSlider = new Slider(
                0,
                100,
                SoundManager.getVolume() * 100
        );
        soundSlider.setPrefWidth(320);

        Label soundValue = new Label(
                String.format("%.0f%%", SoundManager.getVolume() * 100)
        );
        soundValue.setFont(Font.font("Arial", 20));
        soundValue.setStyle("-fx-text-fill: white;");
        soundValue.setPrefWidth(60);
        soundValue.setAlignment(Pos.CENTER_RIGHT);

        soundSlider.valueProperty().addListener((obs, oldVal, newVal) -> {

            soundValue.setText(String.format("%.0f%%", newVal.doubleValue()));

            SoundManager.setVolume(newVal.doubleValue() / 100.0);

        });

        HBox soundBox = new HBox(20);
        soundBox.setAlignment(Pos.CENTER);
        soundBox.getChildren().addAll(
                soundLabel,
                soundSlider,
                soundValue
        );

        // =========================
        // Back Button
        // =========================

        GameButton backBtn = new GameButton("⬅ BACK");
        backBtn.setPrefWidth(260);
        backBtn.setPrefHeight(55);

        backBtn.setOnAction(e -> {

            SoundManager.playSound("click.wav");

            ScreenTransition.switchScreen(
                    getScene(),
                    new MainMenu()
            );

        });

        root.getChildren().addAll(
                title,
                musicBox,
                soundBox,
                backBtn
        );

        getChildren().add(root);
    }
}