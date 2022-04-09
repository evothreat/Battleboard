import java.util.List;

public class Rook extends Piece {

    public Rook(Color color) {
        super(color);
    }

    @Override
    PieceType getPieceType() {
        return PieceType.ROOK;
    }

    @Override
    List<Square> getValidTargets(Board board, Square square) {
        return Target.getTargetsInCross(board, square, getColor());
    }
}
