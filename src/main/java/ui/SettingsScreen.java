package ui;

import agstudios.audio.MusicManager;
import agstudios.audio.SoundManager;
import agstudios.utils.ScreenTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import ui.components.GameBackground;
import ui.components.GameButton;

public class SettingsScreen extends StackPane {

    public SettingsScreen() {
        // Load stylesheet
        try {
            var css = getClass().getResource("/css/style.css");
            if (css != null) {
                getStylesheets().add(css.toExternalForm());
            }
        } catch (Exception ignored) {}

        // Background
        GameBackground background = new GameBackground("/images/bg_settings.jpg");
        getChildren().add(background);

        // Center Content Box (directly over forest background)
        VBox contentBox = new VBox(14);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setMaxWidth(620);
        contentBox.setPadding(new Insets(20, 20, 20, 20));

        // ==========================================
        // 1. Header (directly on background)
        // ==========================================
        Label gearIcon = new Label("⚙");
        gearIcon.setFont(Font.font("Segoe UI Emoji", 32));
        gearIcon.setTextFill(Color.rgb(206, 147, 216));

        DropShadow gearGlow = new DropShadow();
        gearGlow.setColor(Color.rgb(186, 104, 200, 0.9));
        gearGlow.setRadius(16);
        gearIcon.setEffect(gearGlow);

        Label title = new Label("SETTINGS");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 34));
        title.setStyle("-fx-text-fill: white; -fx-letter-spacing: 2px;");

        DropShadow titleGlow = new DropShadow();
        titleGlow.setColor(Color.rgb(171, 71, 188, 0.65));
        titleGlow.setRadius(18);
        title.setEffect(titleGlow);

        Label subtitle = new Label("Customize your experience");
        subtitle.setFont(Font.font("Segoe UI", 13.5));
        subtitle.setStyle("-fx-text-fill: #ce93d8;");

        Label diamondDivider = new Label("◇");
        diamondDivider.setFont(Font.font("Segoe UI", 10));
        diamondDivider.setStyle("-fx-text-fill: #ab47bc;");

        VBox headerBox = new VBox(3);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.getChildren().addAll(gearIcon, title, subtitle, diamondDivider);
        VBox.setMargin(headerBox, new Insets(0, 0, 4, 0));

        // ==========================================
        // 2. AUDIO Panel (Translucent Glass Card)
        // ==========================================
        VBox audioPanel = new VBox(12);
        audioPanel.setAlignment(Pos.CENTER_LEFT);
        audioPanel.setPadding(new Insets(14, 20, 16, 20));
        audioPanel.getStyleClass().add("glass-panel-purple-settings");

        Label audioHeader = new Label("♫  AUDIO");
        audioHeader.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        audioHeader.setStyle("-fx-text-fill: #e1bee7; -fx-letter-spacing: 1.5px;");

        // Music Volume Row
        double initialMusicVol = MusicManager.getVolume() * 100;
        Label musicPercent = createPercentBadge(String.format("%.0f%%", initialMusicVol));
        Slider musicSlider = createPurpleSlider(initialMusicVol);
        musicSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            musicPercent.setText(String.format("%.0f%%", newVal.doubleValue()));
            MusicManager.setVolume(newVal.doubleValue() / 100.0);
        });
        HBox musicRow = createControlRow("🎵", "Music Volume", "Adjust background music volume", musicSlider, musicPercent);

        // Sound Volume Row
        double initialSoundVol = SoundManager.getVolume() * 100;
        Label soundPercent = createPercentBadge(String.format("%.0f%%", initialSoundVol));
        Slider soundSlider = createPurpleSlider(initialSoundVol);
        soundSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            soundPercent.setText(String.format("%.0f%%", newVal.doubleValue()));
            SoundManager.setVolume(newVal.doubleValue() / 100.0);
        });
        HBox soundRow = createControlRow("🔊", "Sound Volume", "Adjust sound effects volume", soundSlider, soundPercent);

        audioPanel.getChildren().addAll(audioHeader, musicRow, soundRow);

        // ==========================================
        // 3. GAME Panel (Translucent Glass Card)
        // ==========================================
        VBox gamePanel = new VBox(12);
        gamePanel.setAlignment(Pos.CENTER_LEFT);
        gamePanel.setPadding(new Insets(14, 20, 16, 20));
        gamePanel.getStyleClass().add("glass-panel-purple-settings");

        Label gameHeader = new Label("🎮  GAME");
        gameHeader.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        gameHeader.setStyle("-fx-text-fill: #e1bee7; -fx-letter-spacing: 1.5px;");

        StackPane winAnimToggle = createModernToggle(true, (enabled) -> {});
        HBox winAnimRow = createRowWithRightNode("✨", "Show Win Animation", "Show animation when you win the game", winAnimToggle);

        StackPane soundToggle = createModernToggle(true, (enabled) -> {
            SoundManager.setVolume(enabled ? 1.0 : 0.0);
            soundSlider.setValue(enabled ? 100 : 0);
        });
        HBox soundToggleRow = createRowWithRightNode("🔊", "Enable Sounds", "Enable all game sound effects", soundToggle);

        gamePanel.getChildren().addAll(gameHeader, winAnimRow, soundToggleRow);

        // ==========================================
        // 4. ABOUT Panel (Translucent Glass Card)
        // ==========================================
        VBox aboutPanel = new VBox(10);
        aboutPanel.setAlignment(Pos.CENTER_LEFT);
        aboutPanel.setPadding(new Insets(12, 20, 14, 20));
        aboutPanel.getStyleClass().add("glass-panel-purple-settings");

        Label aboutHeader = new Label("ⓘ  ABOUT");
        aboutHeader.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        aboutHeader.setStyle("-fx-text-fill: #e1bee7; -fx-letter-spacing: 1.5px;");

        Label versionLabel = new Label("v1.1.0");
        versionLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        versionLabel.setStyle("-fx-text-fill: #ba68c8;");

        HBox versionRow = createRowWithRightNode("ⓘ", "Game Version", "You are playing the latest version", versionLabel);
        aboutPanel.getChildren().addAll(aboutHeader, versionRow);

        // ==========================================
        // 5. Back Button (Centered Bottom)
        // ==========================================
        GameButton backBtn = new GameButton("←  BACK", GameButton.Variant.BACK);
        backBtn.setPrefWidth(180);
        backBtn.setPrefHeight(42);
        backBtn.getStyleClass().add("btn-pill-purple");

        backBtn.setOnAction(e -> {
            SoundManager.playSound("click.wav");
            ScreenTransition.switchScreen(getScene(), new MainMenu());
        });
        VBox.setMargin(backBtn, new Insets(6, 0, 0, 0));

        contentBox.getChildren().addAll(headerBox, audioPanel, gamePanel, aboutPanel, backBtn);
        getChildren().add(contentBox);
    }

    private Slider createPurpleSlider(double initialValue) {
        Slider slider = new Slider(0, 100, initialValue);
        slider.getStyleClass().add("slider-purple");
        slider.setPrefWidth(160);
        slider.setMaxWidth(200);
        return slider;
    }

    private HBox createControlRow(String icon, String title, String desc, Slider slider, Label percentBadge) {
        HBox row = new HBox(12);
        row.setAlignment(Pos.CENTER_LEFT);

        // Left Icon Badge
        StackPane iconBadge = createIconBadge(icon);

        // Title + Description
        VBox textGroup = new VBox(1);
        textGroup.setAlignment(Pos.CENTER_LEFT);

        Label titleLbl = new Label(title);
        titleLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        titleLbl.setTextFill(Color.WHITE);

        Label descLbl = new Label(desc);
        descLbl.setFont(Font.font("Segoe UI", 11));
        descLbl.setStyle("-fx-text-fill: #b39ddb;");

        textGroup.getChildren().addAll(titleLbl, descLbl);
        HBox.setHgrow(textGroup, Priority.ALWAYS);

        // Right Controls: Slider + Percentage + Speaker Icon
        Label speakerIcon = new Label("🔊");
        speakerIcon.setFont(Font.font("Segoe UI Emoji", 13));
        speakerIcon.setStyle("-fx-text-fill: #ce93d8;");

        HBox rightGroup = new HBox(10);
        rightGroup.setAlignment(Pos.CENTER_RIGHT);
        rightGroup.getChildren().addAll(slider, percentBadge, speakerIcon);

        row.getChildren().addAll(iconBadge, textGroup, rightGroup);
        return row;
    }

    private HBox createRowWithRightNode(String icon, String title, String desc, javafx.scene.Node rightNode) {
        HBox row = new HBox(12);
        row.setAlignment(Pos.CENTER_LEFT);

        // Left Icon Badge
        StackPane iconBadge = createIconBadge(icon);

        // Title + Description
        VBox textGroup = new VBox(1);
        textGroup.setAlignment(Pos.CENTER_LEFT);

        Label titleLbl = new Label(title);
        titleLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        titleLbl.setTextFill(Color.WHITE);

        Label descLbl = new Label(desc);
        descLbl.setFont(Font.font("Segoe UI", 11));
        descLbl.setStyle("-fx-text-fill: #b39ddb;");

        textGroup.getChildren().addAll(titleLbl, descLbl);
        HBox.setHgrow(textGroup, Priority.ALWAYS);

        row.getChildren().addAll(iconBadge, textGroup, rightNode);
        return row;
    }

    private StackPane createIconBadge(String icon) {
        StackPane badge = new StackPane();
        badge.setPrefSize(34, 34);
        badge.setMaxSize(34, 34);

        Rectangle bg = new Rectangle(34, 34);
        bg.setArcWidth(12);
        bg.setArcHeight(12);
        bg.setFill(Color.rgb(46, 20, 72, 0.75));
        bg.setStroke(Color.rgb(186, 104, 200, 0.45));
        bg.setStrokeWidth(1.2);

        Label iconLbl = new Label(icon);
        iconLbl.setFont(Font.font("Segoe UI Emoji", 16));

        badge.getChildren().addAll(bg, iconLbl);
        return badge;
    }

    private Label createPercentBadge(String text) {
        Label badge = new Label(text);
        badge.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        badge.setStyle("""
                -fx-text-fill: #f3e5f5;
                -fx-background-color: rgba(46, 20, 72, 0.85);
                -fx-border-color: rgba(186, 104, 200, 0.45);
                -fx-border-radius: 8px;
                -fx-background-radius: 8px;
                -fx-border-width: 1px;
                -fx-padding: 3 8 3 8;
                """);
        badge.setMinWidth(48);
        badge.setAlignment(Pos.CENTER);
        return badge;
    }

    private StackPane createModernToggle(boolean initial, java.util.function.Consumer<Boolean> onToggle) {
        StackPane toggle = new StackPane();
        toggle.setPrefSize(42, 22);
        toggle.setMaxSize(42, 22);
        toggle.setCursor(javafx.scene.Cursor.HAND);

        Rectangle track = new Rectangle(42, 22);
        track.setArcWidth(22);
        track.setArcHeight(22);

        Circle thumb = new Circle(8);
        thumb.setFill(Color.WHITE);
        thumb.setEffect(new DropShadow(4, Color.rgb(0, 0, 0, 0.4)));

        final boolean[] state = {initial};

        Runnable updateToggle = () -> {
            if (state[0]) {
                track.setFill(Color.rgb(186, 104, 200));
                track.setStroke(Color.rgb(225, 190, 231, 0.5));
                track.setStrokeWidth(1);
                thumb.setTranslateX(10);
            } else {
                track.setFill(Color.rgb(50, 30, 70));
                track.setStroke(Color.rgb(120, 80, 140, 0.4));
                track.setStrokeWidth(1);
                thumb.setTranslateX(-10);
            }
        };

        updateToggle.run();

        toggle.setOnMouseClicked(e -> {
            state[0] = !state[0];
            SoundManager.playSound("click.wav");

            TranslateTransition tt = new TranslateTransition(Duration.millis(110), thumb);
            tt.setToX(state[0] ? 10 : -10);
            tt.play();

            if (state[0]) {
                track.setFill(Color.rgb(186, 104, 200));
                track.setStroke(Color.rgb(225, 190, 231, 0.5));
            } else {
                track.setFill(Color.rgb(50, 30, 70));
                track.setStroke(Color.rgb(120, 80, 140, 0.4));
            }
            onToggle.accept(state[0]);
        });

        toggle.getChildren().addAll(track, thumb);
        return toggle;
    }
}