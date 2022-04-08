import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class King extends Piece {

    @Override
    PieceType getPieceType() {
        return PieceType.KING;
    }

    @Override
    List<Position> getValidTargets(Board board, Position position) {
        int x = position.getX();
        int y = position.getY();
        List<Position> targets = Arrays.asList(
                new Position(x - 1, y + 1),
                new Position(x, y + 1),
                new Position(x + 1, y + 1),
                new Position(x + 1, y),
                new Position(x + 1, y - 1),
                new Position(x, y - 1),
                new Position(x - 1, y - 1),
                new Position(x - 1, y)
        );
        List<Position> enemyTargets = board.getValidTargets(getColor().toggle());
        targets.removeIf(p -> p.getX() > 7 || 0 > p.getX() ||
                              p.getY() > 7 || 0 > p.getY() ||
                              hasSameColor(board.getPieceAt(p)) ||
                              enemyTargets.contains(p));
        return targets;
    }
}
