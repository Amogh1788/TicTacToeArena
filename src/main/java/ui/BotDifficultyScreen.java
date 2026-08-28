package ui;

import agstudios.audio.SoundManager;
import agstudios.utils.ScreenTransition;
import game.GameMode;
import game.bot.BotDifficulty;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
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

public class BotDifficultyScreen extends StackPane {

    public BotDifficultyScreen() {
        // Load stylesheet
        try {
            var css = getClass().getResource("/css/style.css");
            if (css != null) {
                getStylesheets().add(css.toExternalForm());
            }
        } catch (Exception ignored) {}

        // Background
        GameBackground background = new GameBackground("/images/bg_difficulty.jpg");
        getChildren().add(background);

        // Center Content Box
        VBox contentBox = new VBox(22);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setMaxWidth(520);
        contentBox.setPadding(new Insets(30, 20, 30, 20));

        // Crown & Header
        Label crownLabel = new Label("👑");
        crownLabel.setFont(Font.font("Segoe UI Emoji", 28));

        Label title = new Label("CHOOSE DIFFICULTY");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 36));
        title.setStyle("-fx-text-fill: white; -fx-letter-spacing: 2px;");

        DropShadow titleGlow = new DropShadow();
        titleGlow.setColor(Color.rgb(0, 229, 255, 0.5));
        titleGlow.setRadius(16);
        title.setEffect(titleGlow);

        Label subtitle = new Label("How strong should your opponent be?");
        subtitle.setFont(Font.font("Segoe UI", 15));
        subtitle.setStyle("-fx-text-fill: #94a3b8;");

        VBox headerBox = new VBox(6);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.getChildren().addAll(crownLabel, title, subtitle);
        VBox.setMargin(headerBox, new Insets(0, 0, 10, 0));

        // 3 Difficulty Cards
        HBox easyCard = createDifficultyCard(
                "😊",
                "EASY",
                "Perfect for beginners.",
                1,
                Color.web("#4caf50"),
                "diff-card-easy",
                () -> selectDifficulty(BotDifficulty.EASY)
        );

        HBox mediumCard = createDifficultyCard(
                "🤔",
                "MEDIUM",
                "A balanced challenge.",
                2,
                Color.web("#ffc107"),
                "diff-card-medium",
                () -> selectDifficulty(BotDifficulty.MEDIUM)
        );

        HBox impossibleCard = createDifficultyCard(
                "😈",
                "IMPOSSIBLE",
                "Only for true champions.",
                3,
                Color.web("#f44336"),
                "diff-card-impossible",
                () -> selectDifficulty(BotDifficulty.IMPOSSIBLE)
        );

        VBox cardsList = new VBox(14);
        cardsList.setAlignment(Pos.CENTER);
        cardsList.getChildren().addAll(easyCard, mediumCard, impossibleCard);

        // Back Button
        GameButton backBtn = new GameButton("← BACK", GameButton.Variant.BACK);
        backBtn.setOnAction(e -> {
            SoundManager.playSound("click.wav");
            ScreenTransition.switchScreen(getScene(), new MainMenu());
        });
        VBox.setMargin(backBtn, new Insets(10, 0, 0, 0));

        contentBox.getChildren().addAll(headerBox, cardsList, backBtn);
        getChildren().add(contentBox);
    }

    private HBox createDifficultyCard(
            String icon,
            String name,
            String desc,
            int barsFilled,
            Color accentColor,
            String cssClass,
            Runnable onSelect) {

        HBox card = new HBox(16);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPadding(new Insets(16, 22, 16, 22));
        card.setPrefWidth(480);
        card.setMaxWidth(500);
        card.getStyleClass().add(cssClass);

        // Left Icon Badge
        StackPane iconBadge = new StackPane();
        Circle badgeBg = new Circle(24);
        badgeBg.setFill(Color.rgb((int)(accentColor.getRed()*255), (int)(accentColor.getGreen()*255), (int)(accentColor.getBlue()*255), 0.22));
        badgeBg.setStroke(accentColor);
        badgeBg.setStrokeWidth(1.5);

        Label iconLbl = new Label(icon);
        iconLbl.setFont(Font.font("Segoe UI Emoji", 22));
        iconBadge.getChildren().addAll(badgeBg, iconLbl);

        // Middle Text Info
        VBox textGroup = new VBox(3);
        textGroup.setAlignment(Pos.CENTER_LEFT);

        Label nameLbl = new Label(name);
        nameLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 19));
        nameLbl.setTextFill(accentColor);

        Label descLbl = new Label(desc);
        descLbl.setFont(Font.font("Segoe UI", 13));
        descLbl.setStyle("-fx-text-fill: #cbd5e1;");

        textGroup.getChildren().addAll(nameLbl, descLbl);
        HBox.setHgrow(textGroup, Priority.ALWAYS);

        // Right Signal Bars Meter (3 bars)
        HBox signalMeter = createSignalMeter(barsFilled, accentColor);

        card.getChildren().addAll(iconBadge, textGroup, signalMeter);

        // Hover & Click Animations
        ScaleTransition grow = new ScaleTransition(Duration.millis(140), card);
        grow.setToX(1.03);
        grow.setToY(1.03);

        ScaleTransition shrink = new ScaleTransition(Duration.millis(140), card);
        shrink.setToX(1.0);
        shrink.setToY(1.0);

        card.setOnMouseEntered(e -> grow.playFromStart());
        card.setOnMouseExited(e -> shrink.playFromStart());

        card.setOnMouseClicked(e -> {
            SoundManager.playSound("click.wav");
            onSelect.run();
        });

        return card;
    }

    private HBox createSignalMeter(int filledCount, Color activeColor) {
        HBox meter = new HBox(4);
        meter.setAlignment(Pos.BOTTOM_CENTER);
        meter.setPrefHeight(24);

        int[] heights = {10, 16, 22};
        for (int i = 0; i < 3; i++) {
            Rectangle bar = new Rectangle(5, heights[i]);
            bar.setArcWidth(3);
            bar.setArcHeight(3);

            if (i < filledCount) {
                bar.setFill(activeColor);
                DropShadow barGlow = new DropShadow();
                barGlow.setColor(activeColor);
                barGlow.setRadius(8);
                bar.setEffect(barGlow);
            } else {
                bar.setFill(Color.rgb(80, 90, 110, 0.45));
            }
            meter.getChildren().add(bar);
        }
        return meter;
    }

    private void selectDifficulty(BotDifficulty difficulty) {
        ScreenTransition.switchScreen(
                getScene(),
                new GameScreen(GameMode.BOT, difficulty)
        );
    }
}