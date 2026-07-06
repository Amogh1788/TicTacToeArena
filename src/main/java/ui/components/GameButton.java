package ui.components;

import javafx.animation.ScaleTransition;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class GameButton extends Button {

    public GameButton(String text) {

        super(text);

        setPrefSize(320, 55);

        setStyle("""
                -fx-background-color: #1E88E5;
                -fx-text-fill: white;
                -fx-font-size: 18px;
                -fx-font-weight: bold;
                -fx-background-radius: 12;
                -fx-cursor: hand;
                """);

        ScaleTransition grow = new ScaleTransition(Duration.millis(150), this);
        grow.setToX(1.05);
        grow.setToY(1.05);

        ScaleTransition shrink = new ScaleTransition(Duration.millis(150), this);
        shrink.setToX(1.0);
        shrink.setToY(1.0);

        setOnMouseEntered(e -> {
            setStyle("""
                    -fx-background-color: #42A5F5;
                    -fx-text-fill: white;
                    -fx-font-size: 18px;
                    -fx-font-weight: bold;
                    -fx-background-radius: 12;
                    -fx-cursor: hand;
                    """);
            grow.playFromStart();
        });

        setOnMouseExited(e -> {
            setStyle("""
                    -fx-background-color: #1E88E5;
                    -fx-text-fill: white;
                    -fx-font-size: 18px;
                    -fx-font-weight: bold;
                    -fx-background-radius: 12;
                    -fx-cursor: hand;
                    """);
            shrink.playFromStart();
        });

        setOnMousePressed(e -> {
            setScaleX(0.97);
            setScaleY(0.97);
        });

        setOnMouseReleased(e -> {
            setScaleX(1.05);
            setScaleY(1.05);
        });
    }
}