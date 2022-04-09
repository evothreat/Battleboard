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
    List<Square> getValidTargets(Board board, Position position) {
        return Target.getTargetsInCross(board, position, getColor());
    }
}
