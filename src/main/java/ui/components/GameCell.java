package ui.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.animation.ScaleTransition;
import javafx.animation.Animation;
import javafx.util.Duration;

public class GameCell extends StackPane {

    private final Rectangle background;
    private final Label symbolLabel;
    private ScaleTransition winnerAnimation;
    private boolean occupied = false;

    public GameCell() {

        setPrefSize(140, 140);
        setMinSize(140, 140);
        setMaxSize(140, 140);

        background = new Rectangle(140, 140);
        background.setArcWidth(24);
        background.setArcHeight(24);

        background.setFill(Color.web("#101010"));
        background.setStroke(Color.web("#1E88E5"));
        background.setStrokeWidth(2);

        symbolLabel = new Label("");
        symbolLabel.setFont(Font.font("Arial", 72));
        symbolLabel.setTextFill(Color.WHITE);

        setAlignment(Pos.CENTER);

        getChildren().addAll(background, symbolLabel);

        setOnMouseEntered(e -> {

            if (!occupied) {
                background.setFill(Color.web("#181818"));
                background.setStroke(Color.web("#42A5F5"));
            }

        });

        setOnMouseExited(e -> {

            if (!occupied) {
                background.setFill(Color.web("#101010"));
                background.setStroke(Color.web("#1E88E5"));
            }

        });
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setSymbol(String symbol) {

        occupied = true;

        symbolLabel.setScaleX(0);
        symbolLabel.setScaleY(0);

        symbolLabel.setText(symbol);

        if (symbol.equals("X")) {
            symbolLabel.setTextFill(Color.web("#42A5F5"));
        } else {
            symbolLabel.setTextFill(Color.web("#EF5350"));
        }
        symbolLabel.setScaleX(0);
        symbolLabel.setScaleY(0);

        ScaleTransition pop1 = new ScaleTransition(Duration.millis(90), symbolLabel);
        pop1.setToX(1.15);
        pop1.setToY(1.15);

        ScaleTransition pop2 = new ScaleTransition(Duration.millis(70), symbolLabel);
        pop2.setToX(0.92);
        pop2.setToY(0.92);

        ScaleTransition pop3 = new ScaleTransition(Duration.millis(60), symbolLabel);
        pop3.setToX(1.0);
        pop3.setToY(1.0);

        pop1.setOnFinished(e -> pop2.play());
        pop2.setOnFinished(e -> pop3.play());

        pop1.play();
    }

    public void resetCell() {

        occupied = false;

        symbolLabel.setText("");

        removeHighlight();
    }
    public void highlightWinner() {

        background.setFill(Color.web("#2A2A2A"));
        background.setStroke(Color.GOLD);
        background.setStrokeWidth(5);

        winnerAnimation = new ScaleTransition(Duration.millis(700), this);

        winnerAnimation.setFromX(1.0);
        winnerAnimation.setFromY(1.0);

        winnerAnimation.setToX(1.08);
        winnerAnimation.setToY(1.08);

        winnerAnimation.setAutoReverse(true);
        winnerAnimation.setCycleCount(Animation.INDEFINITE);

        winnerAnimation.play();
    }
    public void removeHighlight() {

        if (winnerAnimation != null) {
            winnerAnimation.stop();
        }

        setScaleX(1);
        setScaleY(1);

        background.setFill(Color.web("#101010"));
        background.setStroke(Color.web("#1E88E5"));
        background.setStrokeWidth(2);
    }
}