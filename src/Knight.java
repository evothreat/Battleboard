import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Knight extends Piece {

    @Override
    PieceType getPieceType() {
        return PieceType.KNIGHT;
    }

    @Override
    List<Position> getValidTargets(Board board, Position position) {
        int x = position.getX();
        int y = position.getY();
        List<Position> targets = Arrays.asList(
                new Position(x - 1, y + 2),
                new Position(x + 1, y + 2),
                new Position(x + 2, y + 1),
                new Position(x + 2, y - 1),
                new Position(x + 1, y - 2),
                new Position(x - 1, y - 2),
                new Position(x - 2, y - 1),
                new Position(x - 2, y + 1)
        );
        targets.removeIf(p -> p.getX() > 7 || 0 > p.getX() ||
                              p.getY() > 7 || 0 > p.getY() ||
                              hasSameColor(board.getPieceAt(p)));
        return targets;
    }
}
