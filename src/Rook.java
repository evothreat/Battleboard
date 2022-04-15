import java.util.List;

public class Rook extends Piece {

    public Rook(Colour color, int weight) {
        super(PieceType.ROOK, color, weight, false);
    }

    public Rook(Rook other) {
        super(other.getPieceType(), other.getColor(), other.getWeight(), other.hasMoved());
    }

    @Override
    List<Square> getValidTargets(Board board, Square square) {
        return Target.getTargetsInCross(board, square, getColor());
    }
}
