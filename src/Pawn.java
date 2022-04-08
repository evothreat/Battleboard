import java.util.ArrayList;
import java.util.List;


// after move check whether pawn became queen!
public class Pawn extends Piece {

    @Override
    PieceType getPieceType() {
        return PieceType.PAWN;
    }

    @Override
    List<Position> getValidTargets(Board board, Position position) {
        List<Position> targets = new ArrayList<>();

        int x = position.getX();
        int y = position.getY();

        if (getColor().isWhite()) {
            if (board.getPieceAt(x, y + 1) == null) {
                targets.add(new Position(x, y + 1));
            }
            if (8 - y == 7 && board.getPieceAt(x, y + 2) == null) {
                targets.add(new Position(x, y + 2));
            }
            if (board.getPieceAt(x - 1, y + 1) != null && !hasSameColor(board.getPieceAt(x - 1, y + 1))) {
                targets.add(new Position(x - 1, y + 1));
            }
            if (board.getPieceAt(x + 1, y + 1) != null && !hasSameColor(board.getPieceAt(x + 1, y + 1))) {
                targets.add(new Position(x + 1, y + 1));
            }
        } else if (getColor().isBlack()) {
            if (board.getPieceAt(x, y - 1) == null) {
                targets.add(new Position(x, y - 1));
            }
            if (8 - y == 1 && board.getPieceAt(x, y - 2) == null) {
                targets.add(new Position(x, y - 2));
            }
            if (board.getPieceAt(x - 1, y - 1) != null && !hasSameColor(board.getPieceAt(x - 1, y - 1))) {
                targets.add(new Position(x - 1, y - 1));
            }
            if (board.getPieceAt(x + 1, y - 1) != null && !hasSameColor(board.getPieceAt(x + 1, y - 1))) {
                targets.add(new Position(x + 1, y - 1));
            }
        }
        return targets;
    }
}
