package ui.components;

import javafx.animation.Animation;
import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
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
        setMinSize(100, 100);
        setMaxSize(145, 145);

        background = new Rectangle(130, 130);
        background.setArcWidth(20);
        background.setArcHeight(20);

        // Initial default fantasy glass appearance
        applyNormalStyle();

        // Bind background size to cell size
        widthProperty().addListener((obs, oldW, newW) -> background.setWidth(newW.doubleValue()));
        heightProperty().addListener((obs, oldH, newH) -> background.setHeight(newH.doubleValue()));

        symbolContainer = new StackPane();
        symbolContainer.setAlignment(Pos.CENTER);
        symbolContainer.setMouseTransparent(true);

        setAlignment(Pos.CENTER);
        getChildren().addAll(background, symbolContainer);

        setOnMouseEntered(e -> {
            if (!occupied) {
                applyHoverStyle();
            }
        });

        setOnMouseExited(e -> {
            if (!occupied) {
                applyNormalStyle();
            }
        });
    }

    private void applyNormalStyle() {
        LinearGradient gradient = new LinearGradient(
                0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.rgb(32, 22, 54, 0.62)),
                new Stop(1, Color.rgb(18, 26, 50, 0.58))
        );
        background.setFill(gradient);
        background.setStroke(Color.rgb(186, 104, 200, 0.32));
        background.setStrokeWidth(1.5);

        DropShadow shadow = new DropShadow();
        shadow.setBlurType(BlurType.GAUSSIAN);
        shadow.setColor(Color.rgb(0, 0, 0, 0.45));
        shadow.setRadius(10);
        shadow.setOffsetY(3);
        background.setEffect(shadow);
    }

    private void applyHoverStyle() {
        LinearGradient gradient = new LinearGradient(
                0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.rgb(56, 38, 96, 0.82)),
                new Stop(1, Color.rgb(26, 44, 82, 0.78))
        );
        background.setFill(gradient);
        background.setStroke(Color.rgb(0, 229, 255, 0.65));
        background.setStrokeWidth(1.8);

        DropShadow hoverGlow = new DropShadow();
        hoverGlow.setBlurType(BlurType.GAUSSIAN);
        hoverGlow.setColor(Color.rgb(0, 229, 255, 0.40));
        hoverGlow.setRadius(16);
        background.setEffect(hoverGlow);
    }

    private void applyOccupiedStyle(String symbol) {
        if ("X".equalsIgnoreCase(symbol)) {
            LinearGradient gradient = new LinearGradient(
                    0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                    new Stop(0, Color.rgb(24, 20, 48, 0.70)),
                    new Stop(1, Color.rgb(14, 28, 54, 0.65))
            );
            background.setFill(gradient);
            background.setStroke(Color.rgb(0, 229, 255, 0.32));
        } else {
            LinearGradient gradient = new LinearGradient(
                    0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                    new Stop(0, Color.rgb(38, 18, 38, 0.70)),
                    new Stop(1, Color.rgb(44, 20, 28, 0.65))
            );
            background.setFill(gradient);
            background.setStroke(Color.rgb(255, 82, 82, 0.32));
        }
        background.setStrokeWidth(1.5);

        DropShadow shadow = new DropShadow();
        shadow.setBlurType(BlurType.GAUSSIAN);
        shadow.setColor(Color.rgb(0, 0, 0, 0.45));
        shadow.setRadius(10);
        shadow.setOffsetY(3);
        background.setEffect(shadow);
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

        applyOccupiedStyle(symbol);

        // Pop-in scale animation
        symbolContainer.setScaleX(0);
        symbolContainer.setScaleY(0);

        ScaleTransition pop1 = new ScaleTransition(Duration.millis(120), symbolContainer);
        pop1.setToX(1.16);
        pop1.setToY(1.16);

        ScaleTransition pop2 = new ScaleTransition(Duration.millis(80), symbolContainer);
        pop2.setToX(0.95);
        pop2.setToY(0.95);

        ScaleTransition pop3 = new ScaleTransition(Duration.millis(70), symbolContainer);
        pop3.setToX(1.0);
        pop3.setToY(1.0);

        pop1.setOnFinished(e -> pop2.play());
        pop2.setOnFinished(e -> pop3.play());
        pop1.play();
    }

    private Group createXShape() {
        Group group = new Group();
        double size = 30;

        // Outer glow line
        Line line1 = new Line(-size, -size, size, size);
        line1.setStroke(Color.rgb(0, 229, 255));
        line1.setStrokeWidth(11);
        line1.setStrokeLineCap(StrokeLineCap.ROUND);

        Line line2 = new Line(size, -size, -size, size);
        line2.setStroke(Color.rgb(0, 229, 255));
        line2.setStrokeWidth(11);
        line2.setStrokeLineCap(StrokeLineCap.ROUND);

        // Core bright highlight line
        Line core1 = new Line(-size, -size, size, size);
        core1.setStroke(Color.rgb(230, 252, 255));
        core1.setStrokeWidth(4);
        core1.setStrokeLineCap(StrokeLineCap.ROUND);

        Line core2 = new Line(size, -size, -size, size);
        core2.setStroke(Color.rgb(230, 252, 255));
        core2.setStrokeWidth(4);
        core2.setStrokeLineCap(StrokeLineCap.ROUND);

        DropShadow glow = new DropShadow();
        glow.setBlurType(BlurType.GAUSSIAN);
        glow.setColor(Color.rgb(0, 229, 255, 0.85));
        glow.setRadius(22);
        glow.setSpread(0.4);

        group.getChildren().addAll(line1, line2, core1, core2);
        group.setEffect(glow);
        return group;
    }

    private Group createOShape() {
        Group group = new Group();
        double radius = 32;

        Circle outer = new Circle(radius);
        outer.setFill(Color.TRANSPARENT);
        outer.setStroke(Color.rgb(255, 82, 82));
        outer.setStrokeWidth(11);

        // Core bright highlight ring
        Circle core = new Circle(radius);
        core.setFill(Color.TRANSPARENT);
        core.setStroke(Color.rgb(255, 225, 225));
        core.setStrokeWidth(4);

        DropShadow glow = new DropShadow();
        glow.setBlurType(BlurType.GAUSSIAN);
        glow.setColor(Color.rgb(255, 82, 82, 0.85));
        glow.setRadius(22);
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
        applyNormalStyle();
    }

    public void highlightWinner() {
        LinearGradient gradient = new LinearGradient(
                0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.rgb(54, 38, 88, 0.92)),
                new Stop(1, Color.rgb(28, 48, 84, 0.90))
        );
        background.setFill(gradient);
        background.setStroke(Color.rgb(255, 215, 0, 0.95));
        background.setStrokeWidth(2.5);

        DropShadow winGlow = new DropShadow();
        winGlow.setColor(Color.rgb(255, 215, 0, 0.75));
        winGlow.setRadius(24);
        winGlow.setSpread(0.3);
        background.setEffect(winGlow);

        winnerAnimation = new ScaleTransition(Duration.millis(600), symbolContainer);
        winnerAnimation.setFromX(1.0);
        winnerAnimation.setFromY(1.0);
        winnerAnimation.setToX(1.10);
        winnerAnimation.setToY(1.10);
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

        if (occupied && currentSymbol != null) {
            applyOccupiedStyle(currentSymbol);
        } else {
            applyNormalStyle();
        }
    }
}