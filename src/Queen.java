import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Queen extends Piece {

    public Queen(Color color) {
        super(color);
    }

    @Override
    PieceType getPieceType() {
        return PieceType.QUEEN;
    }

    @Override
    List<Square> getValidTargets(Board board, Position position) {
        List<Square> targets = Target.getTargetsInCross(board, position, getColor());
        targets.addAll(Target.getTargetsInDiagonal(board, position, getColor()));
        return targets;
    }
}
