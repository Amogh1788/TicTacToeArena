package agstudios.utils;

import javafx.animation.FadeTransition;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.util.Duration;

public class ScreenTransition {

    public static void switchScreen(Scene scene, Parent newRoot) {

        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), scene.getRoot());
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        fadeOut.setOnFinished(e -> {

            newRoot.setOpacity(0);
            scene.setRoot(newRoot);

            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), newRoot);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();

        });

        fadeOut.play();
    }
}