package ui.components;

import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class GameButton extends Button {

    public enum Variant {
        MENU_CAPSULE,
        PRIMARY_CYAN,
        PRIMARY_AMBER,
        BACK,
        DANGER
    }

    private final Variant variant;

    public GameButton(String text) {
        this(text, Variant.MENU_CAPSULE);
    }

    public GameButton(String text, Variant variant) {
        super();
        this.variant = variant;

        parseAndSetupContent(text);
        applyStyling();
        setupAnimations();
    }

    public GameButton(String icon, String text, Variant variant) {
        super();
        this.variant = variant;

        setupContent(icon, text);
        applyStyling();
        setupAnimations();
    }

    private void parseAndSetupContent(String rawText) {
        String icon = "";
        String label = rawText;

        if (rawText != null && rawText.length() > 2) {
            // Check if string starts with an emoji or symbol
            int spaceIdx = rawText.indexOf(' ');
            if (spaceIdx > 0 && spaceIdx <= 3) {
                icon = rawText.substring(0, spaceIdx).trim();
                label = rawText.substring(spaceIdx + 1).trim();
            }
        }

        if (!icon.isEmpty() && variant == Variant.MENU_CAPSULE) {
            setupContent(icon, label);
        } else {
            setText(rawText);
        }
    }

    private void setupContent(String icon, String labelText) {
        HBox container = new HBox(16);
        container.setAlignment(Pos.CENTER_LEFT);
        container.setMaxWidth(Double.MAX_VALUE);

        Label iconLbl = new Label(icon);
        iconLbl.setFont(Font.font("Segoe UI Emoji", 20));
        iconLbl.setAlignment(Pos.CENTER);
        iconLbl.setMinWidth(32);

        Label textLbl = new Label(labelText);
        textLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 17));
        textLbl.setTextFill(Color.WHITE);

        container.getChildren().addAll(iconLbl, textLbl);
        setGraphic(container);
        setText("");
    }

    private void applyStyling() {
        setMinWidth(Region.USE_PREF_SIZE);

        switch (variant) {
            case MENU_CAPSULE -> {
                setPrefHeight(54);
                setPrefWidth(320);
                setMaxWidth(360);
                setStyle("""
                        -fx-background-color: linear-gradient(to right, rgba(20, 26, 44, 0.88), rgba(28, 36, 62, 0.88));
                        -fx-background-radius: 16px;
                        -fx-border-color: rgba(255, 255, 255, 0.12);
                        -fx-border-radius: 16px;
                        -fx-border-width: 1.5px;
                        -fx-text-fill: white;
                        -fx-font-size: 17px;
                        -fx-font-weight: bold;
                        -fx-cursor: hand;
                        -fx-padding: 8px 24px;
                        -fx-alignment: CENTER_LEFT;
                        """);

                DropShadow shadow = new DropShadow();
                shadow.setColor(Color.rgb(0, 0, 0, 0.45));
                shadow.setRadius(12);
                shadow.setOffsetY(4);
                setEffect(shadow);
            }
            case PRIMARY_CYAN -> {
                setPrefHeight(46);
                setStyle("""
                        -fx-background-color: linear-gradient(to bottom, #00d2ff, #0099ff);
                        -fx-background-radius: 30px;
                        -fx-border-color: rgba(255, 255, 255, 0.45);
                        -fx-border-radius: 30px;
                        -fx-border-width: 1.5px;
                        -fx-text-fill: white;
                        -fx-font-size: 14.5px;
                        -fx-font-weight: bold;
                        -fx-cursor: hand;
                        -fx-padding: 8px 22px;
                        """);

                DropShadow glow = new DropShadow();
                glow.setColor(Color.rgb(0, 210, 255, 0.65));
                glow.setRadius(18);
                glow.setOffsetY(2);
                setEffect(glow);
            }
            case PRIMARY_AMBER -> {
                setPrefHeight(46);
                setStyle("""
                        -fx-background-color: linear-gradient(to bottom, #ff9800, #f57c00);
                        -fx-background-radius: 30px;
                        -fx-border-color: rgba(255, 255, 255, 0.45);
                        -fx-border-radius: 30px;
                        -fx-border-width: 1.5px;
                        -fx-text-fill: white;
                        -fx-font-size: 14.5px;
                        -fx-font-weight: bold;
                        -fx-cursor: hand;
                        -fx-padding: 8px 22px;
                        """);

                DropShadow glow = new DropShadow();
                glow.setColor(Color.rgb(255, 152, 0, 0.65));
                glow.setRadius(18);
                glow.setOffsetY(2);
                setEffect(glow);
            }
            case BACK -> {
                setPrefHeight(46);
                setStyle("""
                        -fx-background-color: rgba(24, 28, 44, 0.85);
                        -fx-background-radius: 30px;
                        -fx-border-color: rgba(255, 255, 255, 0.22);
                        -fx-border-radius: 30px;
                        -fx-border-width: 1.5px;
                        -fx-text-fill: #e2e8f0;
                        -fx-font-size: 14px;
                        -fx-font-weight: bold;
                        -fx-cursor: hand;
                        -fx-padding: 8px 18px;
                        """);

                DropShadow shadow = new DropShadow();
                shadow.setColor(Color.rgb(0, 0, 0, 0.4));
                shadow.setRadius(10);
                shadow.setOffsetY(2);
                setEffect(shadow);
            }
            case DANGER -> {
                setPrefHeight(46);
                setStyle("""
                        -fx-background-color: linear-gradient(to bottom, #ef5350, #c62828);
                        -fx-background-radius: 30px;
                        -fx-border-color: rgba(255, 255, 255, 0.35);
                        -fx-border-radius: 30px;
                        -fx-border-width: 1.5px;
                        -fx-text-fill: white;
                        -fx-font-size: 14px;
                        -fx-font-weight: bold;
                        -fx-cursor: hand;
                        -fx-padding: 8px 18px;
                        """);

                DropShadow glow = new DropShadow();
                glow.setColor(Color.rgb(244, 67, 54, 0.5));
                glow.setRadius(14);
                glow.setOffsetY(2);
                setEffect(glow);
            }
        }
    }

    private void setupAnimations() {
        ScaleTransition grow = new ScaleTransition(Duration.millis(140), this);
        grow.setToX(1.04);
        grow.setToY(1.04);

        ScaleTransition shrink = new ScaleTransition(Duration.millis(140), this);
        shrink.setToX(1.0);
        shrink.setToY(1.0);

        setOnMouseEntered(e -> {
            switch (variant) {
                case MENU_CAPSULE -> {
                    setStyle("""
                            -fx-background-color: linear-gradient(to right, rgba(32, 44, 76, 0.95), rgba(42, 56, 96, 0.95));
                            -fx-background-radius: 16px;
                            -fx-border-color: rgba(0, 229, 255, 0.65);
                            -fx-border-radius: 16px;
                            -fx-border-width: 1.5px;
                            -fx-text-fill: white;
                            -fx-font-size: 17px;
                            -fx-font-weight: bold;
                            -fx-cursor: hand;
                            -fx-padding: 8px 24px;
                            -fx-alignment: CENTER_LEFT;
                            """);

                    DropShadow glow = new DropShadow();
                    glow.setColor(Color.rgb(0, 229, 255, 0.45));
                    glow.setRadius(20);
                    glow.setOffsetY(2);
                    setEffect(glow);
                }
                case PRIMARY_CYAN -> {
                    setStyle("""
                            -fx-background-color: linear-gradient(to bottom, #33dcff, #1ab2ff);
                            -fx-background-radius: 30px;
                            -fx-border-color: rgba(255, 255, 255, 0.7);
                            -fx-border-radius: 30px;
                            -fx-border-width: 1.5px;
                            -fx-text-fill: white;
                            -fx-font-size: 14.5px;
                            -fx-font-weight: bold;
                            -fx-cursor: hand;
                            -fx-padding: 8px 22px;
                            """);

                    DropShadow glow = new DropShadow();
                    glow.setColor(Color.rgb(0, 240, 255, 0.85));
                    glow.setRadius(24);
                    glow.setOffsetY(3);
                    setEffect(glow);
                }
                case PRIMARY_AMBER -> {
                    setStyle("""
                            -fx-background-color: linear-gradient(to bottom, #ffac33, #fb8c00);
                            -fx-background-radius: 30px;
                            -fx-border-color: rgba(255, 255, 255, 0.7);
                            -fx-border-radius: 30px;
                            -fx-border-width: 1.5px;
                            -fx-text-fill: white;
                            -fx-font-size: 14.5px;
                            -fx-font-weight: bold;
                            -fx-cursor: hand;
                            -fx-padding: 8px 22px;
                            """);

                    DropShadow glow = new DropShadow();
                    glow.setColor(Color.rgb(255, 172, 51, 0.85));
                    glow.setRadius(24);
                    glow.setOffsetY(3);
                    setEffect(glow);
                }
                case BACK -> {
                    setStyle("""
                            -fx-background-color: rgba(40, 48, 76, 0.95);
                            -fx-background-radius: 30px;
                            -fx-border-color: rgba(255, 255, 255, 0.45);
                            -fx-border-radius: 30px;
                            -fx-border-width: 1.5px;
                            -fx-text-fill: white;
                            -fx-font-size: 14px;
                            -fx-font-weight: bold;
                            -fx-cursor: hand;
                            -fx-padding: 8px 18px;
                            """);

                    DropShadow glow = new DropShadow();
                    glow.setColor(Color.rgb(255, 255, 255, 0.25));
                    glow.setRadius(14);
                    glow.setOffsetY(2);
                    setEffect(glow);
                }
                case DANGER -> {
                    setStyle("""
                            -fx-background-color: linear-gradient(to bottom, #ff6b68, #d32f2f);
                            -fx-background-radius: 30px;
                            -fx-border-color: rgba(255, 255, 255, 0.5);
                            -fx-border-radius: 30px;
                            -fx-border-width: 1.5px;
                            -fx-text-fill: white;
                            -fx-font-size: 14px;
                            -fx-font-weight: bold;
                            -fx-cursor: hand;
                            -fx-padding: 8px 18px;
                            """);

                    DropShadow glow = new DropShadow();
                    glow.setColor(Color.rgb(244, 67, 54, 0.75));
                    glow.setRadius(20);
                    glow.setOffsetY(2);
                    setEffect(glow);
                }
            }
            grow.playFromStart();
        });

        setOnMouseExited(e -> {
            applyStyling();
            shrink.playFromStart();
        });

        setOnMousePressed(e -> {
            setScaleX(0.97);
            setScaleY(0.97);
        });

        setOnMouseReleased(e -> {
            setScaleX(1.04);
            setScaleY(1.04);
        });
    }
}