import java.util.List;

public class Rook extends Piece {

    @Override
    PieceType getPieceType() {
        return PieceType.ROOK;
    }

    @Override
    List<Position> getValidTargets(Board board, Position position) {
        return Target.getTargetsInCross(board, position, getColor());
    }
}
