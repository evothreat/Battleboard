import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Queen extends Piece {

    public Queen(Color color) {
        super(color, PieceType.QUEEN);
    }

    @Override
    List<Square> getValidTargets(Board board, Square square) {
        List<Square> targets = Target.getTargetsInCross(board, square, getColor());
        targets.addAll(Target.getTargetsInDiagonal(board, square, getColor()));
        return targets;
    }
}
