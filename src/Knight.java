import java.util.List;

public class Knight extends Piece {

    @Override
    PieceType getPieceType() {
        return PieceType.KNIGHT;
    }

    @Override
    List<Position> getLegalMoves(Board board, Position position) {
        return null;
    }
}
