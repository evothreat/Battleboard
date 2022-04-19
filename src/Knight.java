import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {

    public Knight(Colour color) {
        super(PieceType.KNIGHT, color, false);
    }

    public Knight(Knight other) {
        super(other.getPieceType(), other.getColor(), other.hasMoved());
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
