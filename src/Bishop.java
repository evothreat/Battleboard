import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Bishop extends Piece {

    public Bishop(Color color) {
        super(color);
    }

    @Override
    PieceType getPieceType() {
        return PieceType.BISHOP;
    }

    @Override
    List<Square> getValidTargets(Board board, Position position) {
        return Target.getTargetsInDiagonal(board, position, getColor());
    }
}
