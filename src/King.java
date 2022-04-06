import java.util.List;

public class King extends Piece {

    @Override
    PieceType getPieceType() {
        return PieceType.KING;
    }

    @Override
    List<Position> getLegalMoves(Board board, Position position) {
        return null;
    }
}
