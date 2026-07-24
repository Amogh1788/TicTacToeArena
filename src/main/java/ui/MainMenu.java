package ui;

import agstudios.audio.MusicManager;
import agstudios.audio.SoundManager;
import agstudios.utils.ScreenTransition;
import game.GameMode;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import ui.components.GameButton;

public class MainMenu extends VBox {

    public MainMenu() {

        setAlignment(Pos.CENTER);
        setSpacing(28);
        setStyle("-fx-background-color: #050505;");
        MusicManager.playMusic("loop.wav");

        ImageView logo = new ImageView(
                new Image(
                        getClass().getResourceAsStream(
                                "/images/tictactoe_arena_icon.png"
                        )
                )
        );

        logo.setFitWidth(120);
        logo.setFitHeight(120);

        logo.setPreserveRatio(true);
        Label title = new Label("TIC TAC TOE ARENA");
        title.setFont(Font.font("Arial", 60));
        title.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        DropShadow glow = new DropShadow();
        glow.setRadius(15);
        glow.setColor(Color.web("#1E88E5"));

        title.setEffect(glow);
        Label subtitle = new Label("Classic • Local Multiplayer • AI");
        subtitle.setStyle("-fx-text-fill: #A9A9A9;");
        subtitle.setFont(Font.font("Arial",18));
        VBox titleBox = new VBox(4);

        titleBox.setAlignment(Pos.CENTER);

        titleBox.getChildren().addAll(
                title,
                subtitle
        );



        GameButton friendBtn = new GameButton("🎮 PLAY WITH FRIEND");
        friendBtn.setOnAction(e -> {

            SoundManager.playSound("click.wav");

            ScreenTransition.switchScreen(getScene(), new GameScreen(GameMode.FRIEND, null));

        });
        GameButton botBtn = new GameButton("🤖 PLAY WITH BOT");
        botBtn.setOnAction(e -> {

            SoundManager.playSound("click.wav");

            ScreenTransition.switchScreen(
                    getScene(),
                    new BotDifficultyScreen()
            );

        });
        GameButton settingsBtn = new GameButton("⚙ SETTINGS");
        settingsBtn.setOnAction(e -> {

            SoundManager.playSound("click.wav");

            ScreenTransition.switchScreen(
                    getScene(),
                    new SettingsScreen()
            );

        });
        GameButton statsBtn = new GameButton("📊 STATISTICS");
        statsBtn.setOnAction(e -> {

            SoundManager.playSound("click.wav");

            ScreenTransition.switchScreen(
                    getScene(),
                    new StatisticsScreen()
            );

        });
        GameButton exitBtn = new GameButton("🚪 EXIT");

        // Close the application gracefully
        exitBtn.setOnAction(e -> {

            SoundManager.playSound("click.wav");

            getScene().getWindow().hide();

        });
        getChildren().addAll(
                logo,
                titleBox,
                friendBtn,
                botBtn,
                settingsBtn,
                statsBtn,
                exitBtn
        );
    }
}