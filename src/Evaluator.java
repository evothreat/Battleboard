public class Evaluator {

    static int evaluate(Board board) {
        int score = weightOf(board.getKingSq(Colour.WHITE).getPiece());
        for (Square sq : board.getPiecesSq(Colour.WHITE)) {
            score += weightOf(sq.getPiece());
        }
        score -= weightOf(board.getKingSq(Colour.BLACK).getPiece());
        for (Square sq : board.getPiecesSq(Colour.BLACK)) {
            score -= weightOf(sq.getPiece());
        }
        return score;
    }

    static int weightOf(Piece piece) {
        switch (piece.getPieceType()) {
            case PAWN:
                return 100;
            case KNIGHT:
                return 320;
            case BISHOP:
                return 330;
            case ROOK:
                return 500;
            case QUEEN:
                return 900;
            case KING:
                return 20000;
        }
        return 0;
    }
}
