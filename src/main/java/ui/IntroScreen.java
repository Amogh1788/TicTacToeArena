package ui;

import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
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
        logoView.setScaleX(1);
        logoView.setScaleY(1);

        // Studio Name
        studioLabel = new Label("AG STUDIOS");
        studioLabel.setFont(Font.font("Arial Black", 30));
        studioLabel.setStyle("""
    -fx-text-fill: white;
    -fx-font-weight: bold;
    """);
        studioLabel.setOpacity(0);
        DropShadow glow = new DropShadow();

        glow.setRadius(10);
        glow.setSpread(0.12);
        glow.setColor(Color.web("#1E88E5"));

        studioLabel.setEffect(glow);


        // Presents
        presentsLabel = new Label("PRESENTS...");
        presentsLabel.setFont(Font.font("Arial", FontPosture.ITALIC, 40));

        presentsLabel.setStyle("""
    -fx-text-fill: #B0B0B0;
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
        ScaleTransition scale = new ScaleTransition(Duration.millis(450), logoView);

        scale.setFromX(0.9);
        scale.setFromY(0.9);

        scale.setToX(1);
        scale.setToY(1);

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
        ParallelTransition logoIntro =
                new ParallelTransition(logoFade, scale);
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