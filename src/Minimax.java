import java.util.List;

// maybe check whether validMoves list is empty
public class Minimax {

    static int min(Board board, int depth, int alpha, int beta) {
        if (depth == 0) {
            return board.evaluate();
        }
        int min = Integer.MAX_VALUE;
        for (Move m : board.getValidMoves(null)) {
            board.makeMove(m.getSrc(), m.getDst());

            min = Math.min(max(board, depth-1, alpha, beta), min);
            beta = Math.min(beta, min);

            board.restoreMove();

            if (beta <= alpha) {
                break;
            }

        }
        return min;
    }

    static int max(Board board, int depth, int alpha, int beta) {
        if (depth == 0) {
            return board.evaluate();
        }
        int max = Integer.MIN_VALUE;
        for (Move m : board.getValidMoves(null)) {
            board.makeMove(m.getSrc(), m.getDst());

            max = Math.max(min(board, depth-1, alpha, beta), max);
            alpha = Math.max(alpha, max);

            board.restoreMove();
            if (beta <= alpha) {
                break;
            }
        }
        return max;
    }
}
