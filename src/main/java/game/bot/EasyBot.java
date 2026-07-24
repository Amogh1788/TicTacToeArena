package game.bot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EasyBot implements Bot {

    private final Random random = new Random();

    @Override
    public int makeMove(String[] board) {

        List<Integer> available = new ArrayList<>();

        for (int i = 0; i < board.length; i++) {

            if (board[i] == null || board[i].isEmpty()) {
                available.add(i);
            }

        }

        if (available.isEmpty()) {
            return -1;
        }

        return available.get(random.nextInt(available.size()));
    }
}