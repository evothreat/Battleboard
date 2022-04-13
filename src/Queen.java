import java.util.List;

public class Queen extends Piece {

    public Queen(Colour color) {
        super(PieceType.QUEEN, color, color == Colour.BLACK ? -90 : 90, false);
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
