import java.util.List;

public class Bishop extends Piece {

    public Bishop(Colour color) {
        super(color, PieceType.BISHOP, false);
    }

    public Bishop(Bishop other) {
        super(other.getColor(), other.getPieceType(), other.hasMoved());
    }

    @Override
    List<Square> getValidTargets(Board board, Square square) {
        return Target.getTargetsInDiagonal(board, square, getColor());
    }
}
