import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Queen extends Piece {

    @Override
    PieceType getPieceType() {
        return PieceType.QUEEN;
    }

    @Override
    List<Position> getValidTargets(Board board, Position position) {
        List<Position> targets = Target.getTargetsInCross(board, position, getColor());
        targets.addAll(Target.getTargetsInDiagonal(board, position, getColor()));
        return targets;
    }
}
