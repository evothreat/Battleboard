import java.util.List;

// maybe check whether validMoves list is empty
public class Minimax {

    static int min(Board board, int depth) {
        if (depth == 0) {
            return board.evaluate();
        }
        int minValue = Integer.MAX_VALUE;
        for (Move m : board.getValidMoves(null)) {

            board.makeMove(m.getSrc(), m.getDst());
            int value = max(board, depth-1);
            board.restoreMove();

            if (minValue > value) {
                minValue = value;
            }
        }
        return minValue;
    }

    static int max(Board board, int depth) {
        if (depth == 0) {
            return board.evaluate();
        }
        int maxValue = Integer.MIN_VALUE;
        for (Move m : board.getValidMoves(null)) {

            board.makeMove(m.getSrc(), m.getDst());
            int value = min(board, depth-1);
            board.restoreMove();

            if (value > maxValue) {
                maxValue = value;
            }
        }
        return maxValue;
    }
}
