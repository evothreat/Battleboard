import java.util.List;

public class Queen extends Piece {

    public Queen(Colour color) {
        super(color, PieceType.QUEEN, false);
    }

    public Queen(Queen other) {
        super(other.getColor(), other.getPieceType(), other.hasMoved());
    }

    @Override
    List<Square> getValidTargets(Board board, Square square) {
        List<Square> targets = Target.getTargetsInCross(board, square, getColor());
        targets.addAll(Target.getTargetsInDiagonal(board, square, getColor()));
        return targets;
    }
}
