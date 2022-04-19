public class PieceUtil {

    static Piece intToPiece(int n) {
        Colour color = n > 0 ? Colour.WHITE : Colour.BLACK;
        switch (n * color.sign()) {
            case 6:
                return new King(color);
            case 5:
                return new Queen(color);
            case 4:
                return new Rook(color);
            case 3:
                return new Bishop(color);
            case 2:
                return new Knight(color);
            case 1:
                return new Pawn(color);
        }
        return null;
    }

    static int getWeight(Piece piece) {
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
