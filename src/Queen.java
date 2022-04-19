import java.util.List;

public class Queen extends Piece {

    public Queen(Colour color) {
        super(PieceType.QUEEN, color, false);
    }

    public Queen(Queen other) {
        super(other.getPieceType(), other.getColor(), other.hasMoved());
    }

    @Override
    List<Square> getValidTargets(Board board, Square square) {
        List<Square> targets = Target.getTargetsInCross(board, square);
        targets.addAll(Target.getTargetsInDiagonal(board, square));
        return targets;
    }
}
