import java.util.ArrayList;
import java.util.List;

abstract public class Piece {

    private final PieceType pieceType;
    private final Colour color;
    private final int weight;
    private boolean hasMoved;

    public Piece(PieceType pieceType, Colour color, int weight, boolean hasMoved) {
        this.color = color;
        this.pieceType = pieceType;
        this.hasMoved = hasMoved;
        this.weight = weight;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public Colour getColor() {
        return color;
    }

    public int getWeight() {
        return weight;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public boolean hasSameColor(Piece other) {
        return other != null && color == other.getColor();
    }

    public boolean isKing() {
        return pieceType == PieceType.KING;
    }

    public boolean isQueen() {
        return pieceType == PieceType.QUEEN;
    }

    public boolean isBishop() {
        return pieceType == PieceType.BISHOP;
    }

    public boolean isKnight() {
        return pieceType == PieceType.KNIGHT;
    }

    public boolean isRook() {
        return pieceType == PieceType.ROOK;
    }

    public boolean isPawn() {
        return pieceType == PieceType.PAWN;
    }

    public boolean isWhite() {
        return color == Colour.WHITE;
    }

    public boolean isBlack() {
        return color == Colour.BLACK;
    }

    abstract List<Square> getValidTargets(Board board, Square square);
}
