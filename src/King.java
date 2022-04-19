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
    List<Square> getValidTargets(Board board, Square square) {
        // targets will also contain enemy squares
        List<Square> targets = getTargets(board, square);
        targets.removeIf(sq -> sq.isSettled() && hasSameColor(sq.getPiece()));
        getTargets(board, board.getEnemyKingSq()).forEach(targets::remove);

        boolean w = getColor().bool();
        for (Square esq : board.getEnemyPiecesSq()) {
            Piece enemy = esq.getPiece();
            List<Square> enemyTargets = enemy.getValidTargets(board, esq);
            if (enemy.isPawn()) {
                enemyTargets.removeIf(dst -> Direction.from2Squares(esq, dst).isDirect());
                enemyTargets.add(board.getSquareAt(w ? esq.getX()+1 : esq.getX()-1, esq.getY()-1));
                enemyTargets.add(board.getSquareAt(w ? esq.getX()+1 : esq.getX()-1, esq.getY()+1));
                enemyTargets.forEach(targets::remove);      // check for null?
                continue;
            }
            for (Square t : enemyTargets) {
                if (targets.remove(t)) {
                    Direction dir = Direction.from2Squares(esq, square);
                    Square behindSq = board.getSquareAt(square.getX()+dir.getX(), square.getY()+dir.getY());
                    if (behindSq != null) targets.remove(behindSq);
                }
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