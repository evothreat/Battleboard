import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class King extends Piece {

    public King(Color color) {
        super(color);
    }

    @Override
    PieceType getPieceType() {
        return PieceType.KING;
    }

    @Override
    List<Square> getValidTargets(Board board, Square square) {
        int x = square.getX();
        int y = square.getY();
        List<Square> targets = Arrays.asList(
                board.getSquareAt(x - 1, y + 1),
                board.getSquareAt(x, y + 1),
                board.getSquareAt(x + 1, y + 1),
                board.getSquareAt(x + 1, y),
                board.getSquareAt(x + 1, y - 1),
                board.getSquareAt(x, y - 1),
                board.getSquareAt(x - 1, y - 1),
                board.getSquareAt(x - 1, y)
        );
        // retrieve enemyTargets after null & sameColor check?
        List<Square> enemyTargets = board.getValidTargets(getColor().toggle());
        targets.removeIf(sq -> sq == null || sq.isSettled() && hasSameColor(sq.getPiece()) || enemyTargets.contains(sq));
        return targets;
    }
}
