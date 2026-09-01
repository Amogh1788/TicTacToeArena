package ui;

import agstudios.audio.SoundManager;
import agstudios.utils.ScreenTransition;
import game.GameStats;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import ui.components.GameBackground;
import ui.components.GameButton;

public class StatisticsScreen extends StackPane {

    private final Parent returnScreen;

    public StatisticsScreen() {
        this(null);
    }

    public StatisticsScreen(Parent returnScreen) {
        this.returnScreen = returnScreen;

        // Load stylesheet
        try {
            var css = getClass().getResource("/css/style.css");
            if (css != null) {
                getStylesheets().add(css.toExternalForm());
            }
        } catch (Exception ignored) {}

        // Background
        GameBackground background = new GameBackground("/images/bg_statistics.jpg");
        getChildren().add(background);

        // Center Dashboard Container
        VBox dashboard = new VBox(22);
        dashboard.setAlignment(Pos.CENTER);
        dashboard.setMaxWidth(680);
        dashboard.setPadding(new Insets(25, 30, 25, 30));

        // Header Section
        Label title = new Label("STATISTICS");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 36));
        title.setStyle("-fx-text-fill: white; -fx-letter-spacing: 2px;");

        DropShadow titleGlow = new DropShadow();
        titleGlow.setColor(Color.rgb(0, 229, 255, 0.6));
        titleGlow.setRadius(18);
        title.setEffect(titleGlow);

        Label subtitle = new Label("Your game journey");
        subtitle.setFont(Font.font("Segoe UI", 15));
        subtitle.setStyle("-fx-text-fill: #94a3b8;");

        VBox headerBox = new VBox(4);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.getChildren().addAll(title, subtitle);
        VBox.setMargin(headerBox, new Insets(0, 0, 6, 0));

        // ==========================================
        // Top 3 Stat Cards (Total, Wins, Win Rate)
        // ==========================================
        int totalGames = GameStats.getGamesPlayed();
        int totalWins = GameStats.getPlayerWins() + GameStats.getXWins();
        int winRate = totalGames > 0 ? (int) Math.round(((double) totalWins / totalGames) * 100) : 0;

        HBox topCardsRow = new HBox(16);
        topCardsRow.setAlignment(Pos.CENTER);

        VBox totalCard = createSummaryCard("🎮", String.valueOf(totalGames), "TOTAL GAMES", "stat-card-total", Color.rgb(186, 104, 200));
        VBox winsCard = createSummaryCard("🏆", String.valueOf(totalWins), "WINS", "stat-card-wins", Color.rgb(255, 193, 7));
        VBox rateCard = createSummaryCard("🎯", winRate + "%", "WIN RATE", "stat-card-rate", Color.rgb(0, 229, 255));

        HBox.setHgrow(totalCard, Priority.ALWAYS);
        HBox.setHgrow(winsCard, Priority.ALWAYS);
        HBox.setHgrow(rateCard, Priority.ALWAYS);

        topCardsRow.getChildren().addAll(totalCard, winsCard, rateCard);

        // ==========================================
        // Bottom 2 Detail Panels (Vs Bot, Vs Player)
        // ==========================================
        HBox detailRow = new HBox(16);
        detailRow.setAlignment(Pos.CENTER);

        VBox vsBotPanel = createDetailPanel(
                "VS BOT",
                new String[][]{
                        {"Player Wins", String.valueOf(GameStats.getPlayerWins()), "#4caf50"},
                        {"Bot Wins", String.valueOf(GameStats.getBotWins()), "#ef5350"},
                        {"Draws", String.valueOf(GameStats.getBotDraws()), "#ff9800"}
                }
        );

        VBox vsPlayerPanel = createDetailPanel(
                "VS PLAYER",
                new String[][]{
                        {"X Wins", String.valueOf(GameStats.getXWins()), "#00e5ff"},
                        {"O Wins", String.valueOf(GameStats.getOWins()), "#ff5252"},
                        {"Draws", String.valueOf(GameStats.getFriendDraws()), "#ff9800"}
                }
        );

        HBox.setHgrow(vsBotPanel, Priority.ALWAYS);
        HBox.setHgrow(vsPlayerPanel, Priority.ALWAYS);

        detailRow.getChildren().addAll(vsBotPanel, vsPlayerPanel);

        // ==========================================
        // Bottom Action Buttons
        // ==========================================
        GameButton resetBtn = new GameButton("🔄 RESET STATS", GameButton.Variant.DANGER);
        resetBtn.setOnAction(e -> {
            SoundManager.playSound("click.wav");
            GameStats.reset();
            ScreenTransition.switchScreen(getScene(), new StatisticsScreen(returnScreen));
        });

        GameButton backBtn = new GameButton("← BACK", GameButton.Variant.BACK);
        backBtn.setOnAction(e -> {
            SoundManager.playSound("click.wav");
            ScreenTransition.switchScreen(getScene(), returnScreen != null ? returnScreen : new MainMenu());
        });

        HBox buttonBox = new HBox(16);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(resetBtn, backBtn);
        VBox.setMargin(buttonBox, new Insets(10, 0, 0, 0));

        dashboard.getChildren().addAll(headerBox, topCardsRow, detailRow, buttonBox);
        getChildren().add(dashboard);
    }

    private VBox createSummaryCard(String icon, String value, String label, String cssClass, Color accentColor) {
        VBox card = new VBox(6);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(18, 14, 18, 14));
        card.setPrefWidth(180);
        card.getStyleClass().add(cssClass);

        Label iconLbl = new Label(icon);
        iconLbl.setFont(Font.font("Segoe UI Emoji", 26));

        Label valLbl = new Label(value);
        valLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        valLbl.setTextFill(Color.WHITE);

        Label nameLbl = new Label(label);
        nameLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 11));
        nameLbl.setTextFill(accentColor);
        nameLbl.setStyle("-fx-letter-spacing: 1px;");

        card.getChildren().addAll(iconLbl, valLbl, nameLbl);
        return card;
    }

    private VBox createDetailPanel(String title, String[][] rows) {
        VBox panel = new VBox(12);
        panel.setAlignment(Pos.TOP_CENTER);
        panel.setPadding(new Insets(18, 22, 18, 22));
        panel.setPrefWidth(280);
        panel.getStyleClass().add("stat-detail-panel");

        Label heading = new Label(title);
        heading.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));
        heading.setStyle("-fx-text-fill: #90caf9; -fx-letter-spacing: 1.5px;");
        VBox.setMargin(heading, new Insets(0, 0, 4, 0));

        panel.getChildren().add(heading);

        for (String[] row : rows) {
            HBox itemRow = new HBox();
            itemRow.setAlignment(Pos.CENTER_LEFT);

            Label nameLbl = new Label(row[0]);
            nameLbl.setFont(Font.font("Segoe UI", 14));
            nameLbl.setStyle("-fx-text-fill: #cbd5e1;");

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            Label valLbl = new Label(row[1]);
            valLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));
            valLbl.setStyle("-fx-text-fill: " + row[2] + ";");

            itemRow.getChildren().addAll(nameLbl, spacer, valLbl);
            panel.getChildren().add(itemRow);
        }

        return panel;
    }
}