import java.util.List;

abstract public class Piece {

    private final Color color;

    public Piece(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public boolean hasSameColor(Piece other) {
        return other != null && color == other.getColor();
    }

    public boolean canAttackKing(Board board, Square square) {
        List<Square> targets = getValidTargets(board, square);
        return targets.stream().anyMatch(sq -> sq.isSettled() &&
                                         sq.getPiece().getColor() != getColor() &&
                                         sq.getPiece().getPieceType() == PieceType.KING);
    }

    public boolean canDefendKing(Board board, Square square) {
        return false;
    }

    abstract List<Square> getValidTargets(Board board, Square square);

    abstract PieceType getPieceType();
}
