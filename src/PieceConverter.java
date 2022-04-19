public class PieceConverter {

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

    static int pieceToInt(Piece piece) {
        int sign = piece.getColor().sign();
        switch (piece.getPieceType()) {
            case KING:
                return sign * 6;
            case QUEEN:
                return sign * 5;
            case ROOK:
                return sign * 4;
            case BISHOP:
                return sign * 3;
            case KNIGHT:
                return sign * 2;
            case PAWN:
                return sign;
        }
        return 0;
    }
}
