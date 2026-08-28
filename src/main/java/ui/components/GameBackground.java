package ui.components;

import javafx.animation.FadeTransition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class GameBackground extends StackPane {

    private final ImageView imageView;
    private final ImageView secondaryImageView;
    private final Rectangle vignette;
    private Image currentImage;

    public GameBackground(String imagePath) {
        setStyle("-fx-background-color: #080b12;");

        imageView = new ImageView();
        imageView.setSmooth(true);
        imageView.setCache(true);

        secondaryImageView = new ImageView();
        secondaryImageView.setSmooth(true);
        secondaryImageView.setCache(true);
        secondaryImageView.setOpacity(0);

        setImage(imagePath);

        // Dark Vignette & Atmospheric Overlay
        vignette = new Rectangle();
        vignette.setMouseTransparent(true);

        // Bind sizes to fill parent
        widthProperty().addListener((obs, oldW, newW) -> updateLayout(newW.doubleValue(), getHeight()));
        heightProperty().addListener((obs, oldH, newH) -> updateLayout(getWidth(), newH.doubleValue()));

        getChildren().addAll(imageView, secondaryImageView, vignette);
    }

    public void setImage(String imagePath) {
        try {
            var url = getClass().getResource(imagePath);
            if (url != null) {
                currentImage = new Image(url.toExternalForm(), true);
                imageView.setImage(currentImage);
                updateLayout(getWidth(), getHeight());
            }
        } catch (Exception e) {
            System.err.println("Could not load background image: " + imagePath);
        }
    }

    public void transitionToImage(String newImagePath, Duration duration) {
        try {
            var url = getClass().getResource(newImagePath);
            if (url != null) {
                Image newImg = new Image(url.toExternalForm(), true);
                secondaryImageView.setImage(newImg);
                secondaryImageView.setOpacity(0);

                FadeTransition ft = new FadeTransition(duration, secondaryImageView);
                ft.setFromValue(0);
                ft.setToValue(1);
                ft.setOnFinished(e -> {
                    imageView.setImage(newImg);
                    secondaryImageView.setOpacity(0);
                });
                ft.play();
            }
        } catch (Exception e) {
            setImage(newImagePath);
        }
    }

    private void updateLayout(double w, double h) {
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

        scaleImageView(imageView, w, h);
        scaleImageView(secondaryImageView, w, h);
    }

    private void scaleImageView(ImageView view, double w, double h) {
        Image img = view.getImage();
        if (img == null) return;

        double imgW = img.getWidth();
        double imgH = img.getHeight();
        if (imgW <= 0 || imgH <= 0) return;

        double scale = Math.max(w / imgW, h / imgH);
        double targetW = imgW * scale;
        double targetH = imgH * scale;

        view.setFitWidth(targetW);
        view.setFitHeight(targetH);

        // Center within view
        view.setTranslateX((w - targetW) / 2.0);
        view.setTranslateY((h - targetH) / 2.0);
    }
}
