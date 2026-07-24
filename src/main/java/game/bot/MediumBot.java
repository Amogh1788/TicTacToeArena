package game.bot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MediumBot implements Bot {

    private final Random random = new Random();

    @Override
    public int makeMove(String[] board) {

        // 1. Try to win
        int move = findWinningMove(board, "O");

        if (move != -1)
            return move;

        // 2. Block player
        move = findWinningMove(board, "X");

        if (move != -1)
            return move;

        // 3. Random move
        List<Integer> available = new ArrayList<>();

        for (int i = 0; i < board.length; i++) {

            if (board[i] == null || board[i].isEmpty()) {
                available.add(i);
            }

        }

        if (available.isEmpty())
            return -1;

        return available.get(random.nextInt(available.size()));
    }

    private int findWinningMove(String[] board, String symbol) {

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

            int count = 0;
            int empty = -1;

            for (int pos : line) {

                if (symbol.equals(board[pos])) {

                    count++;

                } else if (board[pos] == null) {

                    empty = pos;

                }

            }

            if (count == 2 && empty != -1)
                return empty;

        }

        return -1;
    }

}