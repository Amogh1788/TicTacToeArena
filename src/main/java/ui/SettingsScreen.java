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

        // Centered Glass Card
        VBox cardPanel = new VBox(22);
        cardPanel.setAlignment(Pos.CENTER);
        cardPanel.setMaxWidth(560);
        cardPanel.setPrefWidth(540);
        cardPanel.setPadding(new Insets(30, 40, 30, 40));
        cardPanel.getStyleClass().add("glass-panel-purple");

        // Header Section
        Label gearIcon = new Label("⚙");
        gearIcon.setFont(Font.font("Segoe UI Emoji", 34));
        gearIcon.setTextFill(Color.rgb(186, 104, 200));

        DropShadow gearGlow = new DropShadow();
        gearGlow.setColor(Color.rgb(186, 104, 200, 0.8));
        gearGlow.setRadius(18);
        gearIcon.setEffect(gearGlow);

        Label title = new Label("SETTINGS");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 32));
        title.setStyle("-fx-text-fill: white; -fx-letter-spacing: 2px;");

        DropShadow titleGlow = new DropShadow();
        titleGlow.setColor(Color.rgb(171, 71, 188, 0.6));
        titleGlow.setRadius(16);
        title.setEffect(titleGlow);

        Label subtitle = new Label("Customize your experience");
        subtitle.setFont(Font.font("Segoe UI", 14));
        subtitle.setStyle("-fx-text-fill: #b0bec5;");

        VBox headerBox = new VBox(4);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.getChildren().addAll(gearIcon, title, subtitle);
        VBox.setMargin(headerBox, new Insets(0, 0, 8, 0));

        // =========================
        // AUDIO SECTION
        // =========================
        Label audioSectionTitle = new Label("AUDIO");
        audioSectionTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        audioSectionTitle.setStyle("-fx-text-fill: #ba68c8; -fx-letter-spacing: 1.5px;");

        // Music Volume
        double initialMusicVol = MusicManager.getVolume() * 100;
        Label musicPercent = createPercentBadge(String.format("%.0f%%", initialMusicVol));
        Slider musicSlider = new Slider(0, 100, initialMusicVol);
        HBox.setHgrow(musicSlider, Priority.ALWAYS);
        musicSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            musicPercent.setText(String.format("%.0f%%", newVal.doubleValue()));
            MusicManager.setVolume(newVal.doubleValue() / 100.0);
        });
        HBox musicRow = createSliderRow("🎵", musicSlider, musicPercent);

        // Sound Volume
        double initialSoundVol = SoundManager.getVolume() * 100;
        Label soundPercent = createPercentBadge(String.format("%.0f%%", initialSoundVol));
        Slider soundSlider = new Slider(0, 100, initialSoundVol);
        HBox.setHgrow(soundSlider, Priority.ALWAYS);
        soundSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            soundPercent.setText(String.format("%.0f%%", newVal.doubleValue()));
            SoundManager.setVolume(newVal.doubleValue() / 100.0);
        });
        HBox soundRow = createSliderRow("🔊", soundSlider, soundPercent);

        VBox audioGroup = new VBox(10);
        audioGroup.setAlignment(Pos.CENTER_LEFT);
        audioGroup.getChildren().addAll(audioSectionTitle, musicRow, soundRow);

        // =========================
        // GAME SECTION
        // =========================
        Label gameSectionTitle = new Label("GAME");
        gameSectionTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        gameSectionTitle.setStyle("-fx-text-fill: #ba68c8; -fx-letter-spacing: 1.5px;");

        HBox winAnimRow = createToggleRow("Show Win Animation", true, (enabled) -> {});
        HBox soundsToggleRow = createToggleRow("Enable Sounds", true, (enabled) -> {
            SoundManager.setVolume(enabled ? 1.0 : 0.0);
            soundSlider.setValue(enabled ? 100 : 0);
        });

        VBox gameGroup = new VBox(10);
        gameGroup.setAlignment(Pos.CENTER_LEFT);
        gameGroup.getChildren().addAll(gameSectionTitle, winAnimRow, soundsToggleRow);

        // =========================
        // BACK BUTTON
        // =========================
        GameButton backBtn = new GameButton("← BACK", GameButton.Variant.BACK);
        backBtn.setStyle("""
                -fx-background-color: rgba(38, 20, 58, 0.85);
                -fx-background-radius: 30px;
                -fx-border-color: rgba(186, 104, 200, 0.4);
                -fx-border-radius: 30px;
                -fx-border-width: 1.5px;
                -fx-text-fill: #f3e5f5;
                -fx-font-size: 15px;
                -fx-font-weight: bold;
                -fx-cursor: hand;
                -fx-padding: 8px 26px;
                """);

        backBtn.setOnAction(e -> {
            SoundManager.playSound("click.wav");
            ScreenTransition.switchScreen(getScene(), new MainMenu());
        });
        VBox.setMargin(backBtn, new Insets(10, 0, 0, 0));

        cardPanel.getChildren().addAll(headerBox, audioGroup, gameGroup, backBtn);
        getChildren().add(cardPanel);
    }

    private HBox createSliderRow(String icon, Slider slider, Label percentBadge) {
        HBox row = new HBox(14);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(6, 12, 6, 12));
        row.setStyle("""
                -fx-background-color: rgba(14, 10, 24, 0.6);
                -fx-background-radius: 14px;
                -fx-border-color: rgba(255, 255, 255, 0.06);
                -fx-border-radius: 14px;
                -fx-border-width: 1px;
                """);

        Label iconLbl = new Label(icon);
        iconLbl.setFont(Font.font("Segoe UI Emoji", 18));
        iconLbl.setMinWidth(24);

        row.getChildren().addAll(iconLbl, slider, percentBadge);
        return row;
    }

    private Label createPercentBadge(String text) {
        Label badge = new Label(text);
        badge.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        badge.setStyle("""
                -fx-text-fill: #00e5ff;
                -fx-background-color: rgba(0, 229, 255, 0.12);
                -fx-border-color: rgba(0, 229, 255, 0.4);
                -fx-border-radius: 8px;
                -fx-background-radius: 8px;
                -fx-border-width: 1px;
                -fx-padding: 3 10 3 10;
                """);
        badge.setMinWidth(55);
        badge.setAlignment(Pos.CENTER);
        return badge;
    }

    private HBox createToggleRow(String title, boolean initialValue, java.util.function.Consumer<Boolean> onToggle) {
        HBox row = new HBox(14);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(8, 14, 8, 14));
        row.setStyle("""
                -fx-background-color: rgba(14, 10, 24, 0.6);
                -fx-background-radius: 14px;
                -fx-border-color: rgba(255, 255, 255, 0.06);
                -fx-border-radius: 14px;
                -fx-border-width: 1px;
                """);

        Label titleLbl = new Label(title);
        titleLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        titleLbl.setTextFill(Color.WHITE);
        HBox.setHgrow(titleLbl, Priority.ALWAYS);
        titleLbl.setMaxWidth(Double.MAX_VALUE);

        StackPane toggle = createModernToggle(initialValue, onToggle);

        row.getChildren().addAll(titleLbl, toggle);
        return row;
    }

    private StackPane createModernToggle(boolean initial, java.util.function.Consumer<Boolean> onToggle) {
        StackPane toggle = new StackPane();
        toggle.setPrefSize(44, 24);
        toggle.setMaxSize(44, 24);
        toggle.setCursor(javafx.scene.Cursor.HAND);

        Rectangle track = new Rectangle(44, 24);
        track.setArcWidth(24);
        track.setArcHeight(24);

        Circle thumb = new Circle(9);
        thumb.setFill(Color.WHITE);
        thumb.setEffect(new DropShadow(5, Color.rgb(0, 0, 0, 0.4)));

        final boolean[] state = {initial};

        Runnable updateToggle = () -> {
            if (state[0]) {
                track.setFill(Color.rgb(76, 175, 80));
                thumb.setTranslateX(10);
            } else {
                track.setFill(Color.rgb(60, 65, 80));
                thumb.setTranslateX(-10);
            }
        };

        updateToggle.run();

        toggle.setOnMouseClicked(e -> {
            state[0] = !state[0];
            SoundManager.playSound("click.wav");

            TranslateTransition tt = new TranslateTransition(Duration.millis(120), thumb);
            tt.setToX(state[0] ? 10 : -10);
            tt.play();

            track.setFill(state[0] ? Color.rgb(76, 175, 80) : Color.rgb(60, 65, 80));
            onToggle.accept(state[0]);
        });

        toggle.getChildren().addAll(track, thumb);
        return toggle;
    }
}