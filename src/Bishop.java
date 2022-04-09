import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Bishop extends Piece {

    public Bishop(Color color) {
        super(color, PieceType.BISHOP);
    }

    @Override
    List<Square> getValidTargets(Board board, Square square) {
        return Target.getTargetsInDiagonal(board, square, getColor());
    }
}
