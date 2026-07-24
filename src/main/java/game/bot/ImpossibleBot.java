package game.bot;

public class ImpossibleBot implements Bot {

    @Override
    public int makeMove(String[] board) {

        int bestScore = Integer.MIN_VALUE;
        int bestMove = -1;

        for (int i = 0; i < 9; i++) {

            if (board[i] == null) {

                board[i] = "O";

                int score = minimax(board, false);

                board[i] = null;

                if (score > bestScore) {

                    bestScore = score;
                    bestMove = i;

                }
            }
        }

        return bestMove;
    }

    private int minimax(String[] board, boolean isMaximizing) {

        String result = checkWinner(board);

        if (result != null) {

            switch (result) {

                case "O":
                    return 10;

                case "X":
                    return -10;

                case "DRAW":
                    return 0;

            }
        }

        if (isMaximizing) {

            int bestScore = Integer.MIN_VALUE;

            for (int i = 0; i < 9; i++) {

                if (board[i] == null) {

                    board[i] = "O";

                    int score = minimax(board, false);

                    board[i] = null;

                    bestScore = Math.max(bestScore, score);

                }

            }

            return bestScore;

        } else {

            int bestScore = Integer.MAX_VALUE;

            for (int i = 0; i < 9; i++) {

                if (board[i] == null) {

                    board[i] = "X";

                    int score = minimax(board, true);

                    board[i] = null;

                    bestScore = Math.min(bestScore, score);

                }

            }

            return bestScore;
        }

    }

    private String checkWinner(String[] board) {

        int[][] wins = {

                {0,1,2},
                {3,4,5},
                {6,7,8},

                {0,3,6},
                {1,4,7},
                {2,5,8},

                {0,4,8},
                {2,4,6}

        };

        for (int[] line : wins) {

            String a = board[line[0]];
            String b = board[line[1]];
            String c = board[line[2]];

            if (a != null && a.equals(b) && b.equals(c)) {

                return a;

            }

        }

        for (String cell : board) {

            if (cell == null) {

                return null;

            }

        }

        return "DRAW";

    }

}