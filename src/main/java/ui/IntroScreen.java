package ui;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.ParallelTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class IntroScreen extends StackPane {

    private final ImageView logoView;
    private final Label studioLabel;
    private final Label presentsLabel;

    public IntroScreen(Stage stage) {

        setStyle("-fx-background-color: #050505;");

        // Logo
        Image logo = new Image(getClass().getResourceAsStream("/images/ag_logo.png"));

        logoView = new ImageView(logo);
        logoView.setFitWidth(250);
        logoView.setPreserveRatio(true);
        logoView.setOpacity(0);

        // Studio Name
        studioLabel = new Label("AG STUDIOS");
        studioLabel.setStyle("""
                -fx-text-fill: white;
                -fx-font-size: 32px;
                -fx-font-family: "Arial";
                -fx-font-weight: bold;
                """);
        studioLabel.setOpacity(0);

        // Presents
        presentsLabel = new Label("PRESENTS");
        presentsLabel.setStyle("""
                -fx-text-fill: white;
                -fx-font-size: 26px;
                -fx-font-family: "Arial";
                """);
        presentsLabel.setOpacity(0);

        VBox box = new VBox(20);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(logoView, studioLabel);

        getChildren().addAll(box, presentsLabel);

        StackPane.setAlignment(box, Pos.CENTER);
        StackPane.setAlignment(presentsLabel, Pos.CENTER);

        playIntro();
    }

    private void playIntro() {

        FadeTransition logoFade = new FadeTransition(Duration.seconds(2), logoView);
        logoFade.setFromValue(0);
        logoFade.setToValue(1);

        PauseTransition pause1 = new PauseTransition(Duration.seconds(0.5));

        FadeTransition studioFade = new FadeTransition(Duration.seconds(1.5), studioLabel);
        studioFade.setFromValue(0);
        studioFade.setToValue(1);

        PauseTransition hold = new PauseTransition(Duration.seconds(1.5));

        FadeTransition logoOut = new FadeTransition(Duration.seconds(1.5), logoView);
        logoOut.setFromValue(1);
        logoOut.setToValue(0);

        FadeTransition studioOut = new FadeTransition(Duration.seconds(1.5), studioLabel);
        studioOut.setFromValue(1);
        studioOut.setToValue(0);

        PauseTransition pause2 = new PauseTransition(Duration.seconds(0.5));

        FadeTransition presentsIn = new FadeTransition(Duration.seconds(1.2), presentsLabel);
        presentsIn.setFromValue(0);
        presentsIn.setToValue(1);

        PauseTransition hold2 = new PauseTransition(Duration.seconds(1.5));

        FadeTransition presentsOut = new FadeTransition(Duration.seconds(1.2), presentsLabel);
        presentsOut.setFromValue(1);
        presentsOut.setToValue(0);

        ParallelTransition fadeOutTogether = new ParallelTransition(
                logoOut,
                studioOut
        );

        SequentialTransition intro = new SequentialTransition(
                logoFade,
                pause1,
                studioFade,
                hold,
                fadeOutTogether,
                pause2,
                presentsIn,
                hold2,
                presentsOut
        );

        intro.setOnFinished(e -> {
            getScene().setRoot(new MainMenu());
        });

        intro.play();
    }
}