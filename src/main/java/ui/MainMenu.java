package ui;

import agstudios.audio.MusicManager;
import agstudios.audio.SoundManager;
import agstudios.utils.ScreenTransition;
import game.GameMode;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import ui.components.GameBackground;
import ui.components.GameButton;
import ui.components.TitleLabel;

public class MainMenu extends StackPane {

    public MainMenu() {
        // Apply stylesheet
        try {
            var css = getClass().getResource("/css/style.css");
            if (css != null) {
                getStylesheets().add(css.toExternalForm());
            }
        } catch (Exception ignored) {}

        // Background
        GameBackground background = new GameBackground("/images/bg_main_menu.jpg");
        getChildren().add(background);

        MusicManager.playMusic("loop.wav");

        // Main Layout Structure
        BorderPane layout = new BorderPane();
        layout.setPickOnBounds(false);

        // Center Content: Title + Menu Buttons
        VBox centerBox = new VBox(22);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setMaxWidth(400);

        // Title
        TitleLabel title = new TitleLabel();
        VBox.setMargin(title, new Insets(0, 0, 10, 0));

        // Menu Buttons
        GameButton friendBtn = createMenuButton("👥", "PLAYER VS PLAYER", () -> {
            SoundManager.playSound("click.wav");
            ScreenTransition.switchScreen(getScene(), new GameScreen(GameMode.FRIEND, null));
        });

        GameButton botBtn = createMenuButton("🤖", "PLAYER VS BOT", () -> {
            SoundManager.playSound("click.wav");
            ScreenTransition.switchScreen(getScene(), new BotDifficultyScreen());
        });

        GameButton statsBtn = createMenuButton("📊", "STATISTICS", () -> {
            SoundManager.playSound("click.wav");
            ScreenTransition.switchScreen(getScene(), new StatisticsScreen());
        });

        GameButton settingsBtn = createMenuButton("⚙", "SETTINGS", () -> {
            SoundManager.playSound("click.wav");
            ScreenTransition.switchScreen(getScene(), new SettingsScreen());
        });

        GameButton exitBtn = createMenuButton("🚪", "EXIT", () -> {
            SoundManager.playSound("click.wav");
            if (getScene() != null && getScene().getWindow() != null) {
                getScene().getWindow().hide();
            }
        });

        VBox buttonList = new VBox(14);
        buttonList.setAlignment(Pos.CENTER);
        buttonList.getChildren().addAll(friendBtn, botBtn, statsBtn, settingsBtn, exitBtn);

        centerBox.getChildren().addAll(title, buttonList);
        layout.setCenter(centerBox);

        // Footer Row
        HBox footer = new HBox();
        footer.setAlignment(Pos.CENTER_LEFT);
        footer.setPadding(new Insets(20, 35, 20, 35));

        // AG Studios Branding (Bottom Left)
        HBox studioBrand = new HBox(8);
        studioBrand.setAlignment(Pos.CENTER_LEFT);

        try {
            ImageView logoView = new ImageView(new Image(getClass().getResourceAsStream("/images/ag_logo.png")));
            logoView.setFitHeight(22);
            logoView.setPreserveRatio(true);
            studioBrand.getChildren().add(logoView);
        } catch (Exception ignored) {}

        Label studioText = new Label("AG STUDIOS");
        studioText.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        studioText.setStyle("-fx-text-fill: #00e5ff; -fx-letter-spacing: 2px;");
        DropShadow studioGlow = new DropShadow();
        studioGlow.setColor(Color.rgb(0, 229, 255, 0.6));
        studioGlow.setRadius(10);
        studioText.setEffect(studioGlow);
        studioBrand.getChildren().add(studioText);

        // Version Label (Bottom Right)
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label versionLabel = new Label("v1.1.0");
        versionLabel.setFont(Font.font("Segoe UI", 13));
        versionLabel.setStyle("-fx-text-fill: rgba(255, 255, 255, 0.45);");

        footer.getChildren().addAll(studioBrand, spacer, versionLabel);
        layout.setBottom(footer);

        getChildren().add(layout);
    }

    private GameButton createMenuButton(String icon, String text, Runnable action) {
        GameButton btn = new GameButton(icon, text, GameButton.Variant.MENU_CAPSULE);
        btn.setOnAction(e -> action.run());
        return btn;
    }
}