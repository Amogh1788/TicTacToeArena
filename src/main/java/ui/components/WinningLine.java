package ui.components;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.util.Duration;

public class WinningLine extends Pane {

    private final Line glowLine = new Line();
    private final Line coreLine = new Line();

    public WinningLine() {
        setMouseTransparent(true);

        glowLine.setStroke(Color.rgb(0, 229, 255));
        glowLine.setStrokeWidth(9);
        glowLine.setStrokeLineCap(StrokeLineCap.ROUND);
        glowLine.setVisible(false);

        DropShadow glow = new DropShadow();
        glow.setBlurType(BlurType.GAUSSIAN);
        glow.setColor(Color.rgb(0, 229, 255, 0.95));
        glow.setRadius(28);
        glow.setSpread(0.45);
        glowLine.setEffect(glow);

        coreLine.setStroke(Color.rgb(240, 255, 255));
        coreLine.setStrokeWidth(3.5);
        coreLine.setStrokeLineCap(StrokeLineCap.ROUND);
        coreLine.setVisible(false);

        getChildren().addAll(glowLine, coreLine);
    }

    public void showLine(
            double startX,
            double startY,
            double endX,
            double endY) {

        glowLine.setVisible(true);
        coreLine.setVisible(true);

        glowLine.setStartX(startX);
        glowLine.setStartY(startY);
        glowLine.setEndX(startX);
        glowLine.setEndY(startY);

        coreLine.setStartX(startX);
        coreLine.setStartY(startY);
        coreLine.setEndX(startX);
        coreLine.setEndY(startY);

        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(350),
                        new KeyValue(glowLine.endXProperty(), endX),
                        new KeyValue(glowLine.endYProperty(), endY),
                        new KeyValue(coreLine.endXProperty(), endX),
                        new KeyValue(coreLine.endYProperty(), endY)
                )
        );

        timeline.play();
    }

    public void hideLine() {
        glowLine.setVisible(false);
        coreLine.setVisible(false);
    }
}