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
        int lowestScore = Integer.MAX_VALUE;
        Move bestMove = null;
        for (Move m : board.getValidMoves(null)) {

            board.makeMove(m.getSrc(), m.getDst());
            int score = Minimax.max(board, DEPTH-1);
            board.restoreMove();

            if (lowestScore > score) {
                lowestScore = score;
                bestMove = m;
            }
        }
        return bestMove;
    }
}
