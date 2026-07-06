package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import ui.MainMenu;
import agstudios.utils.ScreenTransition;
import ui.components.GameCell;
import game.GameManager;
import ui.components.WinningLine;
import javafx.geometry.Bounds;

public class GameScreen extends BorderPane {

    private final Button playAgainButton = new Button("🔄 Play Again");
    private final GameCell[][] cells = new GameCell[3][3];
    private final GameManager gameManager = new GameManager();
    private final Label turnLabel = new Label("Player X's Turn");
    private final WinningLine winningLine = new WinningLine();

    private boolean gameOver = false;

    public GameScreen() {

        setStyle("-fx-background-color: #050505;");

        // Top Label
        turnLabel.setText("Player X's Turn");
        turnLabel.setFont(Font.font("Arial", 28));
        turnLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        BorderPane.setAlignment(turnLabel, Pos.CENTER);
        BorderPane.setMargin(turnLabel, new Insets(30));

        setTop(turnLabel);

        // Game Board
        GridPane board = new GridPane();
        board.setAlignment(Pos.CENTER);
        board.setHgap(10);
        board.setVgap(10);

        for (int row = 0; row < 3; row++) {

            for (int col = 0; col < 3; col++) {

                GameCell cell = new GameCell();


                final int currentRow = row;
                final int currentCol = col;

                cell.setOnMouseClicked(e -> {

                    if (gameOver || cell.isOccupied())
                        return;

                    String player = gameManager.getCurrentPlayer();

                    gameManager.makeMove(currentRow, currentCol);

                    cell.setSymbol(player);

// Check winner BEFORE changing turn
                    if (gameManager.checkWinner()) {

                        gameOver = true;

                        int[][] winners = gameManager.getWinningCells();

                        for (int i = 0; i < 3; i++) {

                            int winnerRow = winners[i][0];
                            int winnerCol = winners[i][1];

                            cells[winnerRow][winnerCol].highlightWinner();
                        }
                        showWinningLine();

                        turnLabel.setText("🏆 Player " + player + " Wins!");

                        playAgainButton.setVisible(true);
                        playAgainButton.setManaged(true);

                        return;
                    }
                    if (gameManager.isDraw()) {

                        gameOver = true;

                        turnLabel.setText("🤝 It's a Draw!");
                        playAgainButton.setVisible(true);
                        playAgainButton.setManaged(true);

                        return;
                    }

                    gameManager.nextTurn();

                    turnLabel.setText(
                            "Player " + gameManager.getCurrentPlayer() + "'s Turn"
                    );

                });

                cells[row][col] = cell;

                board.add(cell, col, row);
            }
        }
        StackPane boardContainer = new StackPane();

        boardContainer.setPrefSize(440, 440);

        winningLine.setPrefSize(440, 440);

        boardContainer.getChildren().addAll(board, winningLine);

        setCenter(boardContainer);

        // Bottom Buttons
        Button backButton = new Button("🏠 Back to Menu");
        backButton.setPrefSize(220, 45);
        backButton.setStyle("""
                -fx-background-color: #1E88E5;
                -fx-text-fill: white;
                -fx-font-size: 16px;
                -fx-background-radius: 10;
                -fx-cursor: hand;
                """);
        backButton.setOnAction(e -> {
            ScreenTransition.switchScreen(getScene(), new MainMenu());
        });

        HBox bottom = new HBox(15);

        bottom.getChildren().addAll(backButton, playAgainButton);
        bottom.setAlignment(Pos.CENTER);
        bottom.setPadding(new Insets(25));

        setBottom(bottom);

        playAgainButton.setPrefSize(220, 45);

        playAgainButton.setStyle("""
        -fx-background-color: #43A047;
        -fx-text-fill: white;
        -fx-font-size: 16px;
        -fx-background-radius: 10;
        -fx-cursor: hand;
        """);

        playAgainButton.setVisible(false);
        playAgainButton.setManaged(false);
        playAgainButton.setOnAction(e -> {

            gameManager.reset();

            gameOver = false;

            turnLabel.setText("Player X's Turn");

            for (int row = 0; row < 3; row++) {

                for (int col = 0; col < 3; col++) {

                    cells[row][col].resetCell();

                }
            }
            winningLine.hideLine();

            playAgainButton.setVisible(false);
            playAgainButton.setManaged(false);
        });

    }
    private void showWinningLine() {

        int[][] winners = gameManager.getWinningCells();

        GameCell firstCell = cells[winners[0][0]][winners[0][1]];
        GameCell lastCell  = cells[winners[2][0]][winners[2][1]];

        Bounds firstBounds = firstCell.localToScene(firstCell.getBoundsInLocal());
        Bounds lastBounds  = lastCell.localToScene(lastCell.getBoundsInLocal());

        Bounds overlayBounds = winningLine.localToScene(winningLine.getBoundsInLocal());

        double startX = firstBounds.getMinX() - overlayBounds.getMinX()
                + firstBounds.getWidth() / 2;

        double startY = firstBounds.getMinY() - overlayBounds.getMinY()
                + firstBounds.getHeight() / 2;

        double endX = lastBounds.getMinX() - overlayBounds.getMinX()
                + lastBounds.getWidth() / 2;

        double endY = lastBounds.getMinY() - overlayBounds.getMinY()
                + lastBounds.getHeight() / 2;

        // ===== Extend the line =====
        double extension = 28;

        double dx = endX - startX;
        double dy = endY - startY;

        double length = Math.sqrt(dx * dx + dy * dy);

        if (length != 0) {

            double ux = dx / length;
            double uy = dy / length;

            startX -= ux * extension;
            startY -= uy * extension;

            endX += ux * extension;
            endY += uy * extension;
        }

        winningLine.showLine(startX, startY, endX, endY);
    }
}