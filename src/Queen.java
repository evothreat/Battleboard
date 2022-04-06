import java.util.List;

public class Queen extends Piece {

    @Override
    PieceType getPieceType() {
        return PieceType.QUEEN;
    }

    @Override
    List<Position> getLegalMoves(Board board, Position position) {
        return null;
    }
}
