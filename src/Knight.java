import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Knight extends Piece {

    public Knight(Color color) {
        super(color, PieceType.KNIGHT);
    }

    @Override
    List<Square> getValidTargets(Board board, Square square) {
        int x = square.getX();
        int y = square.getY();
        List<Square> targets = new ArrayList<>();
        targets.add(board.getSquareAt(x - 1, y + 2));
        targets.add(board.getSquareAt(x + 1, y + 2));
        targets.add(board.getSquareAt(x + 2, y + 1));
        targets.add(board.getSquareAt(x + 2, y - 1));
        targets.add(board.getSquareAt(x + 1, y - 2));
        targets.add(board.getSquareAt(x - 1, y - 2));
        targets.add(board.getSquareAt(x - 2, y - 1));
        targets.add(board.getSquareAt(x - 2, y + 1));
        targets.removeIf(sq -> sq == null || sq.isSettled() && hasSameColor(sq.getPiece()));
        return targets;
    }
}
