package ui.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TitleLabel extends VBox {

    public TitleLabel() {
        setAlignment(Pos.CENTER);
        setSpacing(0);

        // Top line: "TIC TAC TOE"
        Label tttLabel = new Label("TIC TAC TOE");
        tttLabel.setFont(Font.font("Segoe UI", FontWeight.EXTRA_BOLD, 46));
        tttLabel.setStyle("""
                -fx-text-fill: linear-gradient(to bottom, #ffffff 0%, #e0f2fe 45%, #90caf9 70%, #42a5f5 100%);
                -fx-font-weight: 900;
                -fx-letter-spacing: 3px;
                """);

        DropShadow tttDepth = new DropShadow();
        tttDepth.setBlurType(BlurType.GAUSSIAN);
        tttDepth.setColor(Color.rgb(10, 25, 50, 0.95));
        tttDepth.setRadius(6);
        tttDepth.setSpread(0.6);
        tttDepth.setOffsetY(4);

        DropShadow tttGlow = new DropShadow();
        tttGlow.setBlurType(BlurType.GAUSSIAN);
        tttGlow.setColor(Color.rgb(0, 210, 255, 0.65));
        tttGlow.setRadius(22);
        tttGlow.setSpread(0.35);
        tttGlow.setInput(tttDepth);

        tttLabel.setEffect(tttGlow);

        // Bottom line: "ARENA"
        Label arenaLabel = new Label("ARENA");
        arenaLabel.setFont(Font.font("Segoe UI", FontWeight.EXTRA_BOLD, 36));
        arenaLabel.setStyle("""
                -fx-text-fill: linear-gradient(to bottom, #fff3e0 0%, #ffb74d 40%, #ff9800 70%, #e65100 100%);
                -fx-font-weight: 900;
                -fx-letter-spacing: 6px;
                """);

        DropShadow arenaDepth = new DropShadow();
        arenaDepth.setBlurType(BlurType.GAUSSIAN);
        arenaDepth.setColor(Color.rgb(40, 15, 0, 0.95));
        arenaDepth.setRadius(6);
        arenaDepth.setSpread(0.6);
        arenaDepth.setOffsetY(4);

        DropShadow arenaGlow = new DropShadow();
        arenaGlow.setBlurType(BlurType.GAUSSIAN);
        arenaGlow.setColor(Color.rgb(255, 152, 0, 0.65));
        arenaGlow.setRadius(24);
        arenaGlow.setSpread(0.35);
        arenaGlow.setInput(arenaDepth);

        arenaLabel.setEffect(arenaGlow);

        getChildren().addAll(tttLabel, arenaLabel);
    }
}
