import java.util.List;

public class Bishop extends Piece {

    public Bishop(Colour color) {
        super(PieceType.BISHOP, color, color == Colour.BLACK ? -30 : 30, false);
    }

    public Bishop(Bishop other) {
        super(other.getPieceType(), other.getColor(), other.getWeight(), other.hasMoved());
    }

    @Override
    List<Square> getValidTargets(Board board, Square square) {
        return Target.getTargetsInDiagonal(board, square, getColor());
    }
}
