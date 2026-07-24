package game;

public class GameStats {

    // =========================
    // Overall
    // =========================

    private static int gamesPlayed;

    // =========================
    // Friend Mode
    // =========================

    private static int xWins;
    private static int oWins;
    private static int friendDraws;

    // =========================
    // Bot Mode
    // =========================

    private static int playerWins;
    private static int botWins;
    private static int botDraws;

    // =========================
    // Increment Methods
    // =========================

    public static void gamePlayed() {
        gamesPlayed++;
    }

    public static void xWon() {
        xWins++;
    }

    public static void oWon() {
        oWins++;
    }

    public static void friendDraw() {
        friendDraws++;
    }

    public static void playerWon() {
        playerWins++;
    }

    public static void botWon() {
        botWins++;
    }

    public static void botDraw() {
        botDraws++;
    }

    // =========================
    // Getters
    // =========================

    public static int getGamesPlayed() {
        return gamesPlayed;
    }

    public static int getXWins() {
        return xWins;
    }

    public static int getOWins() {
        return oWins;
    }

    public static int getFriendDraws() {
        return friendDraws;
    }

    public static int getPlayerWins() {
        return playerWins;
    }

    public static int getBotWins() {
        return botWins;
    }

    public static int getBotDraws() {
        return botDraws;
    }

    // =========================
    // Reset Statistics
    // =========================

    public static void reset() {

        gamesPlayed = 0;

        xWins = 0;
        oWins = 0;
        friendDraws = 0;

        playerWins = 0;
        botWins = 0;
        botDraws = 0;
    }
}