import java.util.List;

public class Queen extends Piece {

    public Queen(Colour color, int weight) {
        super(PieceType.QUEEN, color, weight, false);
    }

    public Queen(Queen other) {
        super(other.getPieceType(), other.getColor(), other.getWeight(), other.hasMoved());
    }

    @Override
    List<Square> getValidTargets(Board board, Square square) {
        List<Square> targets = Target.getTargetsInCross(board, square, getColor());
        targets.addAll(Target.getTargetsInDiagonal(board, square, getColor()));
        return targets;
    }
}
