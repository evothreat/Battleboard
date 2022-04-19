import java.util.List;

public class ChessAI {

    static int DEPTH = 4;

    Colour color;

    public ChessAI(Colour color) {
        this.color = color;
    }

    public Move computeMove(Board board) {
        if (board.getTurn() != color) {
            return null;
        }
        double lowestScore = Integer.MAX_VALUE;
        Move bestMove = null;
        for (Move m : board.getValidMoves(null)) {

            board.makeMove(m.getSrc(), m.getDst());
            double score = Minimax.min(board, DEPTH-1, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
            board.restoreMove();

            if (lowestScore > score) {
                lowestScore = score;
                bestMove = m;
            }
        }
        return bestMove;
    }
}
