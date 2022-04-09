import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Knight extends Piece {

    public Knight(Color color) {
        super(color);
    }

    @Override
    PieceType getPieceType() {
        return PieceType.KNIGHT;
    }

    @Override
    List<Square> getValidTargets(Board board, Position position) {
        int x = position.getX();
        int y = position.getY();
        List<Square> targets = Arrays.asList(
                board.getSquareAt(x - 1, y + 2),
                board.getSquareAt(x + 1, y + 2),
                board.getSquareAt(x + 2, y + 1),
                board.getSquareAt(x + 2, y - 1),
                board.getSquareAt(x + 1, y - 2),
                board.getSquareAt(x - 1, y - 2),
                board.getSquareAt(x - 2, y - 1),
                board.getSquareAt(x - 2, y + 1)
        );
        targets.removeIf(sq -> sq == null || sq.isSettled() && hasSameColor(sq.getPiece()));
        return targets;
    }
}
