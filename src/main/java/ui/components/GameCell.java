package ui.components;

import javafx.animation.Animation;
import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.util.Duration;

public class GameCell extends StackPane {

    private final Rectangle background;
    private final StackPane symbolContainer;
    private ScaleTransition winnerAnimation;
    private boolean occupied = false;
    private String currentSymbol = null;

    public GameCell() {
        setPrefSize(130, 130);
        setMinSize(110, 110);
        setMaxSize(140, 140);

        background = new Rectangle(130, 130);
        background.setArcWidth(22);
        background.setArcHeight(22);
        background.setFill(Color.rgb(14, 20, 34, 0.82));
        background.setStroke(Color.rgb(255, 255, 255, 0.12));
        background.setStrokeWidth(1.8);

        // Bind background size to cell size
        widthProperty().addListener((obs, oldW, newW) -> background.setWidth(newW.doubleValue()));
        heightProperty().addListener((obs, oldH, newH) -> background.setHeight(newH.doubleValue()));

        DropShadow cellShadow = new DropShadow();
        cellShadow.setColor(Color.rgb(0, 0, 0, 0.4));
        cellShadow.setRadius(10);
        cellShadow.setOffsetY(3);
        background.setEffect(cellShadow);

        symbolContainer = new StackPane();
        symbolContainer.setAlignment(Pos.CENTER);

        setAlignment(Pos.CENTER);
        getChildren().addAll(background, symbolContainer);

        setOnMouseEntered(e -> {
            if (!occupied) {
                background.setFill(Color.rgb(22, 32, 54, 0.9));
                background.setStroke(Color.rgb(0, 229, 255, 0.5));
                DropShadow hoverGlow = new DropShadow();
                hoverGlow.setColor(Color.rgb(0, 229, 255, 0.35));
                hoverGlow.setRadius(16);
                background.setEffect(hoverGlow);
            }
        });

        setOnMouseExited(e -> {
            if (!occupied) {
                background.setFill(Color.rgb(14, 20, 34, 0.82));
                background.setStroke(Color.rgb(255, 255, 255, 0.12));
                DropShadow normalShadow = new DropShadow();
                normalShadow.setColor(Color.rgb(0, 0, 0, 0.4));
                normalShadow.setRadius(10);
                normalShadow.setOffsetY(3);
                background.setEffect(normalShadow);
            }
        });
    }

    public boolean isOccupied() {
        return occupied;
    }

    public String getSymbol() {
        return currentSymbol;
    }

    public void setSymbol(String symbol) {
        occupied = true;
        currentSymbol = symbol;
        symbolContainer.getChildren().clear();

        if ("X".equalsIgnoreCase(symbol)) {
            Group xShape = createXShape();
            symbolContainer.getChildren().add(xShape);
        } else if ("O".equalsIgnoreCase(symbol)) {
            Group oShape = createOShape();
            symbolContainer.getChildren().add(oShape);
        }

        // Pop-in scale animation
        symbolContainer.setScaleX(0);
        symbolContainer.setScaleY(0);

        ScaleTransition pop1 = new ScaleTransition(Duration.millis(120), symbolContainer);
        pop1.setToX(1.18);
        pop1.setToY(1.18);

        ScaleTransition pop2 = new ScaleTransition(Duration.millis(90), symbolContainer);
        pop2.setToX(0.94);
        pop2.setToY(0.94);

        ScaleTransition pop3 = new ScaleTransition(Duration.millis(80), symbolContainer);
        pop3.setToX(1.0);
        pop3.setToY(1.0);

        pop1.setOnFinished(e -> pop2.play());
        pop2.setOnFinished(e -> pop3.play());
        pop1.play();
    }

    private Group createXShape() {
        Group group = new Group();
        double size = 32;

        Line line1 = new Line(-size, -size, size, size);
        line1.setStroke(Color.rgb(0, 229, 255));
        line1.setStrokeWidth(12);
        line1.setStrokeLineCap(StrokeLineCap.ROUND);

        Line line2 = new Line(size, -size, -size, size);
        line2.setStroke(Color.rgb(0, 229, 255));
        line2.setStrokeWidth(12);
        line2.setStrokeLineCap(StrokeLineCap.ROUND);

        // Core bright highlight line
        Line core1 = new Line(-size, -size, size, size);
        core1.setStroke(Color.rgb(220, 250, 255));
        core1.setStrokeWidth(5);
        core1.setStrokeLineCap(StrokeLineCap.ROUND);

        Line core2 = new Line(size, -size, -size, size);
        core2.setStroke(Color.rgb(220, 250, 255));
        core2.setStrokeWidth(5);
        core2.setStrokeLineCap(StrokeLineCap.ROUND);

        DropShadow glow = new DropShadow();
        glow.setBlurType(BlurType.GAUSSIAN);
        glow.setColor(Color.rgb(0, 229, 255, 0.85));
        glow.setRadius(24);
        glow.setSpread(0.4);

        group.getChildren().addAll(line1, line2, core1, core2);
        group.setEffect(glow);
        return group;
    }

    private Group createOShape() {
        Group group = new Group();
        double radius = 34;

        Circle outer = new Circle(radius);
        outer.setFill(Color.TRANSPARENT);
        outer.setStroke(Color.rgb(255, 82, 82));
        outer.setStrokeWidth(12);

        // Core bright highlight ring
        Circle core = new Circle(radius);
        core.setFill(Color.TRANSPARENT);
        core.setStroke(Color.rgb(255, 210, 210));
        core.setStrokeWidth(4.5);

        DropShadow glow = new DropShadow();
        glow.setBlurType(BlurType.GAUSSIAN);
        glow.setColor(Color.rgb(255, 82, 82, 0.85));
        glow.setRadius(24);
        glow.setSpread(0.4);

        group.getChildren().addAll(outer, core);
        group.setEffect(glow);
        return group;
    }

    public void resetCell() {
        occupied = false;
        currentSymbol = null;
        symbolContainer.getChildren().clear();
        removeHighlight();
    }

    public void highlightWinner() {
        background.setFill(Color.rgb(28, 38, 64, 0.95));
        background.setStroke(Color.rgb(0, 229, 255, 0.9));
        background.setStrokeWidth(3);

        DropShadow winGlow = new DropShadow();
        winGlow.setColor(Color.rgb(0, 229, 255, 0.75));
        winGlow.setRadius(26);
        winGlow.setSpread(0.3);
        background.setEffect(winGlow);

        winnerAnimation = new ScaleTransition(Duration.millis(600), symbolContainer);
        winnerAnimation.setFromX(1.0);
        winnerAnimation.setFromY(1.0);
        winnerAnimation.setToX(1.12);
        winnerAnimation.setToY(1.12);
        winnerAnimation.setAutoReverse(true);
        winnerAnimation.setCycleCount(Animation.INDEFINITE);
        winnerAnimation.play();
    }

    public void removeHighlight() {
        if (winnerAnimation != null) {
            winnerAnimation.stop();
        }
        symbolContainer.setScaleX(1);
        symbolContainer.setScaleY(1);

        background.setFill(Color.rgb(14, 20, 34, 0.82));
        background.setStroke(Color.rgb(255, 255, 255, 0.12));
        background.setStrokeWidth(1.8);

        DropShadow normalShadow = new DropShadow();
        normalShadow.setColor(Color.rgb(0, 0, 0, 0.4));
        normalShadow.setRadius(10);
        normalShadow.setOffsetY(3);
        background.setEffect(normalShadow);
    }
}