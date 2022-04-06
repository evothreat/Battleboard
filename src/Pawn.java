import java.util.List;

public class Pawn extends Piece {

    @Override
    PieceType getPieceType() {
        return PieceType.PAWN;
    }

    @Override
    List<Position> getLegalMoves(Board board, Position position) {
        return null;
    }
}
