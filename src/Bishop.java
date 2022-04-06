import java.util.List;

public class Bishop extends Piece {

    @Override
    PieceType getPieceType() {
        return PieceType.BISHOP;
    }

    @Override
    List<Position> getLegalMoves(Board board, Position position) {
        return null;
    }
}
