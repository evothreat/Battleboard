import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Bishop extends Piece {

    @Override
    PieceType getPieceType() {
        return PieceType.BISHOP;
    }

    @Override
    List<Position> getValidTargets(Board board, Position position) {
        return Target.getTargetsInDiagonal(board, position, getColor());
    }
}
