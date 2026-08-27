package ui;

import agstudios.audio.MusicManager;
import agstudios.audio.SoundManager;
import agstudios.utils.ScreenTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import ui.components.GameButton;

public class SettingsScreen extends StackPane {

    public SettingsScreen() {

        setStyle("-fx-background-color: #050505;");

        // Load stylesheet for custom slider and UI styling
        try {
            var cssUrl = getClass().getResource("/css/style.css");
            if (cssUrl != null) {
                getStylesheets().add(cssUrl.toExternalForm());
            }
        } catch (Exception ignored) {
        }

        // =========================
        // Main Container Panel (Card)
        // =========================
        VBox cardPanel = new VBox(24);
        cardPanel.setAlignment(Pos.CENTER);
        cardPanel.setMaxWidth(700);
        cardPanel.setPrefWidth(680);
        cardPanel.setMinWidth(480);
        cardPanel.setPadding(new Insets(35, 45, 35, 45));

        cardPanel.setStyle("""
                -fx-background-color: #111111;
                -fx-background-radius: 22;
                -fx-border-color: #2d2d2d;
                -fx-border-radius: 22;
                -fx-border-width: 2;
                """);

        DropShadow cardGlow = new DropShadow();
        cardGlow.setRadius(30);
        cardGlow.setColor(Color.rgb(30, 136, 229, 0.12));
        cardPanel.setEffect(cardGlow);

        // =========================
        // Title Section
        // =========================
        Label title = new Label("SETTINGS");
        title.setFont(Font.font("Arial", 42));
        title.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        DropShadow titleGlow = new DropShadow();
        titleGlow.setRadius(15);
        titleGlow.setColor(Color.web("#1E88E5"));
        title.setEffect(titleGlow);

        Label subtitle = new Label("Audio & Sound Preferences");
        subtitle.setFont(Font.font("Arial", 16));
        subtitle.setStyle("-fx-text-fill: #8E8E93;");

        VBox titleBox = new VBox(6);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.getChildren().addAll(title, subtitle);
        VBox.setMargin(titleBox, new Insets(0, 0, 8, 0));

        // =========================
        // Music Volume Card
        // =========================
        double initialMusicVol = MusicManager.getVolume() * 100;

        Label musicPercent = createValueBadge(String.format("%.0f%%", initialMusicVol));

        Slider musicSlider = new Slider(0, 100, initialMusicVol);
        musicSlider.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(musicSlider, Priority.ALWAYS);

        musicSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            musicPercent.setText(String.format("%.0f%%", newVal.doubleValue()));
            MusicManager.setVolume(newVal.doubleValue() / 100.0);
        });

        VBox musicCard = createSettingCard(
                "🎵",
                "Music Volume",
                "Background soundtrack playback",
                musicSlider,
                musicPercent
        );

        // =========================
        // Sound Volume Card
        // =========================
        double initialSoundVol = SoundManager.getVolume() * 100;

        Label soundPercent = createValueBadge(String.format("%.0f%%", initialSoundVol));

        Slider soundSlider = new Slider(0, 100, initialSoundVol);
        soundSlider.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(soundSlider, Priority.ALWAYS);

        soundSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            soundPercent.setText(String.format("%.0f%%", newVal.doubleValue()));
            SoundManager.setVolume(newVal.doubleValue() / 100.0);
        });

        VBox soundCard = createSettingCard(
                "🔊",
                "Sound Effects",
                "UI clicks, game moves & victory effects",
                soundSlider,
                soundPercent
        );

        // =========================
        // Back Button
        // =========================
        GameButton backBtn = new GameButton("⬅ BACK");
        backBtn.setPrefWidth(280);
        backBtn.setPrefHeight(52);

        backBtn.setOnAction(e -> {
            SoundManager.playSound("click.wav");
            ScreenTransition.switchScreen(
                    getScene(),
                    new MainMenu()
            );
        });

        VBox.setMargin(backBtn, new Insets(8, 0, 0, 0));

        // Assemble Card Panel
        cardPanel.getChildren().addAll(
                titleBox,
                musicCard,
                soundCard,
                backBtn
        );

        getChildren().add(cardPanel);
        StackPane.setAlignment(cardPanel, Pos.CENTER);
    }

    /**
     * Helper to create a stylish card container for an audio setting.
     */
    private VBox createSettingCard(String icon, String title, String description, Slider slider, Label valueBadge) {
        VBox card = new VBox(12);
        card.setPadding(new Insets(16, 20, 16, 20));
        card.setStyle("""
                -fx-background-color: #181818;
                -fx-background-radius: 16;
                -fx-border-color: #2a2a2a;
                -fx-border-radius: 16;
                -fx-border-width: 1.5;
                """);

        // Header Row: Icon + Labels on Left, Percentage Badge on Right
        Label iconLabel = new Label(icon);
        iconLabel.setFont(Font.font("Arial", 22));

        Label headingLabel = new Label(title);
        headingLabel.setFont(Font.font("Arial", 18));
        headingLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        Label descLabel = new Label(description);
        descLabel.setFont(Font.font("Arial", 13));
        descLabel.setStyle("-fx-text-fill: #777777;");

        VBox textGroup = new VBox(2);
        textGroup.getChildren().addAll(headingLabel, descLabel);

        HBox leftInfo = new HBox(12);
        leftInfo.setAlignment(Pos.CENTER_LEFT);
        leftInfo.getChildren().addAll(iconLabel, textGroup);

        HBox headerRow = new HBox(15);
        headerRow.setAlignment(Pos.CENTER);
        HBox.setHgrow(leftInfo, Priority.ALWAYS);
        headerRow.getChildren().addAll(leftInfo, valueBadge);

        // Slider Row: Min Icon, Slider, Max Icon
        Label minLabel = new Label("🔈");
        minLabel.setFont(Font.font("Arial", 14));
        minLabel.setStyle("-fx-text-fill: #555555;");

        Label maxLabel = new Label("🔊");
        maxLabel.setFont(Font.font("Arial", 14));
        maxLabel.setStyle("-fx-text-fill: #888888;");

        HBox sliderRow = new HBox(10);
        sliderRow.setAlignment(Pos.CENTER);
        sliderRow.getChildren().addAll(minLabel, slider, maxLabel);

        card.getChildren().addAll(headerRow, sliderRow);
        return card;
    }

    /**
     * Helper to create a stylized neon percentage badge pill.
     */
    private Label createValueBadge(String text) {
        Label badge = new Label(text);
        badge.setFont(Font.font("Arial", 15));
        badge.setStyle("""
                -fx-text-fill: #42A5F5;
                -fx-font-weight: bold;
                -fx-background-color: #0d2238;
                -fx-border-color: #1E88E5;
                -fx-border-radius: 8;
                -fx-background-radius: 8;
                -fx-border-width: 1.2;
                -fx-padding: 4 14 4 14;
                """);
        badge.setMinWidth(70);
        badge.setAlignment(Pos.CENTER);
        return badge;
    }
}