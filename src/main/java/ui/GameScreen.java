package ui;

import agstudios.audio.SoundManager;
import agstudios.utils.ScreenTransition;
import game.GameManager;
import game.GameMode;
import game.GameStats;
import game.bot.Bot;
import game.bot.BotDifficulty;
import game.bot.BotFactory;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import ui.components.GameBackground;
import ui.components.GameButton;
import ui.components.GameCell;
import ui.components.WinningLine;

public class GameScreen extends StackPane {

    private final GameCell[][] cells = new GameCell[3][3];
    private final GameManager gameManager = new GameManager();
    private final WinningLine winningLine = new WinningLine();
    private final GameMode gameMode;
    private final BotDifficulty difficulty;
    private final Bot bot;

    private final GameBackground background;
    private final HBox playerXCard;
    private final HBox playerOCard;
    private final Label statusBanner;
    private final GameButton primaryActionButton;

    private boolean gameOver = false;
    private boolean botThinking = false;

    public GameScreen(GameMode gameMode, BotDifficulty difficulty) {
        this.gameMode = gameMode;
        this.difficulty = difficulty;
        GameStats.gamePlayed();

        // Load stylesheet
        try {
            var css = getClass().getResource("/css/style.css");
            if (css != null) {
                getStylesheets().add(css.toExternalForm());
            }
        } catch (Exception ignored) {}

        if (gameMode == GameMode.BOT) {
            switch (difficulty) {
                case EASY -> bot = BotFactory.createEasyBot();
                case MEDIUM -> bot = BotFactory.createMediumBot();
                case IMPOSSIBLE -> bot = BotFactory.createImpossibleBot();
                default -> bot = BotFactory.createEasyBot();
            }
        } else {
            bot = null;
        }

        // Background
        background = new GameBackground("/images/bg_gameplay.jpg");
        getChildren().add(background);

        // Main Layout Box
        VBox rootBox = new VBox(20);
        rootBox.setAlignment(Pos.CENTER);
        rootBox.setPadding(new Insets(20, 20, 20, 20));
        rootBox.setMaxWidth(600);

        // ==========================================
        // 1. Header: Player X, VS Badge, Player O
        // ==========================================
        String leftPlayerName = (gameMode == GameMode.BOT) ? "YOU" : "PLAYER 1";
        playerXCard = createPlayerCard("X", leftPlayerName, null, true);

        StackPane vsBadge = createVsBadge();

        String rightPlayerName = (gameMode == GameMode.BOT) ? "BOT" : "PLAYER 2";
        String rightSubtext = (gameMode == GameMode.BOT && difficulty != null) ? difficulty.name() : null;
        playerOCard = createPlayerCard("O", rightPlayerName, rightSubtext, false);

        HBox headerRow = new HBox(16);
        headerRow.setAlignment(Pos.CENTER);
        headerRow.getChildren().addAll(playerXCard, vsBadge, playerOCard);
        VBox.setMargin(headerRow, new Insets(0, 0, 8, 0));

        // ==========================================
        // 2. 3x3 Game Board Container
        // ==========================================
        GridPane board = new GridPane();
        board.setAlignment(Pos.CENTER);
        board.setHgap(10);
        board.setVgap(10);

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                GameCell cell = new GameCell();
                final int currentRow = row;
                final int currentCol = col;

                cell.setOnMouseClicked(e -> handleCellClick(currentRow, currentCol, cell));

                cells[row][col] = cell;
                board.add(cell, col, row);
            }
        }

        StackPane boardContainer = new StackPane();
        boardContainer.setAlignment(Pos.CENTER);
        boardContainer.setPadding(new Insets(14));
        boardContainer.getStyleClass().add("glass-panel-dark");

        winningLine.setPrefSize(420, 420);
        winningLine.setMaxSize(420, 420);
        boardContainer.getChildren().addAll(board, winningLine);

        // ==========================================
        // 3. Status Banner
        // ==========================================
        statusBanner = new Label(gameMode == GameMode.BOT ? "YOUR TURN" : "PLAYER X'S TURN");
        statusBanner.setFont(Font.font("Segoe UI", FontWeight.BOLD, 26));
        statusBanner.setTextFill(Color.rgb(0, 229, 255));
        DropShadow bannerGlow = new DropShadow();
        bannerGlow.setColor(Color.rgb(0, 229, 255, 0.7));
        bannerGlow.setRadius(16);
        statusBanner.setEffect(bannerGlow);

        VBox.setMargin(statusBanner, new Insets(6, 0, 6, 0));

        // ==========================================
        // 4. Bottom Action Row
        // ==========================================
        GameButton menuBtn = new GameButton("← MAIN MENU", GameButton.Variant.BACK);
        menuBtn.setOnAction(e -> {
            SoundManager.playSound("click.wav");
            ScreenTransition.switchScreen(getScene(), new MainMenu());
        });

        primaryActionButton = new GameButton("RESTART", GameButton.Variant.PRIMARY_CYAN);
        primaryActionButton.setOnAction(e -> {
            SoundManager.playSound("click.wav");
            resetGame();
        });

        GameButton statsBtn = new GameButton("STATS 📊", GameButton.Variant.BACK);
        statsBtn.setOnAction(e -> {
            SoundManager.playSound("click.wav");
            ScreenTransition.switchScreen(getScene(), new StatisticsScreen());
        });

        HBox bottomBar = new HBox(15);
        bottomBar.setAlignment(Pos.CENTER);
        bottomBar.getChildren().addAll(menuBtn, primaryActionButton, statsBtn);

        rootBox.getChildren().addAll(headerRow, boardContainer, statusBanner, bottomBar);
        getChildren().add(rootBox);

        updateActivePlayerVisuals();
    }

    private void handleCellClick(int row, int col, GameCell cell) {
        if (gameOver || botThinking || cell.isOccupied()) {
            return;
        }

        String player = gameManager.getCurrentPlayer();
        gameManager.makeMove(row, col);
        SoundManager.playSound("place.wav");
        cell.setSymbol(player);

        // Check Winner
        if (gameManager.checkWinner()) {
            handleGameWin(player);
            return;
        }

        // Check Draw
        if (gameManager.isDraw()) {
            handleGameDraw();
            return;
        }

        gameManager.nextTurn();
        updateActivePlayerVisuals();

        if (gameMode == GameMode.BOT) {
            statusBanner.setText("BOT IS THINKING...");
            statusBanner.setTextFill(Color.rgb(255, 193, 7));
            DropShadow botGlow = new DropShadow();
            botGlow.setColor(Color.rgb(255, 193, 7, 0.7));
            botGlow.setRadius(16);
            statusBanner.setEffect(botGlow);

            makeBotMove();
        } else {
            statusBanner.setText("PLAYER " + gameManager.getCurrentPlayer() + "'S TURN");
        }
    }

    private void makeBotMove() {
        if (bot == null || gameOver) return;
        botThinking = true;

        PauseTransition pause = new PauseTransition(Duration.millis(420));
        pause.setOnFinished(e -> {
            int move = bot.makeMove(gameManager.getBoardState());
            if (move == -1) {
                botThinking = false;
                return;
            }

            int row = move / 3;
            int col = move % 3;

            gameManager.makeMove(row, col);
            SoundManager.playSound("place.wav");
            cells[row][col].setSymbol("O");

            if (gameManager.checkWinner()) {
                handleGameWin("O");
                botThinking = false;
                return;
            }

            if (gameManager.isDraw()) {
                handleGameDraw();
                botThinking = false;
                return;
            }

            botThinking = false;
            gameManager.nextTurn();
            updateActivePlayerVisuals();
            statusBanner.setText("YOUR TURN");
        });
        pause.play();
    }

    private void handleGameWin(String winner) {
        gameOver = true;
        SoundManager.playSound("win.wav");

        int[][] winners = gameManager.getWinningCells();
        for (int i = 0; i < 3; i++) {
            cells[winners[i][0]][winners[i][1]].highlightWinner();
        }
        showWinningLine();

        if (gameMode == GameMode.FRIEND) {
            if ("X".equals(winner)) {
                GameStats.xWon();
                statusBanner.setText("PLAYER X WINS!");
            } else {
                GameStats.oWon();
                statusBanner.setText("PLAYER O WINS!");
            }
        } else {
            if ("X".equals(winner)) {
                GameStats.playerWon();
                statusBanner.setText("YOU WIN!");
            } else {
                GameStats.botWon();
                statusBanner.setText("BOT WINS!");
            }
        }

        Color winColor = "X".equals(winner) ? Color.rgb(0, 229, 255) : Color.rgb(255, 82, 82);
        statusBanner.setTextFill(winColor);
        DropShadow winGlow = new DropShadow();
        winGlow.setColor(winColor);
        winGlow.setRadius(24);
        statusBanner.setEffect(winGlow);

        ScaleTransition bannerPop = new ScaleTransition(Duration.millis(300), statusBanner);
        bannerPop.setFromX(0.85);
        bannerPop.setFromY(0.85);
        bannerPop.setToX(1.1);
        bannerPop.setToY(1.1);
        bannerPop.setAutoReverse(true);
        bannerPop.setCycleCount(2);
        bannerPop.play();

        primaryActionButton.setText("PLAY AGAIN");
        primaryActionButton.setStyle("""
                -fx-background-color: linear-gradient(to bottom, #00d2ff, #0099ff);
                -fx-background-radius: 30px;
                -fx-border-color: rgba(255, 255, 255, 0.6);
                -fx-border-radius: 30px;
                -fx-border-width: 1.5px;
                -fx-text-fill: white;
                -fx-font-size: 16px;
                -fx-font-weight: bold;
                -fx-cursor: hand;
                -fx-padding: 10px 24px;
                """);
    }

    private void handleGameDraw() {
        gameOver = true;
        SoundManager.playSound("tie.wav");

        if (gameMode == GameMode.FRIEND) {
            GameStats.friendDraw();
        } else {
            GameStats.botDraw();
        }

        statusBanner.setText("IT'S A TIE!");
        statusBanner.setTextFill(Color.rgb(255, 171, 0));
        DropShadow tieGlow = new DropShadow();
        tieGlow.setColor(Color.rgb(255, 152, 0, 0.85));
        tieGlow.setRadius(24);
        statusBanner.setEffect(tieGlow);

        // Transition background to warm golden autumn environment
        background.transitionToImage("/images/bg_gameplay_autumn.jpg", Duration.millis(500));

        primaryActionButton.setText("PLAY AGAIN");
        primaryActionButton.setStyle("""
                -fx-background-color: linear-gradient(to bottom, #ff9800, #f57c00);
                -fx-background-radius: 30px;
                -fx-border-color: rgba(255, 255, 255, 0.6);
                -fx-border-radius: 30px;
                -fx-border-width: 1.5px;
                -fx-text-fill: white;
                -fx-font-size: 16px;
                -fx-font-weight: bold;
                -fx-cursor: hand;
                -fx-padding: 10px 24px;
                """);
    }

    private void resetGame() {
        gameManager.reset();
        gameOver = false;
        botThinking = false;

        background.transitionToImage("/images/bg_gameplay.jpg", Duration.millis(400));

        statusBanner.setText(gameMode == GameMode.BOT ? "YOUR TURN" : "PLAYER X'S TURN");
        statusBanner.setTextFill(Color.rgb(0, 229, 255));
        DropShadow bannerGlow = new DropShadow();
        bannerGlow.setColor(Color.rgb(0, 229, 255, 0.7));
        bannerGlow.setRadius(16);
        statusBanner.setEffect(bannerGlow);

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                cells[r][c].resetCell();
            }
        }
        winningLine.hideLine();

        primaryActionButton.setText("RESTART");
        primaryActionButton.setStyle("""
                -fx-background-color: linear-gradient(to bottom, #00d2ff, #0099ff);
                -fx-background-radius: 30px;
                -fx-border-color: rgba(255, 255, 255, 0.45);
                -fx-border-radius: 30px;
                -fx-border-width: 1.5px;
                -fx-text-fill: white;
                -fx-font-size: 16px;
                -fx-font-weight: bold;
                -fx-cursor: hand;
                -fx-padding: 10px 24px;
                """);

        updateActivePlayerVisuals();
    }

    private void updateActivePlayerVisuals() {
        boolean isX = "X".equals(gameManager.getCurrentPlayer());

        playerXCard.getStyleClass().clear();
        playerXCard.getStyleClass().add(isX ? "player-badge-x-active" : "player-badge-x");

        playerOCard.getStyleClass().clear();
        playerOCard.getStyleClass().add(!isX ? "player-badge-o-active" : "player-badge-o");

        if (!gameOver) {
            Color activeColor = isX ? Color.rgb(0, 229, 255) : Color.rgb(255, 82, 82);
            statusBanner.setTextFill(activeColor);
            DropShadow glow = new DropShadow();
            glow.setColor(activeColor);
            glow.setRadius(16);
            statusBanner.setEffect(glow);
        }
    }

    private HBox createPlayerCard(String symbol, String name, String subtitle, boolean isLeft) {
        HBox card = new HBox(12);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(10, 18, 10, 18));
        card.setPrefWidth(170);

        // Symbol Circular Badge
        StackPane badge = new StackPane();
        Circle circle = new Circle(18);
        circle.setFill(Color.rgb(10, 14, 24, 0.8));
        circle.setStroke("X".equals(symbol) ? Color.rgb(0, 229, 255) : Color.rgb(255, 82, 82));
        circle.setStrokeWidth(2);

        Label symLbl = new Label("X".equals(symbol) ? "✕" : "◯");
        symLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        symLbl.setTextFill("X".equals(symbol) ? Color.rgb(0, 229, 255) : Color.rgb(255, 82, 82));
        badge.getChildren().addAll(circle, symLbl);

        VBox textGroup = new VBox(2);
        textGroup.setAlignment(Pos.CENTER_LEFT);

        Label nameLbl = new Label(name);
        nameLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));
        nameLbl.setTextFill(Color.WHITE);
        textGroup.getChildren().add(nameLbl);

        if (subtitle != null && !subtitle.isEmpty()) {
            Label subLbl = new Label(subtitle);
            subLbl.setFont(Font.font("Segoe UI", 10));
            subLbl.setStyle("-fx-text-fill: #94a3b8;");
            textGroup.getChildren().add(subLbl);
        }

        if (isLeft) {
            card.getChildren().addAll(badge, textGroup);
        } else {
            card.getChildren().addAll(textGroup, badge);
        }

        return card;
    }

    private StackPane createVsBadge() {
        StackPane stack = new StackPane();
        Circle circle = new Circle(22);
        circle.setFill(Color.rgb(40, 28, 10, 0.9));
        circle.setStroke(Color.rgb(255, 193, 7));
        circle.setStrokeWidth(1.8);

        DropShadow vsGlow = new DropShadow();
        vsGlow.setColor(Color.rgb(255, 193, 7, 0.6));
        vsGlow.setRadius(14);
        circle.setEffect(vsGlow);

        VBox content = new VBox(0);
        content.setAlignment(Pos.CENTER);

        Label bolt = new Label("⚡");
        bolt.setFont(Font.font("Segoe UI Emoji", 14));

        Label vs = new Label("VS");
        vs.setFont(Font.font("Segoe UI", FontWeight.EXTRA_BOLD, 11));
        vs.setStyle("-fx-text-fill: #ffc107;");

        content.getChildren().addAll(bolt, vs);
        stack.getChildren().addAll(circle, content);
        return stack;
    }

    private void showWinningLine() {
        int[][] winners = gameManager.getWinningCells();

        GameCell firstCell = cells[winners[0][0]][winners[0][1]];
        GameCell lastCell  = cells[winners[2][0]][winners[2][1]];

        Bounds firstBounds = firstCell.localToScene(firstCell.getBoundsInLocal());
        Bounds lastBounds  = lastCell.localToScene(lastCell.getBoundsInLocal());

        Bounds overlayBounds = winningLine.localToScene(winningLine.getBoundsInLocal());

        double startX = firstBounds.getMinX() - overlayBounds.getMinX() + firstBounds.getWidth() / 2;
        double startY = firstBounds.getMinY() - overlayBounds.getMinY() + firstBounds.getHeight() / 2;
        double endX = lastBounds.getMinX() - overlayBounds.getMinX() + lastBounds.getWidth() / 2;
        double endY = lastBounds.getMinY() - overlayBounds.getMinY() + lastBounds.getHeight() / 2;

        double extension = 24;
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