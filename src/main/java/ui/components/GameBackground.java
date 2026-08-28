package ui.components;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

public class GameBackground extends StackPane {

    private final ImageView imageView;
    private final Rectangle vignette;

    public GameBackground(String imagePath) {
        setStyle("-fx-background-color: #080b12;");
        setPickOnBounds(false);

        imageView = new ImageView();
        imageView.setSmooth(true);
        imageView.setCache(true);
        imageView.setMouseTransparent(true);

        try {
            var is = getClass().getResourceAsStream(imagePath);
            if (is != null) {
                Image img = new Image(is);
                imageView.setImage(img);
            }
        } catch (Exception e) {
            System.err.println("Could not load background image: " + imagePath);
        }

        // Dark Vignette & Atmospheric Overlay
        vignette = new Rectangle();
        vignette.setMouseTransparent(true);

        getChildren().addAll(imageView, vignette);
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        double w = getWidth();
        double h = getHeight();
        if (w <= 0 || h <= 0) return;

        vignette.setWidth(w);
        vignette.setHeight(h);

        // Dark edge radial vignette for high UI contrast
        RadialGradient gradient = new RadialGradient(
                0, 0,
                0.5, 0.5,
                0.75,
                true,
                CycleMethod.NO_CYCLE,
                new Stop(0, Color.rgb(8, 11, 20, 0.30)),
                new Stop(0.7, Color.rgb(6, 9, 16, 0.55)),
                new Stop(1.0, Color.rgb(3, 4, 8, 0.85))
        );
        vignette.setFill(gradient);

        Image img = imageView.getImage();
        if (img != null && img.getWidth() > 0 && img.getHeight() > 0) {
            double imgW = img.getWidth();
            double imgH = img.getHeight();

            double scale = Math.max(w / imgW, h / imgH);
            double targetW = imgW * scale;
            double targetH = imgH * scale;

            imageView.setFitWidth(targetW);
            imageView.setFitHeight(targetH);

            double x = (w - targetW) / 2.0;
            double y = (h - targetH) / 2.0;

            imageView.setLayoutX(x);
            imageView.setLayoutY(y);
        }
    }
}
