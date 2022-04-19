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
}
