import java.util.List;

public class Bishop extends Piece {

    public Bishop(Colour color, int weight) {
        super(PieceType.BISHOP, color, weight, false);
    }

    public Bishop(Bishop other) {
        super(other.getPieceType(), other.getColor(), other.getWeight(), other.hasMoved());
    }

    @Override
    List<Square> getValidTargets(Board board, Square square) {
        return Target.getTargetsInDiagonal(board, square, getColor());
    }
}
