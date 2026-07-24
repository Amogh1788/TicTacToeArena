package ui;

import agstudios.audio.SoundManager;
import agstudios.utils.ScreenTransition;
import game.GameStats;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import ui.components.GameButton;

public class StatisticsScreen extends StackPane {

    public StatisticsScreen() {

        setStyle("-fx-background-color: #050505;");

        BorderPane panel = new BorderPane();

        panel.setMaxWidth(900);
        panel.setPrefWidth(900);

        panel.setMaxHeight(600);
        panel.setPrefHeight(600);

        panel.setStyle("""
                        -fx-background-color:#111111;
                        -fx-background-radius:20;
                        -fx-border-color:#2d2d2d;
                        -fx-border-radius:20;
                        -fx-border-width:2;
                        -fx-padding:35;
                        """);

        Label title = new Label("STATISTICS");
        title.setFont(Font.font("Arial", 44));
        title.setStyle("""
    -fx-text-fill: white;
    -fx-font-weight: bold;
""");

        BorderPane.setAlignment(title, Pos.CENTER);
        BorderPane.setMargin(title, new Insets(0,0,25,0));

        panel.setTop(title);

        HBox cards = new HBox(25);
        cards.setAlignment(Pos.CENTER);
        cards.setMaxHeight(220);

// ================= Overall =================

        VBox overallCard = createCard(
                "🎮 Overall",
                "Games Played : " + GameStats.getGamesPlayed()
        );

// ================= Friend =================

        VBox friendCard = createCard(
                "👥 Friend Mode",
                "X Wins : " + GameStats.getXWins()
                        + "\nO Wins : " + GameStats.getOWins()
                        + "\nDraws : " + GameStats.getFriendDraws()
        );

// ================= Bot =================

        VBox botCard = createCard(
                "🤖 Bot Mode",
                "Player Wins : " + GameStats.getPlayerWins()
                        + "\nBot Wins : " + GameStats.getBotWins()
                        + "\nDraws : " + GameStats.getBotDraws()
        );



        cards.getChildren().addAll(
                overallCard,
                friendCard,
                botCard
        );

        panel.setCenter(cards);

        GameButton resetBtn = new GameButton("🔄 RESET STATS");

        resetBtn.setOnAction(e -> {

            SoundManager.playSound("click.wav");

            GameStats.reset();

            ScreenTransition.switchScreen(
                    getScene(),
                    new StatisticsScreen()
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
        HBox buttonBox = new HBox(20);

        buttonBox.setAlignment(Pos.CENTER);

        buttonBox.getChildren().addAll(
                resetBtn,
                backBtn
        );

        panel.setBottom(buttonBox);
        getChildren().add(panel);
    }
    private VBox createCard(String title, String content) {

        Label heading = new Label(title);
        heading.setFont(Font.font("Arial", 22));
        heading.setStyle("-fx-text-fill:white; -fx-font-weight:bold;");

        Label body = new Label(content);
        body.setFont(Font.font("Arial", 18));
        body.setStyle("-fx-text-fill:white;");

        VBox box = new VBox(15);

        box.setAlignment(Pos.TOP_LEFT);

        box.setPrefSize(240, 220);

        box.setMaxSize(240, 220);

        box.setStyle("""
            -fx-background-color:#1a1a1a;
            -fx-background-radius:15;
            -fx-border-color:#2d2d2d;
            -fx-border-radius:15;
            -fx-padding:20;
            """);

        box.getChildren().addAll(
                heading,
                body
        );

        return box;
    }
}