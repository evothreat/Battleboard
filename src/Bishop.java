import java.util.List;

public class Bishop extends Piece {

    public Bishop(Colour color) {
        super(PieceType.BISHOP, color, false);
    }

    public Bishop(Bishop other) {
        super(other.getPieceType(), other.getColor(), other.hasMoved());
    }

    @Override
    List<Square> getValidTargets(Board board, Square square) {
        return Target.getTargetsInDiagonal(board, square);
    }
}
