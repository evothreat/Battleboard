import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class King extends Piece {

    public King(Colour color) {
        super(PieceType.KING, color, false);
    }

    public King(King other) {
        super(other.getPieceType(), other.getColor(), other.hasMoved());
    }

    @Override
    List<Square> getValidTargets(Board board, Square kingSq) {
        // targets will also contain enemy squares
        List<Square> targets = getTargets(board, kingSq);
        targets.removeIf(sq -> sq.isSettled() && hasSameColor(sq.getPiece()));
        getTargets(board, board.getEnemyKingSq()).forEach(targets::remove);

        boolean w = getColor().bool();
        for (Square esq : board.getEnemyPiecesSq()) {
            Piece enemy = esq.getPiece();
            if (enemy.isPawn()) {
                Square diagLeft = board.getSquareAt(w ? esq.getX()+1 : esq.getX()-1, esq.getY()-1);
                Square diagRight = board.getSquareAt(w ? esq.getX()+1 : esq.getX()-1, esq.getY()+1);
                if (diagLeft != null) {
                    targets.remove(diagLeft);
                }
                if (diagRight != null) {
                    targets.remove(diagRight);
                }
                continue;
            }
            for (Square t : enemy.getValidTargets(board, esq)) {
                if (t.equals(kingSq) && !t.getPiece().isKnight()) {
                    Direction dir = Direction.from2Squares(esq, kingSq);
                    Square behindSq = board.getSquareAt(kingSq.getX()+dir.getX(), kingSq.getY()+dir.getY());
                    if (behindSq != null) {
                        targets.remove(behindSq);
                    }
                    continue;
                }
                targets.remove(t);
            }
        }
        return targets;
    }

    private List<Square> getTargets(Board board, Square square) {
        int x = square.getX();
        int y = square.getY();
        List<Square> targets = new ArrayList<>();
        targets.add(board.getSquareAt(x - 1, y + 1));
        targets.add(board.getSquareAt(x, y + 1));
        targets.add(board.getSquareAt(x + 1, y + 1));
        targets.add(board.getSquareAt(x + 1, y));
        targets.add(board.getSquareAt(x + 1, y - 1));
        targets.add(board.getSquareAt(x, y - 1));
        targets.add(board.getSquareAt(x - 1, y - 1));
        targets.add(board.getSquareAt(x - 1, y));
        targets.removeIf(Objects::isNull);
        return targets;
    }
}