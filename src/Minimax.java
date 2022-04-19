import java.util.List;

// maybe check whether validMoves list is empty
public class Minimax {

    static double min(Board board, int depth, double alpha, double beta) {
        if (depth == 0) {
            return Evaluator.evaluate(board);
        }
        double min = Double.POSITIVE_INFINITY;
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

    static double max(Board board, int depth, double alpha, double beta) {
        if (depth == 0) {
            return Evaluator.evaluate(board);
        }
        double max = Double.NEGATIVE_INFINITY;
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
