import java.util.List;

public class Rook extends Piece {

    public Rook(Colour color) {
        super(PieceType.ROOK, color, color == Colour.BLACK ? -50 : 50, false);
    }

    public Rook(Rook other) {
        super(other.getPieceType(), other.getColor(), other.getWeight(), other.hasMoved());
    }

    @Override
    List<Square> getValidTargets(Board board, Square square) {
        return Target.getTargetsInCross(board, square, getColor());
    }
}
