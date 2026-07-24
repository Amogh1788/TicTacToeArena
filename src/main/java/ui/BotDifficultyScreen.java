package ui;

import agstudios.audio.SoundManager;
import agstudios.utils.ScreenTransition;
import game.GameMode;
import game.bot.BotDifficulty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import ui.components.GameButton;

public class BotDifficultyScreen extends VBox {

    public BotDifficultyScreen() {

        setAlignment(Pos.CENTER);
        setSpacing(20);
        setStyle("-fx-background-color: #050505;");

        Label title = new Label("BOT DIFFICULTY");
        title.setFont(Font.font("Arial", 40));
        title.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        GameButton easyBtn = new GameButton("🟢 EASY");

        easyBtn.setOnAction(e -> {

            SoundManager.playSound("click.wav");

            ScreenTransition.switchScreen(
                    getScene(),
                    new GameScreen(GameMode.BOT, BotDifficulty.EASY)
            );

        });

        GameButton mediumBtn = new GameButton("🟡 MEDIUM");

        mediumBtn.setOnAction(e -> {

            SoundManager.playSound("click.wav");

            ScreenTransition.switchScreen(
                    getScene(),
                    new GameScreen(GameMode.BOT, BotDifficulty.MEDIUM)
            );

        });

        GameButton impossibleBtn = new GameButton("🔴 IMPOSSIBLE");

        impossibleBtn.setOnAction(e -> {

            SoundManager.playSound("click.wav");

            ScreenTransition.switchScreen(
                    getScene(),
                    new GameScreen(GameMode.BOT, BotDifficulty.IMPOSSIBLE)
            );

        });

        GameButton backBtn = new GameButton("⬅ BACK");

        backBtn.setOnAction(e -> {

            SoundManager.playSound("click.wav");

            ScreenTransition.switchScreen(
                    getScene(),
                    new MainMenu()
            );

        });

        getChildren().addAll(
                title,
                easyBtn,
                mediumBtn,
                impossibleBtn,
                backBtn
        );
    }
}