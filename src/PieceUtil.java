public class PieceUtil {

    static int KING_WEIGHT = 20000;
    static int QUEEN_WEIGHT = 900;
    static int ROOK_WEIGHT = 500;
    static int BISHOP_WEIGHT = 330;
    static int KNIGHT_WEIGHT = 320;
    static int PAWN_WEIGHT = 100;

    static Piece intToPiece(int n) {
        Colour color = n > 0 ? Colour.WHITE : Colour.BLACK;
        int sign = color.sign();
        switch (n * sign) {
            case 6:
                return new King(color, sign * KING_WEIGHT);
            case 5:
                return new Queen(color, sign * QUEEN_WEIGHT);
            case 4:
                return new Rook(color, sign * ROOK_WEIGHT);
            case 3:
                return new Bishop(color, sign * BISHOP_WEIGHT);
            case 2:
                return new Knight(color, sign * KNIGHT_WEIGHT);
            case 1:
                return new Pawn(color, sign * PAWN_WEIGHT);
        }
        return null;
    }
}
