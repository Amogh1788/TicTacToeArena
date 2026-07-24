package game;

public class GameManager {

    private boolean xTurn = true;
    private final String[][] board = new String[3][3];
    private final int[][] winningCells = new int[3][2];

    public String getCurrentPlayer() {
        return xTurn ? "X" : "O";
    }

    public void nextTurn() {
        xTurn = !xTurn;
    }

    public void makeMove(int row, int col) {
        board[row][col] = getCurrentPlayer();
    }

    public String getCell(int row, int col) {
        return board[row][col];
    }
    public String[] getBoardState() {

        String[] state = new String[9];

        int index = 0;

        for (int row = 0; row < 3; row++) {

            for (int col = 0; col < 3; col++) {

                state[index++] = board[row][col];

            }

        }

        return state;
    }
    private void setWinningCells(
            int r1, int c1,
            int r2, int c2,
            int r3, int c3) {

        winningCells[0][0] = r1;
        winningCells[0][1] = c1;

        winningCells[1][0] = r2;
        winningCells[1][1] = c2;

        winningCells[2][0] = r3;
        winningCells[2][1] = c3;
    }

    public int[][] getWinningCells() {
        return winningCells;
    }

    public boolean checkWinner() {

        // Rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != null &&
                    board[i][0].equals(board[i][1]) &&
                    board[i][1].equals(board[i][2])) {
                setWinningCells(i, 0, i, 1, i, 2);
                return true;
            }
        }

        // Columns
        for (int i = 0; i < 3; i++) {
            if (board[0][i] != null &&
                    board[0][i].equals(board[1][i]) &&
                    board[1][i].equals(board[2][i])) {
                setWinningCells(0, i, 1, i, 2, i);
                return true;
            }
        }

        // Main diagonal
        if (board[0][0] != null &&
                board[0][0].equals(board[1][1]) &&
                board[1][1].equals(board[2][2])) {
            setWinningCells(0, 0, 1, 1, 2, 2);
            return true;
        }

        // Other diagonal
        if (board[0][2] != null &&
                board[0][2].equals(board[1][1]) &&
                board[1][1].equals(board[2][0])) {
            setWinningCells(0, 2, 1, 1, 2, 0);
            return true;
        }

        return false;
    }
    public boolean isDraw() {

        for (int row = 0; row < 3; row++) {

            for (int col = 0; col < 3; col++) {

                if (board[row][col] == null) {
                    return false;
                }

            }
        }

        return !checkWinner();
    }

    public void reset() {

        xTurn = true;

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c] = null;
            }
        }
    }
}