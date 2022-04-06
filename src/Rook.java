import java.util.List;

public class Rook extends Piece {

    @Override
    PieceType getPieceType() {
        return PieceType.ROOK;
    }

    @Override
    List<Position> getLegalMoves(Board board, Position position) {
        return null;
    }
}
