package ui.components;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class WinningLine extends Pane {

    private final Line line = new Line();

    public WinningLine() {

        setMouseTransparent(true);

        line.setStroke(Color.GOLD);
        line.setStrokeWidth(8);

        line.setVisible(false);

        getChildren().add(line);
    }

    public void showLine(
            double startX,
            double startY,
            double endX,
            double endY) {

        line.setVisible(true);

        line.setStartX(startX);
        line.setStartY(startY);

        // Start collapsed
        line.setEndX(startX);
        line.setEndY(startY);

        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(400),
                        new KeyValue(line.endXProperty(), endX),
                        new KeyValue(line.endYProperty(), endY)
                )
        );

        timeline.play();
    }

    public void hideLine() {

        line.setVisible(false);
    }
}