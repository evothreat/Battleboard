import java.util.List;

public class Rook extends Piece {

    public Rook(Colour color) {
        super(PieceType.ROOK, color, false);
    }

    public Rook(Rook other) {
        super(other.getPieceType(), other.getColor(), other.hasMoved());
    }

    @Override
    List<Square> getValidTargets(Board board, Square square) {
        return Target.getTargetsInCross(board, square);
    }
}
