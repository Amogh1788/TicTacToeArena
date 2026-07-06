package ui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import ui.components.GameButton;
import agstudios.utils.ScreenTransition;

public class MainMenu extends VBox {

    public MainMenu() {

        setAlignment(Pos.CENTER);
        setSpacing(20);
        setStyle("-fx-background-color: #050505;");

        Label title = new Label("TIC TAC TOE\nARENA");
        title.setFont(Font.font("Arial", 42));
        title.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        GameButton friendBtn = new GameButton("🎮 PLAY WITH FRIEND");
        friendBtn.setOnAction(e -> {
            ScreenTransition.switchScreen(getScene(), new GameScreen());
        });
        GameButton botBtn = new GameButton("🤖 PLAY WITH BOT");
        botBtn.setOnAction(e -> System.out.println("Bot Mode - Coming Soon"));
        GameButton settingsBtn = new GameButton("⚙ SETTINGS");
        settingsBtn.setOnAction(e -> System.out.println("Settings - Coming Soon"));
        GameButton statsBtn = new GameButton("📊 STATISTICS");
        statsBtn.setOnAction(e -> System.out.println("Statistics - Coming Soon"));
        GameButton exitBtn = new GameButton("🚪 EXIT");

        // Close the application gracefully
        exitBtn.setOnAction(e -> {
            getScene().getWindow().hide();
        });

        getChildren().addAll(
                title,
                friendBtn,
                botBtn,
                settingsBtn,
                statsBtn,
                exitBtn
        );
    }
}