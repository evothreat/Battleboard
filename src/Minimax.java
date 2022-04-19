import java.util.List;

// maybe check whether validMoves list is empty
public class Minimax {

    static int min(Board board, int depth) {
        if (depth == 0) {
            return board.evaluate();
        }
        int min = Integer.MAX_VALUE;
        for (Move m : board.getValidMoves(null)) {
            board.makeMove(m.getSrc(), m.getDst());
            min = Math.min(max(board, depth-1), min);
            board.restoreMove();
        }
        return min;
    }

    static int max(Board board, int depth) {
        if (depth == 0) {
            return board.evaluate();
        }
        int max = Integer.MIN_VALUE;
        for (Move m : board.getValidMoves(null)) {
            board.makeMove(m.getSrc(), m.getDst());
            max = Math.max(min(board, depth-1), max);
            board.restoreMove();
        }
        return max;
    }
}
